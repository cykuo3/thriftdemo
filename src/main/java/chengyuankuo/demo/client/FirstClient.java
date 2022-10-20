package chengyuankuo.demo.client;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFramedTransport;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import chengyuankuo.demo.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chengyuankuo
 */
@Slf4j
public class FirstClient {

    static final String SERVER_IP = "localhost";
    static final int SERVER_PORT = 8888;

    static final int TIMEOUT = 2000;

    static final ExecutorService es = Executors.newFixedThreadPool(50);

    public static void main(String[] args) throws TException, IOException, InterruptedException {
        simpleInvoke();
    }

    private static void asyncInvoke() throws IOException, TException, InterruptedException {
        log.info("async call start...");
        TAsyncClientManager clientManager = new TAsyncClientManager();
        //创建并使用非阻塞传输层
        TNonblockingTransport transport = new TNonblockingSocket(SERVER_IP, SERVER_PORT);
        //创建二进制传输协议工厂
        TProtocolFactory protocol = new TBinaryProtocol.Factory();
        //异步客户端关联异步二进制传输协议工厂protocol，异步客户端管理器clientManager，非阻塞传输层transport
        DemoService.AsyncClient demoService = new DemoService.AsyncClient(protocol, clientManager, transport);
        AsyncMethodCallback<Integer> cb = new AsyncMethodCallback<Integer>() {
            @Override
            public void onComplete(Integer response) {
                log.info("complete res={}",response);
            }

            @Override
            public void onError(Exception exception) {
                log.error("error occur",exception);
            }
        };
        demoService.getAge("小红",cb);
        Thread.sleep(10000);
        log.info("async call finish...");
    }

    private static void framedInvoke() {
        try (TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT))) {
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            DemoService.Client demoService = new DemoService.Client(protocol);
            transport.open();

            String name = "小红";
            int age = demoService.getAge(name);
            log.info("{}.age={}", name, age);

        } catch (TException e) {
            e.printStackTrace();
        }
    }

    private static void simpleInvoke() {
        try (TTransport transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT)) {
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            DemoService.Client demoService = new DemoService.Client(protocol);
            transport.open();

            String name = "小红";
            int age = demoService.getAge(name);
            log.info("{}.age={}", name, age);

        } catch (TException e) {
            e.printStackTrace();
        }
    }

}
