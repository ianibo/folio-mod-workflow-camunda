# folio-mod-workflow-camunda
See 

Generated template project with start.spring.io

Much useful stuff here:
https://github.com/camunda/camunda-bpm-examples/tree/master/multi-tenancy/schema-isolation
https://github.com/camunda/camunda-bpm-examples/tree/master/deployment/embedded-spring-rest
https://blog.bernd-ruecker.com/use-camunda-without-touching-java-and-get-an-easy-to-use-rest-based-orchestration-and-workflow-7bdf25ac198e
https://github.com/camunda/camunda-bpm-webapp
https://github.com/camunda/camunda-bpm-examples
https://docs.camunda.org/manual/7.5/user-guide/process-engine/multi-tenancy/
http://juel.sourceforge.net/

Visit
http://localhost:8080/modwf/engine/default/process-definition
http://localhost:8080/modwf/process-instance/count
after deploy

What needs to be done


Modify ProcessEngineProducer as in the multi-tenancy/schema-isolation project above to inject tenant-specific engines into incoming web requests

Programatically create tenants as per https://forum.camunda.org/t/multi-tenancy-programmatically-create-tenant/2876/5

THe demo

Make sure you've got a postgres db

    have a hostname pghost for easy aliasing of postgres server. The JDBC connection is set for this host, just alias localhost to run locally.
    
    psql -U postgres -h localhost 

    // DROP DATABASE foliodev;
    CREATE DATABASE foliodev;
    CREATE USER folio WITH PASSWORD 'folio';
    GRANT ALL PRIVILEGES ON DATABASE foliodev to folio;

As folio user

    psql -U folio -h localhost 
    create schema camunda_default;
    create schema modwf_diku;


REST Interactions

// List all process definitions

http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition

### Start a GenricPatronSupportProcess - Simple

curl -H "Content-Type: application/json" -X POST \
-d '{"variables":{"someData" : {"value" : "someValue", "type": "String"}},"businessKey" : "12345","PatronName":"Fred Flintstone","PatronId":"12349876","PatronQuery":"Does the patron query system work ok?"}' \
http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition/key/GenericPatronSupportProcess/start

### Start a GenricPatronSupportProcess - Pre-Classified

curl -H "Content-Type: application/json" -X POST \
-d '{"variables":{"someData" : {"value" : "someValue", "type": "String"}},"businessKey" : "12345","PatronName":"Fred Flintstone","PatronId":"12349876","PatronQuery":"This is a general patron support query","classification":"General"}' \
http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition/key/GenericPatronSupportProcess/start
