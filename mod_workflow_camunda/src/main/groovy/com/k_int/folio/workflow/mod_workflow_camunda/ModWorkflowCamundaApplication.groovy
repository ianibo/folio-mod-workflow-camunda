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
import org.postgresql.Driver

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
    println("dataSource: ${dataSource}");
    println("transactionManager: ${transactionManager}");
    createTenant('modwf_diku');
    createTenant('modwf_diku_uk');
  }

  private void createTenant(String tenant_id) {
    println("ModWorkflowCamundaApplication::createTenant(${tenant_id})");
    def root_conn = dataSource.getConnection()

    // See if tenant schema alreadt exists
    if ( 1==2 ) {
      // No need to create the new schema - it already exists
    }
    else {
      // Create schema tenant_id
      try {
        println("Attempt create schema ${tenant_id}");
        def create_schema_stmnt = root_conn.createStatement();
        create_schema_stmnt.executeUpdate('create schema '+tenant_id)
        root_conn.close();
        println("Done");
      }
      catch ( Exception e ) {
        e.printStackTrace();
      }
    }

    org.springframework.jdbc.datasource.SimpleDriverDataSource new_sdds = new org.springframework.jdbc.datasource.SimpleDriverDataSource();
    new_sdds.setDriverClass(Driver.class)
    new_sdds.setUrl("jdbc:postgresql://pghost:5432/foliodev?currentSchema=${tenant_id}")
    new_sdds.setUsername("folio")
    new_sdds.setPassword("folio")

    org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy new_ds = new org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy()
    new_ds.setTargetDataSource(new_sdds)

    org.springframework.jdbc.datasource.DataSourceTransactionManager new_tm = new org.springframework.jdbc.datasource.DataSourceTransactionManager()
    new_tm.setDataSource(new_ds);
    

    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

    // BOTH these need to be done :/
    config.setDataSource(new_ds)
    config.setTransactionManager(new_tm)
    config.setDatabaseSchema(tenant_id)
    config.setDatabaseTablePrefix(tenant_id+'.')
    config.setJobExecutorActivate(true)
    config.setProcessEngineName(tenant_id)
    config.setHistory(ProcessEngineConfiguration.HISTORY_FULL)
    config.setDatabaseSchemaUpdate('true')

    // println("config: ${config}");

    ProcessEngine processEngine = config.buildProcessEngine();
    RuntimeContainerDelegate.INSTANCE.get().registerProcessEngine(processEngine);
  }
}
