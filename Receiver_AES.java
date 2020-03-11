import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.*;
import java.io.*;
class Receiver_AES
{

    static Scanner s=new Scanner(System.in);
    private static SecretKeySpec secretKey;
    private static byte[] key;
    static String decst;
    Receiver_AES(String kk)
    {
    final String secretKey = "123456";
    String originalString = "Mahesh";
            int dd=1;
    
    String decryptedString = decrypt(kk, secretKey,dd) ;
    
    System.out.println(decryptedString);
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
 
 
    public static String decrypt(String strToDecrypt, String secret,int dd) 
    {
        try
        {
            setKey(secret,dd);
            Cipher cipher;
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }


public static void main(String a[])throws Exception
{
Socket s=new Socket(InetAddress.getLocalHost(),10);
 InputStream is =s.getInputStream();
 byte []b=new byte[2048];
is.read(b);
decst=new String(b).trim();
System.out.println(decst);
Receiver_AES r=new Receiver_AES(decst);
}
}
