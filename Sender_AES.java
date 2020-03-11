import java.net.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.rmi.*;
import java.util.*;
public class Sender_AES
{


   static Scanner s=new Scanner(System.in);
   private static SecretKeySpec secretKey;
   private static byte[] key;
   static String encst=null;
    Sender_AES()
   {
   final String secretKey = "123456";
   String originalString = "Mahesh";
           int dd=1;
   String encryptedString = encrypt(originalString, secretKey,dd) ;
   //String decryptedString = decrypt(encryptedString, secretKey,dd) ;
   encst=encryptedString;
   }

   public static void setKey(String myKey,int cc)
   {
       MessageDigest sha = null;
       try {
           key = myKey.getBytes("UTF-8");
          if(cc==1)
           sha = MessageDigest.getInstance("SHA-1");
           else
           {sha = MessageDigest.getInstance("MD5");
           sha.update(key);}
           key = sha.digest(key);
           key = Arrays.copyOf(key, 16);
           secretKey = new SecretKeySpec(key, "AES");
       }
       catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
   }

   public static String encrypt(String strToEncrypt, String secret,int dd)
   {
       try
       {
           setKey(secret,dd);
           Cipher cipher;
           cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
       }
       catch (Exception e)
       {
           System.out.println("Error while encrypting: " + e.toString());
       }
       return null;
   }





public static void main(String a[])throws Exception
{
ServerSocket ser=new ServerSocket(10);
Socket s=ser.accept();
Sender_AES ss=new Sender_AES();
OutputStream os=s.getOutputStream();
os.write(ss.encst.getBytes());
s.close();
}
}
