package com.opendaylight.hello.impl;

//import java.util.ArrayList;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.Futures;

import org.opendaylight.controller.sal.binding.api.data.DataProviderService;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutputBuilder;
//import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;

public class HelloWorldImpl implements HelloWorldService {
    @SuppressWarnings("deprecation")
    DataProviderService dataService;

    public void setDataService(DataProviderService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Future<RpcResult<HelloWorldOutput>> helloWorld(HelloWorldInput input) {
        HelloWorldOutputBuilder helloBuilder = new HelloWorldOutputBuilder();
        helloBuilder.setGreating("Hello " + input.getName());

        /*InstanceIdentifier<Nodes> nodesIdentifier = InstanceIdentifier.builder(Nodes.class).build();
        @SuppressWarnings("deprecation")
        ArrayList<Node> nodes = (ArrayList<Node>) ((Nodes)dataService.readOperationalData(nodesIdentifier)).getNode();
        Node node0 = nodes.get(0);
        NodeKey node0Key = node0.getKey();
        NodeId node0Id = node0Key.getId();
        //String nod0IdStr = node0Id.getValue();*/

        return Futures.immediateFuture( RpcResultBuilder.<HelloWorldOutput> success(helloBuilder.build()).build() );
    }
}