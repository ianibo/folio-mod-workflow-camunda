package com.k_int.folio.workflow.mod_workflow_camunda;

import javax.ws.rs.Path;

@Path("")
public class TenantEndpoint {

  @Path("/tenant")
  public void handle() {
    println("TenantEndpoint::handle");
  }
}
