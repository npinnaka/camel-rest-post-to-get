package com.pinnaka.camelrest.routes;

import com.pinnaka.camelrest.model.Employee;
import com.pinnaka.camelrest.model.Holder;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class FromPostToGet extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/employees")
                .post()
                .to("direct:saveEmployee")
                .get("/")
                .to("direct:getEmployees")
                .get("/{id}")
                .to("direct:getEmployee");

        from("direct:saveEmployee")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .marshal().json(JsonLibrary.Jackson, Employee.class)
                .to("http://localhost:9000/employees?bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Employee.class)
                .log("${body}")
                .choice().when(exchange -> {
                    Employee emp = exchange.getMessage().getBody(Employee.class);
                    // Change the To route based on the Employee ID
                    return emp.getEmpId() % 2 == 0;
                })
                .process(exchange -> {
                    Employee emp = exchange.getMessage().getBody(Employee.class);
                    exchange.getMessage().setBody("");
                    exchange.getMessage().setHeader("id", constant(emp.getEmpId()));
                })
                .to("direct:getEmployee")
                .otherwise()
                .to("direct:getEmployees");

        from("direct:getEmployees")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("http://localhost:9000/employees?bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Holder.class)
                .log("${body}");


        from("direct:getEmployee")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("http://localhost:9000/employees/${header.id}?bridgeEndpoint=true")
                .unmarshal().json(JsonLibrary.Jackson, Employee.class)
                .log("${body}");
    }
}
