package com.opendaylight.hello.impl;

import java.util.concurrent.Future;
import com.google.common.util.concurrent.Futures;

import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutputBuilder;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

public class HelloWorldImpl implements HelloWorldService {

    @Override
    public Future<RpcResult<HelloWorldOutput>> helloWorld(HelloWorldInput input) {
        HelloWorldOutputBuilder helloBuilder = new HelloWorldOutputBuilder();
        helloBuilder.setGreating("Hello " + input.getName());
        return Futures.immediateFuture( RpcResultBuilder.<HelloWorldOutput> success(helloBuilder.build()).build() );
    }
}