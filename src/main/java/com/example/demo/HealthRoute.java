package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

/**
 * A simple Camel REST DSL route with OpenApi API documentation.
 */


//@ApplicationScoped
@Component
public class HealthRoute
  extends RouteBuilder {

    //@Inject
    //@Uri("direct:healthCheckEndpoint")
    //Endpoint healthCheckEndpoint;
    private String contextPath = "";

    @Override
    public void configure() throws Exception {

        /*
        restConfiguration()
          .component("undertow").contextPath("/")
          .bindingMode(RestBindingMode.json)
          .apiContextPath("/api-docs")
          .enableCORS(true).apiVendorExtension(true)
          .apiProperty("base.path", "/icms")
          .apiProperty("api.title", "ICMS REST API");
         */

        restConfiguration()
          .component("undertow")
          .bindingMode(RestBindingMode.json) // auto: No type converter // off: No type converter // json: ?? // json_xml: XML DataFormat jaxb not found
          .dataFormatProperty("prettyPrint", "true")
          .enableCORS(true)
          .port("8082")
          .contextPath("/api")
          .apiContextPath("/api-doc")
          .apiProperty("api.title", "User API")
          .apiProperty("api.version", "1.0.0");

        /*
        rest("/users").description("User REST service")
          .consumes("application/json")
          .produces("application/json")

          .get().description("Find all users").outType(User[].class)
          .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
          .to("bean:userService?method=findUsers")

          .get("/{id}").description("Find user by ID")
          .outType(User.class)
          .param().name("id").type(path).description("The ID of the user").dataType("integer").endParam()
          .responseMessage().code(200).message("User successfully returned").endResponseMessage()
          .to("bean:userService?method=findUser(${header.id})")

          .put("/{id}").description("Update a user").type(User.class)
          .param().name("id").type(path).description("The ID of the user to update").dataType("integer").endParam()
          .param().name("body").type(body).description("The user to update").endParam()
          .responseMessage().code(204).message("User successfully updated").endResponseMessage()
          .to("direct:update-user");

        from("direct:update-user")
          .to("bean:userService?method=updateUser")
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
          .setBody(constant(""));
        */
        //rest("/api3").get("/health").produces("application/json").to("direct:healthCheckEndpoint");

        rest("/users").description("User REST service")
          .produces("application/json")
          .get().description("Find all users").outType(User[].class)
          .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
          .to("bean:userService?method=findUsers");



        /*
        rest("/users").description("User REST service")
          .get("/health")
          .consumes("application/json")
          .produces("application/json")
          .to("direct:healthCheckEndpoint");

        from("direct:healthCheckEndpoint").routeId("health-check-events")
          .log("!!!!!!!!!!!!!!!!!!!!!")
          .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
          //.setBody(constant("{'Message': 'Hello world'}"));
          //.log("!!!!!!!!!!!!!!!!!!!!!")
          //.bean(HealthBean.class);
        */
    }
}

