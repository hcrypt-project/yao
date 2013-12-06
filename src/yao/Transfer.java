package yao;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Random;

public class Transfer 
{
	Random rand=new Random();
	
	public void exchange() throws Exception
	{
		KeyPair keypair=Utils.genRSAkeypair();
		KeyFactory fact = KeyFactory.getInstance("RSA");
		
		PrivateKey 			Alice_privatekey=keypair.getPrivate();
		RSAPrivateKeySpec 	Alice_priv=fact.getKeySpec(Alice_privatekey,RSAPrivateKeySpec.class);
		BigInteger 			Alice_N=Alice_priv.getModulus();
		BigInteger 			Alice_d=Alice_priv.getPrivateExponent();
		System.out.println("Alice_d="+Alice_d.toString(10));
		BigInteger 			Alice_m0=new BigInteger("12345",10);
		BigInteger 			Alice_m1=new BigInteger("67890",10);
		BigInteger 			Alice_x0=new BigInteger(1024,rand);
		BigInteger		 	Alice_x1=new BigInteger(1024,rand);
		
		PublicKey 			Bob_publickey=keypair.getPublic();
		RSAPublicKeySpec 	Bob_pub = fact.getKeySpec(Bob_publickey,RSAPublicKeySpec.class);
		BigInteger 			Bob_N=Bob_pub.getModulus();
		BigInteger 			Bob_e=Bob_pub.getPublicExponent();
		
		System.out.println("Bob_e="+Bob_e.toString(10));
		BigInteger 			Bob_x0=Alice_x0;
		BigInteger 			Bob_x1=Alice_x1;
		
		BigInteger 			Bob_secretx=Bob_x1; //wir wollen m1 haben
		BigInteger			Bob_k=new BigInteger(1024,rand);
		BigInteger 			Bob_v=Bob_secretx.add(Bob_k.pow(Bob_e.intValue())).mod(Bob_N); 
		
		BigInteger 			Alice_v=Bob_v;
		
		
	
	}
}
