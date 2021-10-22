//package lab10;

import java.io.*;
import java.net.*;

public class ChatServer {
    private static ServerSocket serSock = null;
    private static Socket sock = null;
    private static DataInputStream inpStream = null;

    public ChatServer(int port) {
        try {
            System.out.println("Binding to port " + port + ", please wait  ...");
             serSock = new ServerSocket(port);
             System.out.println("Server started: " + serSock);
             System.out.println("Waiting for a client ...");
             sock = serSock.accept();
             System.out.println("Client accepted: " + serSock);
             open();
             boolean done = false;
             while (!done)
             {  try
                {  String line = inpStream.readUTF();
                   System.out.println(line);
                   done = line.equals(".bye");
                }
                catch(IOException ioe)
                {  done = true;
                }
             }
             close();
          }
          catch(IOException ioe) {
              System.out.println(ioe);
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
        ChatServer server = null;
        if (args.length != 1) {
            System.out.println("  ERROR\n Proper usage: java ChatServer port");
        } else {
            server = new ChatServer(Integer.parseInt(args[0]));
        }
    }
}
