package com.opendaylight.hello.impl;

import java.util.Collection;
import java.util.Collections;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.AbstractBindingAwareProvider;
import org.opendaylight.yangtools.yang.binding.RpcService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloProvider extends AbstractBindingAwareProvider implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(HelloProvider.class);
    private RpcRegistration<HelloWorldService> helloService;

    public HelloProvider() {
        LOG.info("HelloProvider Constructor Called!");
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("HelloProvider Session Initiated");
        helloService = session.addRpcImplementation(HelloWorldService.class, new HelloWorldImpl());
    }

    @Override
    public void close() throws Exception {
        LOG.info("HelloProvider Closed");
        helloService.close();
    }

    public Collection<? extends RpcService> getImplementations() {
        return Collections.emptySet();
    }

    public Collection<? extends ProviderFunctionality> getFunctionality() {
        return Collections.emptySet();
    }
}