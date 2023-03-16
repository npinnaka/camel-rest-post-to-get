package com.pinnaka.camelrest.processors;

import com.pinnaka.camelrest.model.Holder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class HolderProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        Holder holder = exchange.getMessage().getBody(Holder.class);
        System.out.println(holder);
    }
}
