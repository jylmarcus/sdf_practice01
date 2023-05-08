package sg.edu.nus.iss;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int port = 3000;
        final String DIRECTORY = "./target";
        List<Path> paths = new LinkedList<Path>();
        paths.add(Paths.get(DIRECTORY));

        List<String> argPaths = new LinkedList<String>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--port":
                    port = Integer.parseInt(args[i + 1]);
                    break;
                case "--docroot":
                    String[] tempPaths = args[i+1].split(":");
                    for(String path: tempPaths) {
                        argPaths.add(path);
                    }
                    break;
                default:
                    break;
            }
        }

        for(String argPath : argPaths) {
            if(!paths.get(0).toString().equals(argPath)) {
                paths.add(Paths.get(argPath));
            }
        }
    }
}
