/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.QuestionSessionBeanLocal;
import entity.Question;
import entity.QuestionWrapper;
import entity.User;
import exception.QuestionWrapperNotFoundException;
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
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("Question")
public class QuestionResource {

    QuestionSessionBeanLocal questionSessionBean = lookupQuestionSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QuestionResource
     */
    public QuestionResource() {
    }

    /**
     * Retrieves representation of an instance of ws.rest.QuestionResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    @Path("retrieveQuestionWrapperByTempId/{temp}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveQuestionWrapperByTempId(@PathParam("temp") String temp) {
        try {
            System.out.println("tset");
            Long id = Long.valueOf(temp);
            QuestionWrapper q = questionSessionBean.retrieveQuestionWrapperByTempId(id);
            GenericEntity<QuestionWrapper> genericEntity = new GenericEntity<QuestionWrapper>(q) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (QuestionWrapperNotFoundException | NumberFormatException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    /**
     * PUT method for updating or creating an instance of QuestionResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private QuestionSessionBeanLocal lookupQuestionSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (QuestionSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/QuestionSessionBean!ejb.session.stateless.QuestionSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
