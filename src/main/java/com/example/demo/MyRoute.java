package com.example.demo;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import com.example.demo.bean.*;

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


        from("timer:foo?period={{myPeriod}}")
          .id("Justin")
          .setHeader("foo", constant("bar"))
          .transform().simple("aaa")
          .choice()
          .when(simple("${header.foo} == 'bar'"))
          .log("Header value is bar. It is ${header.foo}")
          .to("direct:b")
          .to("spring-rabbitmq:foo?routingKey=mykey")
          .otherwise()
          .log("Header value is not bar. It is ${header.foo}")
          .log("-------${body}------------")
          .log("-------${header.foo}------------!!!")  //ToDo
          .to("direct:c");

        from( "direct:b")
          .id( "Route B")
          .log( "Bar: ${body} and ${header.foo}");

        from( "direct:c")
          .id( "Route C")
          .log( "Non Bar: ${body}");

        from("direct:start")
          .id( "Original Route" )
          .transform().simple("Tapped Message ?????")
          .log( "1=============${body}=============")
          .to("log:foo")
          .wireTap("direct:tap")
          .id( "Tapped Route" )
          .log( "2=============${body}=============")
          .to("log:result");

        from("direct:tap")
          .delay(1000).setBody().constant("Tapped ???")
          .to("log:tap")
          .log( "${body}");

        int a = 2;
        a = +4;
        System.out.println( "aaaaaaaaaaaaaaaa=" + a);


        from("timer:foo?period={{myPeriod}}")
          //.to("log:fast?level=OFF")
          .to("direct:slow");


        from("direct:slow")
          //.to("log:slow?level=OFF")
          .bean(MyBean.class, "hello")
          .log("+++++++++++${body}+++++++++++");
          //.to("log:slow?level=OFF");

        from("spring-rabbitmq:foo?queues=myqueue&routingKey=mykey")
          .log("");
          //.log("======From RabbitMQ: ${body}======");


        /*
        from("timer:hello?period=1000")
          .transform(simple("Random number ${random(0,100)}"))
          .to("spring-rabbitmq:foo?routingKey=mykey");

        from("timer:hello?period=2000")
          .transform(simple("Bigger random number ${random(100,200)}"))
          .to("spring-rabbitmq:foo?routingKey=mykey");

        from("spring-rabbitmq:foo?queues=myqueue&routingKey=mykey")
          .log("From RabbitMQ: ${body}");
         */

    }
}

