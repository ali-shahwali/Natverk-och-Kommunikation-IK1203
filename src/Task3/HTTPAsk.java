package Task3;
import Task1.TCPClient;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class HTTPAsk {

    private static int INIT_SIZE = 512;

    public static void main(String[] args) throws IOException {

        String found = "HTTP/1.1 200 OK\n\n";
        String notFound = "HTTP/1.1 404 Not Found\n\n";
        String badRequest = "HTTP/1.1 400 Bad Request\n\n";

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true) {
            Socket clientSocket = serverSocket.accept();
            InputStream inStream = clientSocket.getInputStream();
            OutputStream outStream = clientSocket.getOutputStream();


            int port = 0;
            String host = null;
            String str = null;


            byte[] data = new byte[INIT_SIZE];
            int size = inStream.read(data);

            byte[] trimmedData = new byte[size];
            for(int i = 0; i < size; i++)
                trimmedData[i] = data[i];


            String request = new String(trimmedData, StandardCharsets.UTF_8);
            String[] split = request.split("[ ?=&\\r\\n]");

            boolean httpVer = false;
            for(int i = 0; i < split.length; i++) {
                if(split[i].equals("hostname"))
                    host = split[i + 1];
                else if(split[i].equals("port"))
                    port = Integer.parseInt(split[i+1]);
                else if(split[i].equals("string")) {
                    str = split[i+1];
                    str += "\n";
                }
                else if(split[i].equals("HTTP/1.1"))
                    httpVer = true;
            }

            if(split[0].equals("GET") && split[1].equals("/ask") && port != 0 && httpVer) {
                try {
                    String output;
                    if(str == null)
                        output = TCPClient.askServer(host, port);
                    else
                        output = TCPClient.askServer(host, port, str);

                    outStream.write(found.getBytes(StandardCharsets.UTF_8));
                    outStream.write(output.getBytes(StandardCharsets.UTF_8));
                }
                catch(IOException e) {
                    outStream.write(notFound.getBytes(StandardCharsets.UTF_8));
                }
            }
            else {
                outStream.write(badRequest.getBytes(StandardCharsets.UTF_8));
            }

            clientSocket.close();
        }
    }
}
