package com.howtodoinjava.jersey;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * Rest web service for operate the admin details
 * 
 * @author dinuka
 * 
 */
@Path("/" + "admin")
@io.swagger.annotations.Api(value = "add", description = "Rest api for do operations on admin", produces = MediaType.APPLICATION_JSON)
@Produces({ MediaType.APPLICATION_JSON })
public class AdminAPI {
    @Context
    private UriInfo context;
    private static final String ACCEPT_HEADERS = "accept";
    @Context
    private HttpHeaders headers;
    @Context
    private HttpServletRequest httpServletRequest;

    @GET
    @Path("/{userName}")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    @io.swagger.annotations.ApiOperation(value = "Get specific admin", httpMethod = "GET", notes = "Fetch the admin user details", response = Response.class)
    public Response getAdmin(
	    @io.swagger.annotations.ApiParam(value = "user_name of admin", required = true) @PathParam("userName") String userName) {

	try {
	    String adminData = "";
	    // process the JSON type request
	    if (headers.getRequestHeaders().get(ACCEPT_HEADERS).contains(MediaType.APPLICATION_JSON)) {
		adminData = "Hello Nabin";
	    }
	    adminData = "Hello "+userName;
	    // TODO: Need to process the XML type requests

	    if (adminData != "") {
		return Response.ok().entity(adminData).build();
	    } else {
		return Response.status(404).build();
	    }
	} catch (Exception e) {
	    return Response.status(404).build();
	}
}
}
