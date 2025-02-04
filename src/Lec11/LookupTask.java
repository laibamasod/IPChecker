package Lec11;

import java.net.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.concurrent.Callable;
public class LookupTask implements Callable<String> {
    private String line;
    public LookupTask(String line) {
        this.line = line;
    }
    @Override
    public String call() {
        try {
// separate out the IP address
            int index = line.indexOf(' ');
            String address = line.substring(0, index);
            String theRest = line.substring(index);
            String hostname = InetAddress.getByName(address).getHostName();
//            System.out.println("host is "+hostname);
            return hostname;
        } catch (Exception ex) {
            return line;
        }
    }
}

