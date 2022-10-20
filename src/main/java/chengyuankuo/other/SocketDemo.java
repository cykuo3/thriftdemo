package chengyuankuo.other;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(8888));
            Socket socket = serverSocket.accept();
            doSomeThing(socket);
        }
    }

    private static void doSomeThing(Socket socket) {
        //......
    }
}
