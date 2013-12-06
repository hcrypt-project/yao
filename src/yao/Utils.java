package yao;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import yao.gate.Gate;

public class Utils 
{
	public static void swap(byte[] a,byte[] b)
	{
		byte[] h;
		h=a;
		a=b;
		b=h;
	}
	
	public static void printLut(Gate gate,String title)
	{
		System.out.println(title);
		for(int i=0;i<4;i++)
		{
			System.out.println(getHex(gate.getLutEntry(i)));
		}
		System.out.println();
	}
	
	public static String getHex(byte[] b)
	{
		String result="";
		for(int j=0;j<b.length;j++)
		{
			String hex="0"+Integer.toHexString(b[j]&255);
			result+=hex.substring(hex.length()-2);
		}
		return result;
	}
	
	public static boolean arraysAreEqual(byte[] a,byte[] b)
	{
		if(a.length!=b.length)
			return false;
		
		for(int i=0;i<a.length;i++)
			if(a[i]!=b[i])
				return false;
		
		return true;
	}
	
	public static byte[] genAESkey(int size) throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(size); 
		
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		
		return raw;
	}
	public static byte[] AESdecrypt(byte[] encrypted,byte[] key) throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] plain=cipher.doFinal(encrypted);
		return plain;
	}
	
	public static byte[] AESencrypt(byte[] plain,byte[] key) throws Exception
	{
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted =cipher.doFinal(plain);
		return encrypted;
	}
	
	public static KeyPair genRSAkeypair() throws Exception
	{
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(128);
		KeyPair kp = kpg.genKeyPair();
//		Key publicKey = kp.getPublic();
//		Key privateKey = kp.getPrivate();
		return kp;
	}
	
	public static byte[] RSAencrypt(byte[] data,PublicKey key) throws Exception
	{		  
	  Cipher cipher = Cipher.getInstance("RSA");
	  cipher.init(Cipher.ENCRYPT_MODE, key);
	  byte[] cipherData = cipher.doFinal(data);
	  return cipherData;
	}
	
	public static byte[] RSAdecrypt(byte[] data,PrivateKey key) throws Exception
	{		  
	  Cipher cipher = Cipher.getInstance("RSA");
	  cipher.init(Cipher.DECRYPT_MODE, key);
	  byte[] cipherData = cipher.doFinal(data);
	  return cipherData;
	}

	
	public void bench(Gate gate1,byte[] in_a1,byte[] in_a2) throws Exception
	{
		long start=System.currentTimeMillis();
		
		for(int i=0;i<10000;i++)
			gate1.operate(in_a2, in_a1);
		
		System.out.println("10000 operations: "+(System.currentTimeMillis()-start)+" ms");
		
		start=System.currentTimeMillis();
		
		for(int i=0;i<10000;i++)
		{
			for(int j=0;j<4;j++)
			{
				Utils.AESdecrypt(gate1.getLutEntry(0),in_a2);
				Utils.AESdecrypt(gate1.getLutEntry(0),in_a2);
			}
		}
		
		System.out.println("10000 *4 *2 decryptions: "+(System.currentTimeMillis()-start)+" ms");
		
		start=System.currentTimeMillis();
		
		for(int i=0;i<10000;i++)
		{
			for(int j=0;j<4;j++)
			{
				Utils.AESencrypt(gate1.getLutEntry(0),in_a2);
				Utils.AESencrypt(gate1.getLutEntry(0),in_a2);
			}
		}
		
		System.out.println("10000 *4 *2 encryptions: "+(System.currentTimeMillis()-start)+" ms");
		start=System.currentTimeMillis();
		
		MessageDigest md = MessageDigest.getInstance( "SHA" );
		
		for(int i=0;i<10000;i++)
		{
			for(int j=0;j<4;j++)
			{
				md.reset();
				md.update(in_a1);
				md.update(in_a2);
		        // md.update( int ) processes only the low order 8-bits. It actually expects an unsigned byte.
		        md.digest();
		        
		        md.reset();
				md.update(in_a1);
				md.update(in_a2);
		        // md.update( int ) processes only the low order 8-bits. It actually expects an unsigned byte.
		        md.digest();
			}
		}
		System.out.println("10000 *4 *2 hashes: "+(System.currentTimeMillis()-start)+" ms");
	}
}
