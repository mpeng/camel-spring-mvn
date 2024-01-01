package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:foo")
          .id("Steve")
          .to("log:bar");

        from("direct:a")
          .id("Johnson")
          .choice()
          .when(simple("${header.foo} == 'bar'"))
          .log("bar")
          .to("direct:b")
          .when(simple("${header.foo} == 'cheese'"))
          .log("cheese")
          .to("direct:c")
          .otherwise()
          .log("otherwise")
          .to("direct:d");

        from("timer:foo?period={{myPeriod}}")
          .id("Kevin")
          .transform().simple("${random(20)}")
          .choice()
          .when().simple("${body} > 10")
          .log("high ${body}")
          .when().simple("${body} > 5")
          .log("med ${body}")
          .otherwise()
          .log("low ${body}");


        from("timer:hello?period=1000")
          .transform(simple("Random number ${random(0,100)}"))
          .to("spring-rabbitmq:foo?routingKey=mykey");

        from("timer:hello?period=2000")
          .transform(simple("Bigger random number ${random(100,200)}"))
          .to("spring-rabbitmq:foo?routingKey=mykey");

        from("spring-rabbitmq:foo?queues=myqueue&routingKey=mykey")
          .log("From RabbitMQ: ${body}");
    }
}

