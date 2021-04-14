/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.ResponseSessionBeanLocal;
import entity.SurveyResponse;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("Response")
public class ResponseResource {

    ResponseSessionBeanLocal responseSessionBean = lookupResponseSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ResponseResource
     */
    public ResponseResource() {
    }

    @Path("createResponse")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResponse(SurveyResponse res) {
        System.out.println("create response called");
        if (res != null) {
            try {
                Long id = responseSessionBean.createResponse(res);
                System.out.println("dcdddddddd" + id);
                return Response.status(Response.Status.OK).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid create new response request").build();
        }
    }

    /**
     * Retrieves representation of an instance of ws.rest.ResponseResource
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
     * PUT method for updating or creating an instance of ResponseResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private ResponseSessionBeanLocal lookupResponseSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ResponseSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/ResponseSessionBean!ejb.session.stateless.ResponseSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
