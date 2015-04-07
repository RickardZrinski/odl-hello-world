package org.opendaylight.controller.sample.hello_world.provider;

import java.util.ArrayList;
//import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
//import org.opendaylight.controller.md.sal.binding.api.ReadOnlyTransaction;
//import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Node;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.Paths;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.PathsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.PathsKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.paths.Links;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.paths.LinksBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.get.all.paths.output.paths.LinksKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.testing.output.Things;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.testing.output.ThingsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.testing.output.ThingsKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.GetAllPathsInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.GetAllPathsOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.GetAllPathsOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.HelloWorldOutputBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.TestingInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.TestingOutput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.hello.world.rev150105.TestingOutputBuilder;
//import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldImpl implements HelloWorldService, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(HelloWorldImpl.class);
    private DataBroker dataBroker;

    public void setDataBroker(DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    @Override
    public void close() throws Exception {
        LOG.info("HelloWorldImpl Close!");
    }

    @Override
    public Future<RpcResult<HelloWorldOutput>> helloWorld(HelloWorldInput input) {
        HelloWorldOutputBuilder helloBuilder = new HelloWorldOutputBuilder();
        helloBuilder.setGreating("Hello " + input.getName());
        return Futures.immediateFuture( RpcResultBuilder.<HelloWorldOutput> success(helloBuilder.build()).build() );
    }

    @Override
    public Future<RpcResult<GetAllPathsOutput>> getAllPaths(GetAllPathsInput input) {
        // Initiate builders
        GetAllPathsOutputBuilder outputBuilder = new GetAllPathsOutputBuilder();
        PathsBuilder pathsBuilder = new PathsBuilder();
        LinksBuilder linksBuilder = new LinksBuilder();

        // Set MAC addresses in output
        outputBuilder.setNode1Mac(input.getNode1Mac());
        outputBuilder.setNode2Mac(input.getNode2Mac());

        // Add Paths to list
        ArrayList<Paths> pathsList = new ArrayList<Paths>();

        // Add a path to the Paths list
        pathsBuilder.setPathId((long) 0);
        pathsBuilder.setKey(new PathsKey((long) 0));

        ArrayList<Links> linksList1 = new ArrayList<Links>();
        linksBuilder.setLinkId("link-id-1");
        linksBuilder.setKey(new LinksKey("link-id-1"));
        Links links = linksBuilder.build();
        linksList1.add(links);

        pathsBuilder.setLinks(linksList1);
        Paths paths = pathsBuilder.build();
        pathsList.add(paths);

        // Set Paths list in output
        outputBuilder.setPaths(pathsList);

        // Return output
        return Futures.immediateFuture( RpcResultBuilder.<GetAllPathsOutput> success(outputBuilder.build()).build() );
    }

    @Override
    public Future<RpcResult<TestingOutput>> testing(TestingInput input) {
        TestingOutputBuilder outputBuilder = new TestingOutputBuilder();
        outputBuilder.setInput1("input-test-1");
        outputBuilder.setInput1(input.getInput2());

        ArrayList<Things> thingsList = new ArrayList<Things>();
        ThingsBuilder thingsBuilder = new ThingsBuilder();
        thingsBuilder.setKey(new ThingsKey((long) 0));
        thingsBuilder.setThingId((long) 0);
        Things thing1 = thingsBuilder.build();
        thingsBuilder.setKey(new ThingsKey((long) 1));
        thingsBuilder.setThingId((long) 1);
        Things thing2 = thingsBuilder.build();
        thingsList.add(thing1);
        thingsList.add(thing2);
        outputBuilder.setThings(thingsList);

        // Attempt to get the data needed for finding all possible paths
        /*InstanceIdentifier<Nodes> nodes_iid = InstanceIdentifier.builder(Nodes.class).build();

        ReadOnlyTransaction rTx = dataBroker.newReadOnlyTransaction();
        try {
            Optional<Nodes> data = rTx.read(LogicalDatastoreType.OPERATIONAL, nodes_iid).get();
            if(data.isPresent()) {
                Nodes nodes = data.get();
                ArrayList<Node> nodeList = (ArrayList) nodes.getNode();
                for(int i = 0;i < nodeList.size();i++) {
                    Node node = nodeList.get(i);
                    node.getId();
                }
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        return Futures.immediateFuture( RpcResultBuilder.<TestingOutput> success(outputBuilder.build()).build() );
    }
}