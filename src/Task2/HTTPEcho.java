package Task2;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class HTTPEcho {

    private static int INIT_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        String status = "HTTP/1.1 200 OK\n\n";
        byte[] encodedStatus = status.getBytes(StandardCharsets.UTF_8);

        while(true) {
            Socket toClient = serverSocket.accept();
            InputStream clientInput = toClient.getInputStream();
            OutputStream clientOutput = toClient.getOutputStream();

            clientOutput.write(encodedStatus);

            byte[] data = new byte[INIT_SIZE];
            while(true) {
                int dataSize = clientInput.read(data);

                if(dataSize < INIT_SIZE) {
                    byte[] trimData = new byte[dataSize];
                    int i = 0;
                    while(i < dataSize) {
                        trimData[i] = data[i];
                        i++;
                    }
                    clientOutput.write(trimData);
                    toClient.close();
                    break;
                }
                else {
                    clientOutput.write(data);
                    data = new byte[INIT_SIZE];
                }
            }
        }
    }

// not used
    public static byte[] resize(byte[] arr) {

        byte[] resize = new byte[arr.length*2];
        for(int i = 0; i < arr.length; i++)
            resize[i] = arr[i];

        return resize;

    }

    public static byte[] trim(byte[] arr, int size) {
        byte[] trimmedData = new byte[size];
        for(int j = 0; j < trimmedData.length; j++)
            trimmedData[j] = arr[j];

        return trimmedData;
    }
}
