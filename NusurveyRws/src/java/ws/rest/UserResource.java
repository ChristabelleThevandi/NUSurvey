/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Survey;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String email, String password) {
        if (email != null && password != null) {
            try {
                // ......
                Long newUserId = userSessionBean.login(email, password).getUserId();
                return Response.status(Response.Status.OK).entity(newUserId).build();
            } catch (InvalidLoginCredentialException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid log in request").build();
        }
    }

    @Path("retrieveAllRecords")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUserByEmail(String email) {
        try {
            User userEntity = userSessionBean.retrieveUserByEmail(email);

            GenericEntity<User> genericEntity = new GenericEntity<User>(userEntity) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(User user, String password) {
        if (user != null && password != null) {
            try {
                userSessionBean.changePassword(user, password);
                return Response.status(Response.Status.OK).entity(user.getUserId()).build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid change password request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProfile(User user) {
        if (user != null) {
            try {
                userSessionBean.updateProfile(user);
                return Response.status(Response.Status.OK).entity(user.getUserId()).build();
            } catch (UserNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadAvatar(User user) {
        if (user != null) {
            try {
                userSessionBean.updateProfile(user);
                return Response.status(Response.Status.OK).entity(user.getUserId()).build();
            } catch (UserNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid upload request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCreditCard(User user, CreditCard creditCard) {
        if (user != null && creditCard != null) {
            try {
                User newUser = userSessionBean.addCreditCard(user, creditCard);
                return Response.status(Response.Status.OK).entity(newUser.getUserId()).build();
            } catch (UserNotFoundException | CreditCardErrorException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid add credit card request").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendation(User user) {
        if (user != null) {
            try {
                List<Survey> surveys = userSessionBean.getRecommendation(user);

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
