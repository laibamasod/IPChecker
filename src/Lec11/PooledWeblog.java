package Lec11;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class PooledWeblog {
    private final static int NUM_THREADS = 5;

    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        Queue<LogEntry> results = new LinkedList<>();
        List<String> ipAddresses = new ArrayList<>(); // List to hold IP addresses

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream("apache_logs.txt"), "UTF-8"))) {
            for (String entry = in.readLine(); entry != null; entry = in.readLine()) {
                LookupTask task = new LookupTask(entry);
                Future<String> future = executor.submit(task);
                try {
                    // This will block until the result is available
                    String ip = future.get();
                    System.out.println(ip);
                } catch (InterruptedException e) {
                    // Handle the InterruptedException
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    // Handle the ExecutionException
                    e.printStackTrace();
                } finally {
                    // Make sure to shut down the executor when you're done with it
                    executor.shutdown();
                }
                LogEntry result = new LogEntry(entry, future);
                results.add(result);

                String ipAddress = extractIPAddress(entry);
                if (ipAddress != null) {
                    ipAddresses.add(ipAddress);
                }
            }
        }

        // Pass ipAddresses to SpamCheck for spam detection
        SpamCheck.checkSpam(ipAddresses);

        // Start printing the results. This blocks each time a result isn't ready.
//        for (LogEntry result : results) {
//            try {
//                System.out.println(result.future.get());
//            } catch (InterruptedException | ExecutionException ex) {
//                System.out.println(result.original);
//            }
//        }

        executor.shutdown();
    }

    private static class LogEntry {
        String original;
        Future<String> future;

        LogEntry(String original, Future<String> future) {
            this.original = original;
            this.future = future;
        }
    }

    private static String extractIPAddress(String logEntry) {
        // Logic to extract the IP address from log entry
        // Assuming IP is the first token before the "- -"
        String[] tokens = logEntry.split(" - -");
        if (tokens.length > 0) {
            String ipAddress = tokens[0].trim();
            // Validate IP address (you may need more robust validation)
            if (ipAddress.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b")) {
                return ipAddress;
            }
        }
        return null;
    }
}
