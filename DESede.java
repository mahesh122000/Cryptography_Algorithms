import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.*;
import java.io.*; 
import javax.crypto.Cipher;
import javax.crypto.spec.*;
 
public class DESede {
    static Scanner s=new Scanner(System.in);
    private static SecretKeySpec secretKey;
    private static byte[] key;
    DESede()
    {System.out.println("enter the secret key...");
    final String secretKey = s.nextLine();
    System.out.println("enter the original string...");
    String originalString = s.nextLine();
    System.out.println("enter 1 for SHA-1 and 2 for MD5...");
            int dd=s.nextInt();
    String encryptedString = encrypt(originalString, secretKey,dd) ;
    String decryptedString = decrypt(encryptedString, secretKey,dd) ;
    System.out.println(originalString);
    System.out.println(encryptedString);
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
            key = Arrays.copyOf(key, 24); 
            secretKey = new SecretKeySpec(key, "DESede");
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
            IvParameterSpec iv=new IvParameterSpec(new byte[8]);
            cipher = Cipher.getInstance("DESede/CFB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,iv);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret,int dd) 
    {
        try
        {
            setKey(secret,dd);
            Cipher cipher;
             IvParameterSpec iv=new IvParameterSpec(new byte[8]);
            cipher = Cipher.getInstance("DESede/CFB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey,iv);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
   public static void main(String arg[])
   {DESede aes=new DESede();}
}