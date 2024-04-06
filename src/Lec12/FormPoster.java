package Lec12;

import java.io.*;
import java.net.*;
public class FormPoster {
    private URL url;
    private QueryString query = new QueryString();
    public FormPoster (URL url) {
        if (!url.getProtocol().toLowerCase().startsWith("http")) { throw new IllegalArgumentException("Posting only works for http URLs");}
        this.url = url;
    }
    public void add(String name, String value) { query.add(name, value); }
    public InputStream post() throws IOException {
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(uc.getOutputStream(), "UTF-8")) {
            out.write(query.toString());
            out.write("\r\n");
            out.flush();
        }
        return uc.getInputStream(); }
    public static void main(String[] args) {
        URL url;
        if (args.length > 0) {
            try { url = new URL(args[0]);
            } catch (MalformedURLException ex) {
                System.err.println("Usage: java FormPoster url"); return;
            }
        } else {
            try {
                url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            } catch (MalformedURLException ex) { // shouldn't happen
                System.err.println(ex); return;
            }
        }
        FormPoster poster = new FormPoster(url);
        poster.add("name", "Elliotte Rusty Harold");
        poster.add("email", "elharo@ibiblio.org");
        try (InputStream in = poster.post()) {
            Reader r = new InputStreamReader(in); int c;
            while((c = r.read()) != -1) {
                System.out.print((char) c);}
            System.out.println();
        } catch (IOException ex) {
            System.err.println(ex);
        }}
    class QueryString {
        private StringBuilder query = new StringBuilder();

        public void add(String name, String value) {
            if (query.length() > 0) {
                query.append("&");
            }
            try {
                query.append(URLEncoder.encode(name, "UTF-8"));
                query.append("=");
                query.append(URLEncoder.encode(value, "UTF-8"));
            }catch (UnsupportedEncodingException ex) {
                System.err.println("Unsupported Encoding: " + ex.getMessage());
            }
        }

        @Override
        public String toString() {
            return query.toString();
        }
    }}

