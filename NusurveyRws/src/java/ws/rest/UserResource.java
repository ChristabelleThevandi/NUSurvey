/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Survey;
import entity.Transaction;
import entity.User;
import exception.CreditCardErrorException;
import exception.EmailExistException;
import exception.InputDataValidationException;
import exception.InvalidLoginCredentialException;
import exception.UnknownPersistenceException;
import exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.AddCreditCardReq;
import ws.datamodel.ChangePasswordReq;
import ws.datamodel.LoginReq;
import ws.datamodel.UpdateTagReq;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("User")
public class UserResource {

    UserSessionBeanLocal userSessionBean = lookupUserSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginReq req) {
        LoginReq loginReq = req;
        String email = loginReq.getEmail();
        String password = loginReq.getPassword();
        try {
            User currentUser = userSessionBean.login(email, password);
            currentUser.setPassword(null);
            currentUser.setSurveyTaken(null);
            currentUser.setCreditCard(null);
            currentUser.setMySurveys(null);
            currentUser.setSurveyTaken(null);
            currentUser.setResponses(null);
            currentUser.setTransactions(null);
            return Response.status(Response.Status.OK).entity(currentUser).build();
        } catch (InvalidLoginCredentialException ex) {
            return Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveUserByEmail/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUserByEmail(@PathParam("email") String email) {
        try {
            System.out.println("tset");
            User currentUser = userSessionBean.retrieveUserByEmail(email);
            currentUser.setPassword(null);
            currentUser.setSurveyTaken(null);
            currentUser.setCreditCard(null);
            currentUser.setMySurveys(null);
            currentUser.setSurveyTaken(null);
            currentUser.setResponses(null);
            currentUser.setTransactions(null);
            GenericEntity<User> genericEntity = new GenericEntity<User>(currentUser) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (UserNotFoundException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("register")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User newUser) {
        if (newUser != null) {
            try {
                Long newUserId = userSessionBean.register(newUser).getUserId();
                return Response.status(Response.Status.OK).entity(newUserId).build();
            } catch (EmailExistException | InputDataValidationException | UnknownPersistenceException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid register request").build();
        }
    }

    @Path("changePassword")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(ChangePasswordReq req) {
        if (req != null) {
            try {
                userSessionBean.changePassword(req.getUser(), req.getPassword());
                return Response.status(Response.Status.OK).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid change password request").build();
        }
    }

    @Path("updateProfile")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(User user) {
        if (user != null) {
            try {
                userSessionBean.updateProfile(user);
                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update request").build();
        }
    }
    
    @Path("updateTag")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTag(UpdateTagReq req) {
        if (req != null) {
            try {
                userSessionBean.updateTag(req.getUser(), req.getTags());
                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update request").build();
        }
    }

    @Path("uploadAvatar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatar(User user) {
        if (user != null) {
            try {
                userSessionBean.updateProfile(user);
                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid upload request").build();
        }
    }

    @Path("addCreditCard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCreditCard(AddCreditCardReq req) {
        if (req != null) {
            try {
                User newUser = userSessionBean.addCreditCard(req.getUser(), req.getCard());
                return Response.status(Response.Status.OK).build();
            } catch (UserNotFoundException | CreditCardErrorException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid add credit card request").build();
        }
    }

    @Path("getRecommendation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendation(User user) {
        if (user != null) {
            try {
                List<Survey> surveys = userSessionBean.getRecommendation(user);
                for (Survey s : surveys) {
                    s.getCreator().getSurveyTaken().clear();
                    s.getCreator().getMySurveys().clear();
                    s.getCreator().setCreditCard(null);
                    s.getCreator().getTransactions().clear();
                    s.getCreator().getResponses().clear();
                    s.getTransactions().clear();
                    s.setSurveyees(null);
                    s.setQuestionWrappers(null);
                    s.setResponses(null);
                }
                return Response.status(Response.Status.OK).entity(surveys).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid get recoomendation request").build();
        }
    }

    private UserSessionBeanLocal lookupUserSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/UserSessionBean!ejb.session.stateless.UserSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
