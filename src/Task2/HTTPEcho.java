package Task2;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;


public class HTTPEcho {
    private static int INIT_SIZE = 128;

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);


        while(true) {
            Socket toClient = serverSocket.accept();
            InputStream clientInput = toClient.getInputStream();
            OutputStream clientOutput = toClient.getOutputStream();

            byte[] data = new byte[INIT_SIZE];
            byte[] ch = new byte[1];
            int i = 0;
            int k = clientInput.read(ch);
            try {
                while(k != -1) {
                    if(i == data.length)
                        data = resize(data);

                    if(ch[0] == 13){
                        data[i] = 13;
                        i++;
                        k = clientInput.read(ch);
                        if(ch[0] == 10) {
                            data[i] = 10;
                            i++;
                            k = clientInput.read(ch);
                            if(ch[0] == 13) {
                                data[i] = 13;
                                i++;
                                break;
                            }
                        }
                    }
                    else {
                        data[i] = ch[0];
                        k = clientInput.read(ch);
                        i++;
                    }
                }
                data = trim(data, i);

                String status = "HTTP/1.1 200 OK\n\n";
                byte[] encodedStatus = status.getBytes(StandardCharsets.UTF_8);
                clientOutput.write(encodedStatus);
                clientOutput.write(data);

                toClient.close();
            }
            catch(Exception e) {
                System.err.println("Exception occured");
            }
        }
    }

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
