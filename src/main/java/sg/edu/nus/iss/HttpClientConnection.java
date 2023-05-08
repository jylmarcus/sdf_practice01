package sg.edu.nus.iss;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HttpClientConnection implements Runnable{

    private final Socket sock;
    private List<Path> paths;

    public HttpClientConnection(Socket s, List<Path> paths) {
        this.sock = s;
        this.paths = paths;
    }

    @Override
    public void run() {
        try {
            InputStream is = sock.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            OutputStream os = sock.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            String[] request = br.readLine().split(" ");

            //check for GET method
            if(!request[0].trim().equalsIgnoreCase("get")){
                dos.writeUTF("HTTP/1.1 405 Method Not Allowed\r\n\r\n<method name> not supported\r\n");
                dos.flush();

                dos.close();
                bos.close();
                os.close();

                br.close();
                isr.close();
                is.close();

                sock.close();
            }

            //convert "/" to "/index.html"
            if(request[1].equals("/")){
                request[1] = "/index.html";
            }

            List<File> filePaths = new ArrayList<File>();
            for(Path path: paths){
                File file = new File(path.toString() + request[1]);
                filePaths.add(file);
            }

            for(File file: filePaths) {
                //if file exists, one of the below actions are carried out
                if(file.exists() && file.toPath().endsWith(".png")){
                    String msg = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\n\r\n";
                    dos.writeUTF(msg);
                    dos.flush();
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] buff = new byte[4*1024];
                    int size = 0;
                    while((size = bis.read(buff)) > 0) {
                        dos.write(buff, 0, size);
                        dos.flush();
                    }

                    bis.close();
                    fis.close();

                    dos.close();
                    bos.close();
                    os.close();
        
                    br.close();
                    isr.close();
                    is.close();

                    sock.close();
                }

                if(file.exists() && !file.toPath().endsWith(".png")){
                    dos.writeUTF("HTTP/1.1 200 OK\r\n\r\n");
                    dos.flush();

                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    byte[] buff = new byte[4*1024];
                    int size = 0;
                    while((size = bis.read(buff)) > 0) {
                        dos.write(buff, 0, size);
                        dos.flush();
                    }

                    bis.close();
                    fis.close();

                    dos.close();
                    bos.close();
                    os.close();
        
                    br.close();
                    isr.close();
                    is.close();

                    sock.close();
                }
            }

            //if file doesn't exist, execute the following lines of code
            dos.writeUTF("HTTP/1.1 404 Not Found\r\n\r\n<resource name> not found\r\n");
            dos.flush();

            dos.close();
            bos.close();
            os.close();

            br.close();
            isr.close();
            is.close();

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {sock.close(); } catch (Exception e) { }
        }
    }
    
}
