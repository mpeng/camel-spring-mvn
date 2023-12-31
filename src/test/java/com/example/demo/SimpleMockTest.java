package com.example.demo;

import org.apache.camel.Body;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class SimpleMockTest extends CamelTestSupport {

  public static final String PREFIX = "Translated: ";

  @Test
  public void testMock() throws Exception {
    getMockEndpoint("mock:ABC").expectedBodiesReceived(PREFIX + "Hello World");

    template.sendBody("direct:start1", "Hello World");

    MockEndpoint.assertIsSatisfied(context);
  }

  @Override
  protected RoutesBuilder createRouteBuilder() {
    return new RouteBuilder() {
      @Override
      public void configure() {
        from("direct:start1")
          .bean(MyMessageTranslator.class, "translateMessage")
          .log( "---Hello You---")
          .to("mock:ABC");
      }
    };
  }

}

class MyMessageTranslator {

  static public String translateMessage(@Body String input) {
    // Translate the message (in this case, simply append "Translated: " to the input)
    return SimpleMockTest.PREFIX + input;
  }
}
