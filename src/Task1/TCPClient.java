package Task1;
import java.net.*;
import java.io.*;

public class TCPClient {

    private static int INIT_TIMEOUT = 2000;

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(INIT_TIMEOUT);
        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();

        for(int i = 0; i < ToServer.length(); i++) {
            int ch = ToServer.charAt(i);
            output.write(ch);
        }

        int nextChar = input.read();
        StringBuilder data = new StringBuilder("");
        try {
            do {
                data.append((char) nextChar);
                nextChar = input.read();
            } while(nextChar != -1);
        }
        catch(java.net.SocketTimeoutException e) {

        }

        socket.close();
        return data.toString();
    }

    public static String askServer(String hostname, int port) throws  IOException {
        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(INIT_TIMEOUT);
        InputStream input = socket.getInputStream();

        int nextChar = input.read();
        StringBuilder data = new StringBuilder("");
        try {
            do {
                data.append((char) nextChar);
                nextChar = input.read();
                if(data.length() < 500)
                    break;
            } while(nextChar != -1);
        }
        catch(java.net.SocketTimeoutException e) {

        }

        socket.close();
        return data.toString();
    }
}


