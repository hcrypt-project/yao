package yao.gate;

import java.util.Random;

import yao.Utils;

public class Gate 
{
	byte[][] lut=new byte[4][];
	
	public Gate(){}
	
	public Gate(byte[] l1,byte[] l2,byte[] l3,byte[] l4)
	{
		lut[0]=l1;
		lut[1]=l2;
		lut[2]=l3;
		lut[3]=l4;
	}
	
	public Gate(byte[][] lut)
	{
		this.lut=lut;
	}
	
	public byte[] operate(byte[] key1, byte[] key2) throws Exception
	{
		byte[] result1=null;
		byte[] result2=null;
		
		for(int i=0;i<4;i++)
		{
			result1=Utils.AESdecrypt(lut[i],key1);
			result2=Utils.AESdecrypt(result1,key2);
			
			if(result2[0]==0x12&&result2[1]==0x33&&result2[2]==0x21)
				return result2;
		}
		
		return null;
	}
	
	/*
	 * generate an encrypted lookup-table
	 * ==================================
	 * 
	 * i-wires are inputs, r-wire is the result
	 * 
	 * the table consists of entries that hold output values taken
	 * from the r-wire values. this function can generate arbitrary
	 * function tables, just call with 0's and 1's according to the
	 * following table and the function picks the corresponding
	 * wire ciphers
	 * 
	 * LUT  | i1.0   i1.1 
	 * -------------------
	 * i2.0 | 0:i00  1:i01
	 *      |
	 * i2.1 | 2:i10  3:i11
	 * 
	 * colums keys: i1.0,i1.1
	 * row keys:    i2.0,i2.1
	 *
	 * 
	 * examples:
	 * 
	 * AND: genEncryptedLut(0,0,0,1,i1,i2,r)
	 * XOR: genEncryptedlut(0,1,1,0,i1,i2,r)
	 *  OR: genEncryptedlut(0,1,1,1,i1,i2,r)
	 * 
	 * each entry is encrypted with its column- and row-key (in that
	 * order):
	 * 
	 * lut[0]=encrypt(encrypt(entry,key_i1.0),key_i2.0)
	 * lut[1]=encrypt(encrypt(entry,key_i1.1),key_i2.0)
	 * lut[2]=encrypt(encrypt(entry,key_i1.0),key_i2.1)
	 * lut[3]=encrypt(encrypt(entry,key_i1.1),key_i2.1)
	 * 
	 */
	void genEncryptedLut(int i00,int i01,int i10,int i11,Wire i1, Wire i2, Wire r) throws Exception
	{	
		//encrypt
		lut[0]=Utils.AESencrypt(Utils.AESencrypt(r.value[i00],i1.value[0]),i2.value[0]);
		lut[1]=Utils.AESencrypt(Utils.AESencrypt(r.value[i01],i1.value[1]),i2.value[0]);
		lut[2]=Utils.AESencrypt(Utils.AESencrypt(r.value[i10],i1.value[0]),i2.value[1]);
		lut[3]=Utils.AESencrypt(Utils.AESencrypt(r.value[i11],i1.value[1]),i2.value[1]);
		
		//shuffle
		Random rand=new Random();
		if(rand.nextBoolean())
			Utils.swap(lut[0],lut[1]);
		if(rand.nextBoolean())
			Utils.swap(lut[2],lut[3]);
		if(rand.nextBoolean())
			Utils.swap(lut[0],lut[2]);
		if(rand.nextBoolean())
			Utils.swap(lut[1],lut[3]);
	}
	
	public byte[] getLutEntry(int i)
	{
		return lut[i];
	}
	
	public byte[][] getLut()
	{
		return lut;
	}

}
