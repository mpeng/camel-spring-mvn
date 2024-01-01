package com.example.demo;

import org.apache.camel.Body;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import com.example.demo.bean.MessageTranslatorBean;


public class SimpleMockTest extends CamelTestSupport {

  public static final String PREFIX = "Translated: ";

  @Test
  public void testMock() throws Exception {

    System.out.println( "============Camel Context=========" );
    System.out.println( context );
    System.out.println( context() );
    System.out.println( context == context() );
    context.getEndpoints().stream().map( e -> e.getEndpointUri()).forEach(System.out::println);

    getMockEndpoint("mock:Michael").expectedBodiesReceived(PREFIX + "Hello World");
    getMockEndpoint("mock:Christie").expectedBodiesReceived(PREFIX + "Hello World");
    getMockEndpoint("mock:Richard").expectedBodiesReceived(PREFIX + "Hello World");
    getMockEndpoint("mock:Charlie").expectedBodiesReceived(PREFIX + "Hello World");

    template.sendBody("direct:Michael", "Hello World");
    template.sendBody("direct:Christie", "Hello World");
    template.sendBody("direct:Richard", "Hello World");
    template.sendBody("direct:Charlie", "Hello World");


    System.out.println( "-------Begin Body-------------" );
    template.sendBody("direct:Json", "Huge Body");
    System.out.println( "---------End Body-----------" );

    System.out.println( "-------Begin Header-------------" );
    template.sendBodyAndHeader("direct:Json", "Big Body", "foo", "bar");
    System.out.println( "---------End Header-----------" );


    MockEndpoint.assertIsSatisfied(context);
  }

  @Override
  protected RoutesBuilder createRouteBuilder() {
    return new RouteBuilder() {
      @Override
      public void configure() {
        from("direct:Michael")
          .id("Michael")
          .bean(MyMessageTranslator.class, "translateMessage")
          .log( "---Hello Michael---")
          .to("mock:Michael");

        from("direct:Christie")
          .id("Christie")
          .bean(MessageTranslatorBean.class, "translateMessage")
          .log( "---Hello Christie---")
          .to("mock:Christie");

        from("direct:Richard")
          .id("Richard")
          .bean(MessageTranslatorBean.class, "translateMessage")
          .log( "---Hello Richard---")
          .to("mock:Richard");

        from("direct:Charlie")
          .id("Charlie")
          .bean(MyMessageTranslator.class, "translateMessage")
          .log( "---Hello Charlie---")
          .to("mock:Charlie");


        from("direct:Json")
          .id("Json")
          .log( "---Hello Json---")
          .log( "---${body}--${header.foo}---")
          .choice()
          .when().simple("${header.foo} == 'bar'")
          .log( "The header value is bar" )
          .log(LoggingLevel.INFO, "test", "${body} and --${header.foo}")
          .to("mock:Json")
          .otherwise()
          .log("The header value is NOT bar")
          .log(LoggingLevel.INFO, "test", "${body} and --${header.foo}")
          .to("mock:Json");

        from("timer:foo?period=2000")
          .id("Kevin")
          .transform().simple("${random(20)}")
          .choice()
          .when().simple("${body} > 10")
          .log("high ${body}")
          .when().simple("${body} > 5")
          .log("med ${body}")
          .otherwise()
          .log("low ${body}");
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
