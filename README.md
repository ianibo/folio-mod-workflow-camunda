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


# REST Interactions

// List all process definitions

http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition

## Start a GenricPatronSupportProcess - Simple

    curl -H "Content-Type: application/json" -X POST \
    -d '
    {
      "variables":{
        "someData" : {"value" : "someValue", "type": "String"}, 
        "PatronName" : { "value" : "Fred Flintstone", "type": "String" },
        "PatronId" : { "value" : "123456753334", "type": "String" },
        "PatronQuery" : { "value" : "This is a general patron support query which has not been pre-classified", "type": "String" }
      },
      "businessKey" : "12344475"
    }' \
    http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition/key/GenericPatronSupportProcess/start

## Start a GenricPatronSupportProcess - Pre-Classified

    curl -H "Content-Type: application/json" -X POST \
    -d '
    {
      "variables":{
        "someData" : {"value" : "someValue", "type": "String"}, 
        "PatronName" : { "value" : "Fred Flintstone", "type": "String" },
        "PatronId" : { "value" : "123456753334", "type": "String" },
        "PatronQuery" : { "value" : "This is a general patron support query which has been pre-classified", "type": "String" },
        "classification" : { "value" : "General", "type": "String" }
      },
      "businessKey" : "12345"
    }' \
    http://localhost:8080/modwf/engine/modwf_diku_uk/process-definition/key/GenericPatronSupportProcess/start

## List all tasks currently open for GenericPatronSupportProcess

More info here:: https://docs.camunda.org/manual/latest/reference/rest/task/get-query/#example

http://localhost:8080/modwf/engine/modwf_diku_uk/task?processDefinitionKey=GenericPatronSupportProcess&maxResults=100
http://localhost:8080/modwf/engine/modwf_diku_uk/task?maxResults=100


Extract the task ID and use calls like

http://localhost:8080/modwf/engine/modwf_diku_uk/task/#TASKID##
http://localhost:8080/modwf/engine/modwf_diku_uk/task/#TASKID##/variables

Use &candidateUser=freda to list only tasks that user freda (Or one of freda's groups) are allowed to lock.

## Groups

    curl -H "Content-Type: application/json" -X POST -d '
    {
      "id":"Patron Support",
      "name":"Patron Support",
      "type":"Organizational Unit"
    }' http://localhost:8080/modwf/engine/modwf_diku_uk/group/create

## Users

    https://docs.camunda.org/manual/latest/reference/rest/user/post-create/

    curl -H "Content-Type: application/json" -X POST -d '
    {
      "profile": {
        "id": "8324982777388837383",
        "firstName":"Freda",
        "lastName":"Jones",
        "email":"fread@some.domain"
      }
    } ' \
    http://localhost:8080/modwf/engine/modwf_diku_uk/user/create

## Group Membership

Note %20 escaping of space

    curl -H "Content-Type: application/json" -X PUT "http://localhost:8080/modwf/engine/modwf_diku_uk/group/Patron%20Support/members/8324982777388837383"

## List ALL tasks available for user freda

http://localhost:8080/modwf/engine/modwf_diku_uk/task?candidateUser=8324982777388837383


#
