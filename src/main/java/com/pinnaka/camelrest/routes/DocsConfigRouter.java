package com.pinnaka.camelrest.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class DocsConfigRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .apiContextPath("/docs")
                .dataFormatProperty("prettyPrint", "true")
                .apiProperty("api.title", "Camel Rest APIs")
                .apiProperty("api.version", "1.0")
        ;
    }
}
