package sender;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketSender implements DataSender<String> {
    static ArrayList<PrintWriter> outs = new ArrayList();

    static ServerSocket serverSocket;
    Suport s = new Suport();
    Thread t = new Thread(s);

    public SocketSender() {
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.start();
    }

    int j = 100000;

    @Override
    public void send(String data) {
       System.out.println(data);
        for (int i = 0; i < outs.size(); i++) {
            if (j == 0) {
                System.out.println("send 100000 masages");
                j = 100000;
            }
            j--;
            outs.get(i).println(data);

        }
//        for (PrintWriter out : outs) {
//            out.println(data);
//        }
    }

    //help to send data to all clients of Server
    class Suport extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Socket fromserver = serverSocket.accept();
                    outs.add(new PrintWriter(fromserver.getOutputStream(), true));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

