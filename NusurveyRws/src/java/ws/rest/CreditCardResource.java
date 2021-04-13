/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.CreditCardSessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.QuestionWrapper;
import entity.User;
import exception.CreditCardErrorException;
import exception.UserNotFoundException;
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
import ws.datamodel.AddCreditCardReq;
import ws.datamodel.CreateNewTransactionReq;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("CreditCard")
public class CreditCardResource {

    UserSessionBeanLocal userSessionBean = lookupUserSessionBeanLocal();

    CreditCardSessionBeanLocal creditCardSessionBean = lookupCreditCardSessionBeanLocal();

    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreditCardResource
     */
    public CreditCardResource() {
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewCreditCard(CreditCard creditCard) {
        if (creditCard != null) {
            try {
                creditCardSessionBean.createCreditCard(creditCard);
                creditCard.setTransactions(null);
                creditCard.setUser(null);
                return Response.status(Response.Status.OK).entity(creditCard).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new credit card request").build();
        }
    }
    @Path("removeCreditCard")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCreditCard(User user) {
        if (user != null) {
            try {
                User newUser = userSessionBean.retrieveUserByEmail(user.getEmail());
                creditCardSessionBean.removeCreditCard(newUser);
                return Response.status(Response.Status.OK).entity(newUser).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid add credit card request").build();
        }
    }
     @Path("retrieveCardByCardId/{temp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCardByCardId(@PathParam("temp") String temp) {
        try {
            System.out.println("tset");
            Long id = Long.valueOf(temp);
            CreditCard c = creditCardSessionBean.retrieveCreditCardByCardId(id);
            GenericEntity<CreditCard> genericEntity = new GenericEntity<CreditCard>(c) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (CreditCardErrorException | NumberFormatException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    /**
     * Retrieves representation of an instance of ws.rest.CreditCardResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of CreditCardResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private CreditCardSessionBeanLocal lookupCreditCardSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CreditCardSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/CreditCardSessionBean!ejb.session.stateless.CreditCardSessionBeanLocal");
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
