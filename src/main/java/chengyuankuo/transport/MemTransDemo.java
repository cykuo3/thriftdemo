package chengyuankuo.transport;

import org.apache.thrift.transport.TMemoryBuffer;
import org.apache.thrift.transport.TTransportException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import chengyuankuo.common.Trade;

/**
 * TMemoryBuffer测试类
 * @author chengyuankuo
 */
public class MemTransDemo {

    public static void main(String[] args) throws TTransportException, IOException, ClassNotFoundException {
        try (TMemoryBuffer transport = new TMemoryBuffer(4096)) {
            Trade trade = new Trade();
            trade.symbol = "F";
            trade.price = 13.10;
            trade.size = 2500;
            transport.write(obj2byte(trade));
            byte[] buf = new byte[4096];
            transport.read(buf, 0, buf.length);
            Trade tradeRead = (Trade) byte2obj(buf);
            System.out.println(tradeRead);
        }
    }

    private static byte[] obj2byte(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        return baos.toByteArray();
    }

    private static Object byte2obj(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(arr);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}
