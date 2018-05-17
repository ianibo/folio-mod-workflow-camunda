package com.k_int.folio.workflow.mod_workflow_camunda

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
// import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.container.RuntimeContainerDelegate;
import org.camunda.bpm.engine.ProcessEngine


public class ModWorkflowCamundaApplication implements InitializingBean {

  @Autowired
  private RuntimeService runtimeService;

  // @Autowired
  // private SpringProcessEngineConfiguration processEngineConfiguration;


  public void afterPropertiesSet() throws Exception {
    runtimeService.startProcessInstanceByKey("loanApproval");
    configureTestTenants()
  }

  public void setRuntimeService(RuntimeService runtimeService) {
    this.runtimeService = runtimeService;
  }

  private void configureTestTenants() {
    println("ModWorkflowCamundaApplication::configureTestTenants");
    // https://docs.camunda.org/manual/7.7/user-guide/spring-framework-integration/
    // 
    // Consider using .setDataSource(dataSource());
    //
    ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
       .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
       .setJdbcDriver('org.postgresql.Driver')
       .setJdbcUrl('jdbc:postgresql://pghost:5432/foliodev')
       .setJdbcUsername('folio')
       .setJdbcPassword('folio')
       .setDatabaseTablePrefix('modwf_diku.')
       .setJobExecutorActivate(true)
       .setProcessEngineName('diku')
       .setHistory(ProcessEngineConfiguration.HISTORY_FULL)
       .buildProcessEngine();
  
    RuntimeContainerDelegate.INSTANCE.get().registerProcessEngine(processEngine);

  }
}
