/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import entity.User;
import exception.UserNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@Path("Tag")
public class TagResource {

    TagSessionBeanLocal tagSessionBean = lookupTagSessionBeanLocal();

    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TagResource
     */
    public TagResource() {
    }

    @Path("retrieveAllTags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTags() {
        try {
            System.out.println("test");
            List<Tag> tags = tagSessionBean.retrieveAllTags();
            GenericEntity<List<Tag>> genericEntity = new GenericEntity<List<Tag>>(tags) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path("retrieveTagByTagName/{tag}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTagByTagName(@PathParam("tag") String tag) {
        try {
            System.out.println("tset");
            Tag t = tagSessionBean.retrieveTagByTagName(tag);
            GenericEntity<Tag> genericEntity = new GenericEntity<Tag>(t) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    /**
     * Retrieves representation of an instance of ws.rest.TagResource
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
     * PUT method for updating or creating an instance of TagResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private TagSessionBeanLocal lookupTagSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TagSessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/TagSessionBean!ejb.session.stateless.TagSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
