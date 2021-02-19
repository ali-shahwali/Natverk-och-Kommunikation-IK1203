package Task1;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    private static int INIT_SIZE = 1024;

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(2000);
        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();

        // writes to server with UTF-8 encoding
        byte[] clientOutput = ToServer.getBytes(StandardCharsets.UTF_8);
        output.write(clientOutput);

        // reads 1 byte from inputstream and adds it to a resizing array
        byte[] data = new byte[INIT_SIZE];
        byte[] ch = new byte[1];
        int i = 0;
        int k = input.read(ch);
        try {
            while(k != -1) {
                if(i == data.length) {
                    byte[] resize = new byte[data.length*2];
                    for(int j = 0; j < data.length; j++)
                        resize[j] = data[j];
                    data = resize;
                }
                data[i] = ch[0];
                k = input.read(ch);
                i++;
            }
        }
        catch(java.net.SocketTimeoutException e) {

        }

        // trims the data so no unessecary data is returned
        byte[] trimmedData = new byte[i];
        for(int j = 0; j < trimmedData.length; j++)
            trimmedData[j] = data[j];

        socket.close();
        return new String(trimmedData, StandardCharsets.UTF_8);
    }

    public static String askServer(String hostname, int port) throws  IOException {
        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(2000);
        InputStream input = socket.getInputStream();

        // reads 1 byte from inputstream and adds it to a resizing array
        byte[] data = new byte[INIT_SIZE];
        byte[] ch = new byte[1];
        int i = 0;
        int k = input.read(ch);
        try {
            while(k != -1) {
                if(i == data.length) {
                    byte[] resize = new byte[data.length*2];
                    for(int j = 0; j < data.length; j++)
                        resize[j] = data[j];
                    data = resize;
                }
                data[i] = ch[0];
                k = input.read(ch);
                i++;
            }
        }
        catch(java.net.SocketTimeoutException e) {

        }

        // trims the data so no unessecary data is returned
        byte[] trimmedData = new byte[i];
        for(int j = 0; j < trimmedData.length; j++)
            trimmedData[j] = data[j];

        socket.close();
        return new String(trimmedData, StandardCharsets.UTF_8);
    }
}


