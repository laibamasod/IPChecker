package Lec12;

import java.io.*;
import java.net.*;
public class EncoderTest {
    public static void main(String[] args) {
        try {
            // underscore hyphen period asterisk reutnr as it is
            String url = "https://www.google.com/search?";
 url += URLEncoder.encode("hl", "UTF-8"); url += "=";
 url += URLEncoder.encode("en", "UTF-8"); url += "&";
 url += URLEncoder.encode("as_q", "UTF-8"); url += "=";
 url += URLEncoder.encode("Java  l", "UTF-8"); url += "&";
 url += URLEncoder.encode("as_epq", "UTF-8"); url += "=";
 url += URLEncoder.encode("I/O", "UTF-8");
 System.out.println(url);
        System.out.println(URLDecoder.decode(url,"UTF-8"));
            System.out.println(URLEncoder.encode("This string has spaces","UTF-8"));
            System.out.println(URLEncoder.encode("This*string*has*asterisks","UTF-8"));
            System.out.println(URLEncoder.encode("This%string%has%percent%signs","UTF-8"));
            System.out.println(URLEncoder.encode("This+string+has+pluses","UTF-8"));
            System.out.println(URLEncoder.encode("This/string/has/slashes","UTF-8"));
            System.out.println(URLEncoder.encode("This\"string\"has\"quote\"marks","UTF-8"));
            System.out.println(URLEncoder.encode("This:string:has:colons","UTF-8"));
            System.out.println(URLEncoder.encode("This~string~has~tildes","UTF-8"));
            System.out.println(URLEncoder.encode("This(string)has(parentheses)","UTF-8"));
            System.out.println(URLEncoder.encode("This.string.has.periods","UTF-8"));
            System.out.println(URLEncoder.encode("This=string=has=equals=signs","UTF-8"));
            System.out.println(URLEncoder.encode("This&string&has&ampersands","UTF-8"));
            System.out.println(URLEncoder.encode("Thiséstringéhasénon-ASCII characters", "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Broken VM does not support UTF-8");}
    }}
