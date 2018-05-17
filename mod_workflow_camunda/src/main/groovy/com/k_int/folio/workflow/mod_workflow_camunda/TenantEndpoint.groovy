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

  // This method will need to do the following at least
  //
  // ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
  // .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
  // .setJdbcDriver(“com.mysql.jdbc.Driver”)
  // .setJdbcUrl(“jdbc:mysql://localhost:3306/camunda”)
  // .setJdbcUsername(“admin”)
  // .setJdbcPassword(“demo”)
  // .setJobExecutorActivate(true)
  // .setProcessEngineName(“camunda”)
  // .setHistory(ProcessEngineConfiguration.HISTORY_FULL)
  // .buildProcessEngine();
  // 
  // RuntimeContainerDelegate.INSTANCE.get().registerProcessEngine(processEngine);
  //
  // As well as store the created tenant in a db table for use in
  // src/main/groovy/org/camunda/bpm/example/loanapproval/rest/RestProcessEngineProvider.groovy getProcessEngineNames
}
