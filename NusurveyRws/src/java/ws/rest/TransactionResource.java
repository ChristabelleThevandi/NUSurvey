/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.TransactionSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Survey;
import entity.Transaction;
import entity.User;
import enumeration.TransactionType;
import exception.SurveyNotFoundException;
import exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import ws.datamodel.CreateNewTransactionReq;
import ws.datamodel.GiveRewardReq;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("Transaction")
public class TransactionResource {

    UserSessionBeanLocal userSessionBean = lookupUserSessionBeanLocal();

    TransactionSessionBeanLocal transactionSessionBean = lookupTransactionSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TransactionResource
     */
    public TransactionResource() {
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewTransaction(CreateNewTransactionReq req) {
        if (req != null) {
            try {
                transactionSessionBean.createNewTransaction(req.getCard(), req.getAmount(), req.getType(), req.getSurveyId(), req.getDate());
                return Response.status(Response.Status.OK).build();
            } catch (SurveyNotFoundException ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new transaction request").build();
        }
    }

    @Path("retrieveAllTransaction/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTransaction(@PathParam("email") String email) {
        try {
            User userEntity = userSessionBean.retrieveUserByEmail(email);
            List<Transaction> trans = transactionSessionBean.retrieveAllTransaction(userEntity);
            for (Transaction t : trans) {
                t.setUser(null);
                t.getCreditCard().getTransactions().clear();
                t.setSurvey(null);
                t.getCreditCard().setUser(null);
            }
            GenericEntity<List<Transaction>> genericEntity = new GenericEntity<List<Transaction>>(trans) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (UserNotFoundException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    /*@Path("retrieveMyExpenseTransaction")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMyExpenseTransaction(@PathParam("email") String email) {
        try {
            User userEntity = userSessionBean.retrieveUserByEmail(email);
            List<Transaction> trans = transactionSessionBean.retrieveMyExpenseTransaction(userEntity);
            GenericEntity<List<Transaction>> genericEntity = new GenericEntity<List<Transaction>>(trans) {
            };

            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }*/

    @Path("giveReward")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response giveReward(Survey sur) {
        if (sur != null) {
            try {
                transactionSessionBean.giveReward(sur);
                return Response.status(Response.Status.OK).entity("Success").build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid upload request").build();
        }
    }

    @Path("receiveIncentive")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveIncentive(User user) {
        if (user != null) {
            try {
                transactionSessionBean.receiveIncentive(user);
                return Response.status(Response.Status.OK).entity("Success").build();
            } catch (Exception ex) {
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid upload request").build();
        }
    }

    /**
     * Retrieves representation of an instance of ws.rest.TransactionResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of TransactionResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private TransactionSessionBeanLocal lookupTransactionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TransactionSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/TransactionSessionBean!ejb.session.stateless.TransactionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
