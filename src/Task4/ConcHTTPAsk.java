package Task4;
import java.io.IOException;
import java.net.*;

public class ConcHTTPAsk {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new HTTPAsk(clientSocket)).start();
        }
    }
}
