package Lec11;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public class SpamCheck {
    public static final String BLACKHOLE = "sbl.spamhaus.org";
    private static Set<String> processedIPs = new HashSet<>();
    public static void checkSpam(List<String> ipAddresses) {
        for (String ipAddress : ipAddresses) {
                if (isSpammer(ipAddress)) {
                    if (!processedIPs.contains(ipAddress)) {
                        processedIPs.add(ipAddress); // Add the address to processed IPs
                        System.out.println(ipAddress + " is a known spammer.");
                    }
                } else {
                //    System.out.println(ipAddress + " appears legitimate.");
                }

        }
    }

    private static boolean isSpammer(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
//            System.out.println(query);
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
