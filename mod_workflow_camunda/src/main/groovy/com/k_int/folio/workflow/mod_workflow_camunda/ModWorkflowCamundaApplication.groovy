package com.k_int.folio.workflow.mod_workflow_camunda

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.container.RuntimeContainerDelegate;
import org.camunda.bpm.engine.ProcessEngine
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.jdbc.datasource.DataSourceTransactionManager


public class ModWorkflowCamundaApplication implements InitializingBean {

  @Autowired
  private RuntimeService runtimeService;
 
  @Autowired
  private TransactionAwareDataSourceProxy dataSource

  @Autowired
  private DataSourceTransactionManager transactionManager

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
    println("\n\n****\n\nModWorkflowCamundaApplication::configureTestTenants\n\n****\n\n");
    // https://docs.camunda.org/manual/7.7/user-guide/spring-framework-integration/
    // 
    // Consider using .setDataSource(dataSource());
    // .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
    //
    // https://docs.camunda.org/manual/7.3/api-references/deployment-descriptors/#tags-process-engine-configuration-configuration-properties
    //
    // ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
    // .setJdbcUrl('jdbc:postgresql://pghost:5432/foliodev?currentSchema=modwf_diku')
    // .setJdbcDriver('org.postgresql.Driver')
    // .setJdbcUrl('jdbc:postgresql://pghost:5432/foliodev')
    // .setJdbcUsername('folio')
    // .setJdbcPassword('folio')

    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

    println("dataSource: ${dataSource}");
    println("transactionManager: ${transactionManager}");

    config.setDatabaseSchemaUpdate('true')
    config.setDatabaseTablePrefix('modwf_diku.')
    config.setJobExecutorActivate(true)
    config.setProcessEngineName('diku')
    config.setHistory(ProcessEngineConfiguration.HISTORY_FULL)
    config.setDataSource(dataSource)
    config.setTransactionManager(transactionManager)

    println("config: ${config}");

    ProcessEngine processEngine = config.buildProcessEngine();
  
    RuntimeContainerDelegate.INSTANCE.get().registerProcessEngine(processEngine);

  }
}
