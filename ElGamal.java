import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;

public class ElGamal
{
    public static void main(String[] args) throws IOException
    { 
        Scanner scan=new Scanner(System.in);
        BigInteger q, a, YA, XA;
        Random sc = new SecureRandom();
        XA = new BigInteger("12345678901234567890");
        
        // public key calculation
        
        System.out.println("secretKey or XA= " + XA);
        q = BigInteger.probablePrime(64, sc);
        a = new BigInteger("3");
        YA = a.modPow(XA, q);
        System.out.println("Q = " + q);
        System.out.println("a or alpha = " + a);
        System.out.println("YA or public key = " + YA);
        
        // Encryption
        
        System.out.print("Enter your Big Number message -->");
        String s = scan.next();
        BigInteger X = new BigInteger(s);
        BigInteger k = new BigInteger(64, sc);
        BigInteger C2 = X.multiply(YA.modPow(k, q)).mod(q);
        BigInteger C1 = a.modPow(k, q);
        System.out.println("Plaintext = " + X);
        System.out.println("k or random value = " + k);
        BigInteger K=YA.modPow(k, q);
        System.out.println("K from encryption="+K);
        System.out.println("C2 = " + C2);
        System.out.println("C1 = " + C1);
        
        // Decryption
        
        K = C1.modPow(XA, q);
        BigInteger K_1 = K.modInverse(q);
        BigInteger M = K_1.multiply(C2).mod(q);
        System.out.println("K from decryption = " + K);
        System.out.println("K inverse is = " + K_1);
        System.out.println("Alice decodes: " + M);

    }
}