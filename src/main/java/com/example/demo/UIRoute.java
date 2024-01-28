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

import com.example.demo.bean.*;

@Component
public class UIRoute
  extends RouteBuilder {

  private String contextPath = "";

  @Override
  public void configure() throws Exception {

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

    rest("/ui").description("User REST service")
      .get("/health")
      .consumes("application/json")
      .produces("application/json")
      .to("direct:uihealthCheck");

    from("direct:uihealthCheck").routeId("ui-health-check")
      .bean(UIBean.class);

  }
}


