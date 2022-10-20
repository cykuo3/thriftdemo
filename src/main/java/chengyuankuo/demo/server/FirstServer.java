package chengyuankuo.demo.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import chengyuankuo.demo.DemoService;
import chengyuankuo.demo.DemoServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengyuankuo
 */
@Slf4j
public class FirstServer {

    static final int PORT = 8888;

    public static void main(String[] args) {
        startSimpleServer();
    }

    private static void startSimpleServer() {
        try {
            TServerTransport serverTransport = new TServerSocket(PORT);
            DemoService.Processor<DemoServiceImpl> processor = new DemoService.Processor<>(new DemoServiceImpl());

            //二进制协议
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();

            TServer server =
                    new TSimpleServer(new TServer.Args(serverTransport).inputProtocolFactory(factory).processor(processor));
            log.info("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void startNonblockingServer() {
        try {
            TNonblockingServerSocket nonblockingServerSocket = new TNonblockingServerSocket(PORT);
            DemoService.Processor<DemoServiceImpl> processor = new DemoService.Processor<>(new DemoServiceImpl());

            //二进制协议
            TBinaryProtocol.Factory factory = new TBinaryProtocol.Factory();

            TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(nonblockingServerSocket);

            TThreadedSelectorServer server =
                    new TThreadedSelectorServer(args.inputProtocolFactory(factory).processor(processor).workerThreads(20));
            server.serve();
            log.info("Starting the nonblocking server...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
