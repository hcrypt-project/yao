package yao4.gate;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Wire 
{
	public static final int AES_KEYLENGTH=128;
	byte[][] value=new byte[4][0];
	
	public Wire() throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(AES_KEYLENGTH); 
		SecretKey skey = kgen.generateKey();
		value[0]=skey.getEncoded();
		skey = kgen.generateKey();
		value[1]=skey.getEncoded();
		skey = kgen.generateKey();
		value[2]=skey.getEncoded();
		skey = kgen.generateKey();
		value[3]=skey.getEncoded();
		
		value[0][0]=0x12;value[0][1]=0x33;value[0][2]=0x21;
		value[1][0]=0x12;value[1][1]=0x33;value[1][2]=0x21;
		value[2][0]=0x12;value[2][1]=0x33;value[2][2]=0x21;
		value[3][0]=0x12;value[3][1]=0x33;value[3][2]=0x21;
	}
	
	public byte[] getValue0(int i)
	{
		return value[0+i];
	}
	
	public byte[] getValue1(int i)
	{
		return value[2+i];
	}
}
