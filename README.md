# Agile Dev Starter
This is a simple sample application which can be used for different platforms, e.g. Cloud Foundry. It is a Spring Boot 
Java application which provides a simple REST API. Furthermore a database is used to store some data.

## Cloud Foundry
This is quick guide to deploy, run and manage this application on Cloud Foundry platform. It will show the basic Cloud 
Foundry commands to get started with Spring Boot and Cloud Foundry.

For more information about Cloud Foundry commands use `cf help <command>` (Cloud Foundry CLI) or consult the 
[Cloud Foundry document](https://docs.cloudfoundry.org/devguide/index.html).

### Deploy an application
The command `cf push` is used to deploy an application to Cloud Foundry. To ease the deployment configuration a 
`manifest.yaml` file can be used to set several deployment options. It is also possible to command parameters instead 
of a configuration file. As long as the file is named `manifest.yaml` it is not necessary to add the manifest parameter 
(`-f`) to the command. This is the default name and is picked up automatically.

Sample command using `manifest.yaml`:
```
cf push -p build/libs/agilestarterbundle-0.0.1-SNAPSHOT.jar
``` 

You can add the parameter `--no-start` so that the application is not started after a successful deployment.

### Show deployed apps
All deployed applications on Cloud Foundry can be listed using the `cf apps` command. Afterwards all applications with 
some further information are listed.

Furthermore details of one application can be shown using the `cf app <app>` command. It will show some more information 
about the application, e.g. route, memory, etc.

Sample command:
```
cf app agile-dev-starter
```

### Start, stop and restart an application
Applications can be started, stopped and restarted. The Cloud Foundry provides the commands `cf start <app>`, 
`cf stop <app>` and `cf restart <app>`. The usage of these commands is straightforward.

If the application are running with more instances there is a specific command `cf restart-app-instance <app> <instance_number>` 
to restart only a single instance of an application. This becomes handy when for example a Spring Cloud Config server 
is used to manage the application configuration. In this case the configuration can be changed and afterwards instance by 
instance can be restarted without downtime.

Sample command to restart the first instance:
```
cf restart-app-instance agile-dev-starter 0
```

### Delete an application
If an applications is obsolete it can be deleted. This can be achieved by using the `cf delete <app>` command. After 
executing this command it has to be confirmed.

### Add and remove a route to an application
Sometimes it is necessary to add another route to an application. This can be achieved using the 
`cf map-route <app> <domain> --hostname <hostname>` command. The hostname is a simple name which has no further restrictions. 
All available domains can be listed using the `cf domains` command.

Sample command:
```
cf map-route agile-dev-starter apps.internal --hostname my-app-route
```

The new route can be also removed from an application. The command is similar the aforementioned command. The command is 
`cf unmap-route <app> <domain> --hostname <hostname>`. Afterwards the route is removed from the application by Cloud Foundry. 

Sample command to remove created route:
```
cf unmap-route agile-dev-starter apps.internal --hostname my-app-route
```

**Note:** This route is not deleted. It is only removed from the application but still exists in Cloud Foundry. To delete 
a route the `cf delete-route <domain> --hostname <hostname>` command has to be executed.

Sample command ro delete created route:
```
cf delete-route apps.internal --hostname my-app-route
```

### Create and bind a marketplace service
Cloud Foundry has marketplace concept. The marketplace provides different platform services, e.g. databases, app scaler. 
These service can be bound to an application or so called service keys (see documentation for more information). To use 
a marketplace service three steps have to executed:

1. Create marketplace service
2. Bind marketplace service to application
3. Restart/restage application

First the marketplace service has to be created using the `cf create-service <marketplace-service-name> <plan> <custom-name>`. 
Each marketplace service has different plans. These plans define the usage pricing of the provided service.

Sample command to create a Postgres database service on Pivotal Cloud Foundry:
```
cf create-service elephantsql turtle agile-dev-starter-db
```

Now the marketplace service can be bound to an application with the `cf bind-service <app> <custom-name>` command.

Sample command to bind created marketplace service:
```
cf bind-service agile-dev-starter agile-dev-starter-db
```

After the binding the application container (droplet) has to be restarted or restaged. This depends on the created 
marketplace service. If a marketplace service only extends the environment of an application a restart is sufficient. 
Otherwise the application has to be restaged. During restaging a new application container is created. An example 
of such a marketplace service is New Relic on the Pivotal Cloud Foundry because an java agent is added to the application 
container. See the section regarding starting, stopping and restarting applications. For restaging the command 
`cf restage <app>` is used. 

### Create and bind a user-provided service
Beside marketplace services there are user-provided services. These are services which are created manually. The purpose 
of a user-provided service is to extend the application environment with properties, e.g. urls, credentials. The same steps 
as used for marketplace services are necessary to create a user-provided service. As aforementioned those services extend 
only the environment and thus, a restart of the application is sufficient. Only the creation of such a service is 
different. The command `cf create-user-provided-service <custom-name>` is used here.

There different types of user-provided services. See Cloud Foundry documentation for more information. This example is 
creating a user-provided service for credentials.

Sample command to create user-provided service for credentials:
```
cf create-user-provided-service agile-dev-starter-config-server -p "url, user, password"
``` 

Then all properties can be entered via CLI. After all properties are entered the user-provided service is created. The 
next step is to bind it to an application. The created user-provided service adds the entered parameters to the environment.

Example for created user-provided service:
```
{
    "binding_name": null,
    "credentials": {
     "password": "config-server-password",
     "url": "https://agile-dev-starter-config-server.com",
     "user": "config-server-user"
    },
    "instance_name": "agile-dev-starter-config-server",
    "label": "user-provided",
    "name": "agile-dev-starter-config-server",
    "syslog_drain_url": "",
    "tags": [],
    "volume_mounts": []
   }
```

### Set, remove and show application environment
Beside services it is possible to add simple properties to the environment. For this the command `cf set-env <app> <name> <value>` 
is used.

Sample command to add an environment variable:
```
cf set-env agile-dev-starter platform cf
```

Added environment variables can be removed using the `cf unset-env <app> <name>` command.

Sample command to remove an environment variable:
```
cf unset-env agile-dev-starter platform
```

As aforementioned marketplace and user-provided services also extend the environment of an application. Spring Boot 
facilitates the Cloud Foundry environment so that it can be used within the application, e.g. in configuration files.

After adding services and environment variable the environment of an application can be shown. It contains all added 
variables and additional information which are added by Cloud Foundry. The command `cf env <app>` has to be executed to 
show the environment of an application.
