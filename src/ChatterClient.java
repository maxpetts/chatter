//package lab10;

import java.io.*;
import java.net.*;
import java.lang.Integer;

public class ChatterClient {

    private static Socket sock = null;
    private static InputStream inpStream = null;
    private static OutputStream outStream = null;

    private BufferedReader recieveRead = null;
    private PrintWriter pwrite = null;

    public static void main(String[] args) {
        ChatterClient client;

        if (args.length == 2) {
            String host = args[0];
            String port = args[1];
            client = new ChatterClient(host, Integer.parseInt(port));
        } else {
            System.out.println("  ERROR\nProper Usage: java ChatterClient host port");
        }
    }

    public ChatterClient(String host, int port) {
        System.out.println("Connecting to " + port + "\n Please wait..");
        try {
            sock = new Socket(host, port);
            inpStream = sock.getInputStream();
            outStream = sock.getOutputStream();

            // keyboard reading
            recieveRead = new BufferedReader(new InputStreamReader(System.in));
            pwrite = new PrintWriter(outStream, true);

            System.out.println("Connected to socket. Host: " + sock.getInetAddress() + " Port: " + sock.getPort());
            System.out.println("Type away. Enter to send, also: .bye to quit");
        } catch(UnknownHostException uhe) {
            System.out.println("  ERROR\nHost unknown: " + uhe.getMessage());
        } catch(IOException ioe) {
            System.out.println("  ERROR\nUnexpected exception: " + ioe.getMessage());
        } catch(NullPointerException npe) {
            System.out.println("  ERROR\nNull Pointer Exception. Are you sure the server is running?");
        }
        String line = "";
        while (!line.equals(".bye")) {
            try {
                line = recieveRead.readLine();
                pwrite.println(line);
                pwrite.flush();
            }
            catch(IOException ioe) {
                System.out.println("Sending error: " + ioe.getMessage());
            }
        }
    }


    private void stop() {
        try {
            sock.close();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }
}
