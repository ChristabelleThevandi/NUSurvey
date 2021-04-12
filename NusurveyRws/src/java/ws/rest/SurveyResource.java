/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.rest;

import ejb.session.stateless.SurveySessionBeanLocal;
import ejb.session.stateless.UserSessionBeanLocal;
import entity.Survey;
import entity.User;
import exception.SurveyNotFoundException;
import exception.UserNotFoundException;
import java.util.ArrayList;
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
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ws.datamodel.FilterSurveysByTagsReq;

/**
 * REST Web Service
 *
 * @author hp
 */
@Path("Survey")
public class SurveyResource {

    UserSessionBeanLocal userSessionBean = lookupUserSessionBeanLocal();

    SurveySessionBeanLocal surveySessionBean = lookupSurveySessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SurveyResource
     */
    public SurveyResource() {
    }
    @Path("retrieveSurveyBySurveyId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSurveyBySurveyId(@PathParam("id") Long id) {
        try {
            System.out.println("tset");
            Survey survey = surveySessionBean.retrieveSurveyBySurveyId(id);
            GenericEntity<Survey> genericEntity = new GenericEntity<Survey>(survey) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (SurveyNotFoundException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    @Path("retrieveAllSurveys")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSurveys() {
        try {
            System.out.println("tset");
            List<Survey> survey = surveySessionBean.retrieveAllSurveys();
            GenericEntity<List<Survey>> genericEntity = new GenericEntity<List<Survey>>(survey) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    @Path("retrieveMyFilledSurveys")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMyFilledSurveys(User user) {
        try {
            System.out.println("tset");
            User currentUser = userSessionBean.retrieveUserByEmail(user.getEmail());
            List<Survey> survey = currentUser.getMySurveys();
            GenericEntity<List<Survey>> genericEntity = new GenericEntity<List<Survey>>(survey) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (UserNotFoundException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    @Path("searchSurveysByTitle")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSurveysByTitle(@QueryParam("title") String title,@QueryParam("email") String email ) {
        try {
            System.out.println("tset");
            User user = userSessionBean.retrieveUserByEmail(email);
            List<Survey> survey = surveySessionBean.searchSurveysByTitle(title);
            List<Survey> toSend = new ArrayList<>();
            for(Survey s:survey) {
                if (!s.getSurveyees().contains(user)) {
                    toSend.add(s);
                }
            }
            GenericEntity<List<Survey>> genericEntity = new GenericEntity<List<Survey>>(toSend) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    @Path(" filterSurveysByTags")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response  filterSurveysByTags(FilterSurveysByTagsReq req) {
        try {
            System.out.println("tset");
            List<Survey> survey = surveySessionBean.filterSurveysByTags(req.getTagIds(), req.getCondition());
            
            GenericEntity<List<Survey>> genericEntity = new GenericEntity<List<Survey>>(survey) {
            };

            return Response.status(Response.Status.OK).entity(genericEntity).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
 
  
    /**
     * Retrieves representation of an instance of ws.rest.SurveyResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SurveyResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    private SurveySessionBeanLocal lookupSurveySessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SurveySessionBeanLocal) c.lookup("java:global/Nusurvey/Nusurvey-ejb/SurveySessionBean!ejb.session.stateless.SurveySessionBeanLocal");
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
