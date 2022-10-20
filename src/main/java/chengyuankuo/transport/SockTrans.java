package chengyuankuo.transport;

import org.apache.thrift.transport.TSimpleFileTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.nio.charset.StandardCharsets;

public class SockTrans {
    public static void main(String[] args) throws TTransportException {
        //Display web page
        TTransport trans = new TSocket("thrift.apache.org", 80);
        final String msg = "GET / \n";
        trans.open();
        trans.write(msg.getBytes());
        trans.flush();
        readTrans(trans);
        trans.close();
        //Display file
        trans = new TSimpleFileTransport("SockTrans.java");
        trans.open();
        readTrans(trans);
        trans.close();
    }

    private static void readTrans(TTransport trans) {
        int size = 1024 * 8;
        byte[] buf = new byte[size];
        while (true){
            try {
                int byteSize = trans.read(buf, 0, size);
                System.out.println(new String(buf, 0, byteSize, StandardCharsets.UTF_8));
                if (byteSize <= 0){
                    break;
                }
            } catch (TTransportException e) {
                e.printStackTrace();
                break;
            }
        }

    }

}
