import javax.crypto.spec.DESKeySpec;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Scanner;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.math.BigInteger;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
public class DESede
{SecretKey key;
 SecureRandom rand,saltrand;
 KeyGenerator gen;
 Cipher c;
 IvParameterSpec iv;
 String mode;
 int ivsize;
 String md5;
 byte[] salt = new byte[16];
 MessageDigest md;
 public ALG(String mode,String algo,int size) throws Exception
 {this.mode = mode;
  rand = new SecureRandom();
  gen = KeyGenerator.getInstance(algo);
  gen.init(size,rand);
  key = gen.generateKey();
  md = MessageDigest.getInstance("MD5");
  ivsize=8;
  System.out.println("IV Size in bytes:"+ivsize);
  iv = new IvParameterSpec(new byte[ivsize]);
  System.out.println("Key: "+key.toString());
  c = Cipher.getInstance(algo+"/"+mode+"/PKCS5Padding");	 
 }
 public String encrypt(String text)throws Exception
 {
  c.init(Cipher.ENCRYPT_MODE,key,iv);
  byte[] btext = text.getBytes();
  byte[] encbyte = c.doFinal(btext);
  md.update(btext,0,btext.length);
  md5 = new BigInteger(1,md.digest()).toString(16);
 return new String(encbyte);  
 }
 public String decrypt(String text)throws Exception
 {
  c.init(Cipher.DECRYPT_MODE,key,iv);
  byte[] btext = text.getBytes();
  byte[] decbyte = c.doFinal(btext);
  md.update(decbyte,0,decbyte.length);
  String m5 = new BigInteger(1,md.digest()).toString(16);
  if(md5.equals(m5))
  {System.out.println("Message is not corrupted...");}
  else
  {System.out.println("Message is corrupted!..");
   System.out.println("previous hash:"+md5);
   System.out.println("current hash:"+m5);
  }
 return new String(decbyte);	 
 }	
 public static void main(String[] args)
 {Scanner sc = new Scanner(System.in);
  System.out.print("Enter the text: ");
  String text = sc.nextLine();
  int size=56;
  System.out.print("Enter keysize(112(for two different keys),168(for three different keys)): ");  
  size = sc.nextInt();String dummy=sc.nextLine();	
  System.out.print("Enter the mode of encryption(CFB/OFB): ");
  String mode = sc.nextLine();
  try{
	  ALG a = new ALG(mode,algo,size);
	  String enc = a.encrypt(text);
	  System.out.println("Encrypted(Cipher) Text: "+enc);
	  text = a.decrypt(enc);
	  System.out.println("Decrypted(DeCipher) Text: "+text);  
	 }catch(Exception e)
		{System.out.println("Exception: "+e);}
 }
}