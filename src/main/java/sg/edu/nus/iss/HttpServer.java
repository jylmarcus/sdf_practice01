package sg.edu.nus.iss;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private ServerSocket ss;
    private List<Path> paths;
    private ExecutorService thrPool = Executors.newFixedThreadPool(3);

    public HttpServer(Integer port, List<Path> paths) throws IOException {
        this.ss = new ServerSocket(port);
        this.paths = paths;
    }

    public HttpServer() {
    }

    public ServerSocket getSs() {
        return ss;
    }

    public void checkPaths(List<Path> paths) {
        for(Path path : paths) {
            File file = new File(path.toString());
            if(!file.exists()){
                System.out.println("Path does not exist");
                System.exit(1);
            }
            if(!file.isDirectory()) {
                System.out.println("Path is not a directory");
                System.exit(1);
            }
            if(!file.canRead()) {
                System.out.println("Path is not readable");
                System.exit(1);
            }
        }
    }

    public void startServer() throws IOException {
        checkPaths(this.paths);

        while(true) {
            System.out.println("Waiting for new client connection...");
            Socket sock = this.ss.accept();

            System.out.printf("Got a new connection: %s\n\n", sock);

            HttpClientConnection handler = new HttpClientConnection(sock, this.paths);
            System.out.println("Dispatching client to thread pool");

            this.thrPool.submit(handler);
        }
    }
}