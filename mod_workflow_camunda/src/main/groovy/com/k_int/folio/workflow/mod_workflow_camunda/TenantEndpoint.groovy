package com.k_int.folio.workflow.mod_workflow_camunda;

import javax.ws.rs.Path;


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
  public void handle() {
    println("TenantEndpoint::handle");
  }
}
