package com.k_int.folio.workflow.mod_workflow_camunda;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType

// notes
// @GET
// @Path("echoBpm/{processID}/{hello}")
// @Produces(MediaType.APPLICATION_JSON)
// public String echoBpm(@PathParam("processID") String processID, 
// 				    @PathParam("hello") String hello ){
// 	
// 	LOGGER.info("*** echoBpm - processID: " + processID);

@Path("")
public class TenantEndpoint {

  @Path("/tenant")
  @Produces(MediaType.APPLICATION_JSON)
  public String handle() {
    println("TenantEndpoint::handle");
    return "{result:'OK'}"
  }
}
