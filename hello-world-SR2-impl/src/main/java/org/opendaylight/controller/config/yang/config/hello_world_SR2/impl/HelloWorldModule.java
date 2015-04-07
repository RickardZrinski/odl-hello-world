package org.opendaylight.controller.config.yang.config.hello_world_SR2.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sample.hello_world.provider.HelloWorldImpl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldModule extends org.opendaylight.controller.config.yang.config.hello_world_SR2.impl.AbstractHelloWorldModule {
    private static final Logger log = LoggerFactory.getLogger(HelloWorldModule.class);

    public HelloWorldModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public HelloWorldModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.controller.config.yang.config.hello_world_SR2.impl.HelloWorldModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        log.info("Hello World Provider (instance {}) torn down.", this);

        final HelloWorldImpl helloWorldImpl = new HelloWorldImpl();

        DataBroker dataBrokerService = getDataBrokerDependency();
        helloWorldImpl.setDataBroker(dataBrokerService);

        final RpcRegistration<HelloWorldService> rpcRegistration = getRpcRegistryDependency()
                .addRpcImplementation(HelloWorldService.class, helloWorldImpl);

        final class AutoCloseableHelloWorld implements AutoCloseable {
            @Override
            public void close() throws Exception {
                rpcRegistration.close();
                log.info("Hello World Provider (instance {}) torn down.", this);
            }
        }

        AutoCloseable ret = new AutoCloseableHelloWorld();
        log.info("Hello World provider (instance {}) initialized.", ret);
        return ret;
    }
}
