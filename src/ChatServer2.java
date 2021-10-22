//package lab10;

import java.io.*;
import java.net.*;

public class ChatServer2 implements Runnable {
    private static ServerSocket serSock = null;
    private static Socket sock = null;
    private static Thread thread = null;

    private static InputStream inpStream = null;
    private static OutputStream outStream = null;
    private BufferedReader recieveRead = null;
    private PrintWriter pwrite = null;

    public ChatServer2(int port) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
            serSock = new ServerSocket(port);
            System.out.println("Server started: " + serSock);
            startThread();
            System.out.println("Waiting for a client ...");
        }
        catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    @Override
    public void run() {
        try {
            sock = serSock.accept();
            System.out.println("Client accepted: " + serSock);
            inpStream = sock.getInputStream();
            outStream = sock.getOutputStream();

            // keyboard reading
            recieveRead = new BufferedReader(new InputStreamReader(System.in));
            pwrite = new PrintWriter(outStream, true);

            pwrite.println(recieveRead);
            open();
            boolean done = false;
            while (!done) {
                try {
                    String line = recieveRead.readLine();
                    System.out.println(line);

                    if (line == ".kill") {
                        thread.interrupt();
                        close();
                    }
                } catch(IOException ioe) {
                    System.out.println(ioe);
                }
            }
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void startThread() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void open() throws IOException {
        inpStream = new DataInputStream(new BufferedInputStream(sock.getInputStream()));
    }

    public void close() throws IOException {
        if (sock != null)
            sock.close();
        if (inpStream != null)
            inpStream.close();
    }
    /**
    serSock = new ServerSocket(port);
    System.out.println("Server ready for chatting");
    sock = serSock.accept();
    // reading from keyboard (keyRead object)
    BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
    // sending to client (pwrite object)
    OutputStream ostream = sock.getOutputStream();
    PrintWriter pwrite = new PrintWriter(ostream, true);

    // receiving from server ( receiveRead  object)
    InputStream istream = sock.getInputStream();
    BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

    String receiveMessage, sendMessage;
    while(true) {
    if((receiveMessage = receiveRead.readLine()) != null) {
    if (receiveMessage.equals(".bye")) {
    System.out.println(".bye sent to the server. \nQuiting..");
    break;
}
System.out.println(receiveMessage);
}
sendMessage = keyRead.readLine();
pwrite.println(sendMessage);
pwrite.flush();
}
} catch (IOException ioe) {
System.out.println(ioe);
}
}
*/

public static void main(String[] args) throws Exception {
    ChatServer2 server = null;
    if (args.length != 1) {
        System.out.println("  ERROR\n Proper usage: java ChatServer2 port");
    } else {
        server = new ChatServer2(Integer.parseInt(args[0]));
    }
}
}
