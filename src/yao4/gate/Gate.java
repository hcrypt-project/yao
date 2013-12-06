package yao4.gate;

import java.util.Random;

import yao.Utils;

public class Gate 
{
	byte[][] lut=new byte[16][];
	
	public Gate(){}
	
	public Gate(byte[] l1,byte[] l2,byte[] l3,byte[] l4,
				byte[] l5,byte[] l6,byte[] l7,byte[] l8,
				byte[] l9,byte[] l10,byte[] l11,byte[] l12,
				byte[] l13,byte[] l14,byte[] l15,byte[] l16)
	{
		lut[0]=l1;
		lut[1]=l2;
		lut[2]=l3;
		lut[3]=l4;
		lut[4]=l5;
		lut[5]=l6;
		lut[6]=l7;
		lut[7]=l8;
		lut[8]=l9;
		lut[9]=l10;
		lut[10]=l11;
		lut[11]=l12;
		lut[12]=l13;
		lut[13]=l14;
		lut[14]=l15;
		lut[15]=l16;
	}
	
	public Gate(byte[][] lut)
	{
		this.lut=lut;
	}
	
	public byte[] operate(byte[] key1, byte[] key2) throws Exception
	{
		byte[] result1=null;
		byte[] result2=null;
		
		for(int i=0;i<16;i++)
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
		Random rnd=new Random();
		//encrypt
		lut[0]=Utils.AESencrypt(Utils.AESencrypt(r.value[i00],i1.getValue0(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[1]=Utils.AESencrypt(Utils.AESencrypt(r.value[i00],i1.getValue0(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[4]=Utils.AESencrypt(Utils.AESencrypt(r.value[i00],i1.getValue0(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[5]=Utils.AESencrypt(Utils.AESencrypt(r.value[i00],i1.getValue0(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		
		lut[2]=Utils.AESencrypt(Utils.AESencrypt(r.value[i01],i1.getValue0(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[3]=Utils.AESencrypt(Utils.AESencrypt(r.value[i01],i1.getValue0(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[6]=Utils.AESencrypt(Utils.AESencrypt(r.value[i01],i1.getValue0(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[7]=Utils.AESencrypt(Utils.AESencrypt(r.value[i01],i1.getValue0(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
			
		lut[8]=Utils.AESencrypt(Utils.AESencrypt(r.value[i10],i1.getValue1(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[9]=Utils.AESencrypt(Utils.AESencrypt(r.value[i10],i1.getValue1(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[12]=Utils.AESencrypt(Utils.AESencrypt(r.value[i10],i1.getValue1(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		lut[13]=Utils.AESencrypt(Utils.AESencrypt(r.value[i10],i1.getValue1(rnd.nextInt(2))),i2.getValue0(rnd.nextInt(2)));
		
		lut[10]=Utils.AESencrypt(Utils.AESencrypt(r.value[i11],i1.getValue1(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[11]=Utils.AESencrypt(Utils.AESencrypt(r.value[i11],i1.getValue1(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[14]=Utils.AESencrypt(Utils.AESencrypt(r.value[i11],i1.getValue1(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));
		lut[15]=Utils.AESencrypt(Utils.AESencrypt(r.value[i11],i1.getValue1(rnd.nextInt(2))),i2.getValue1(rnd.nextInt(2)));

		
		
		//shuffle
		for(int i=0;i<16;i++)
		{
			int s1=rnd.nextInt(16);
			int s2=rnd.nextInt(16);
			if(s1!=s2)
				Utils.swap(lut[s1], lut[s2]);
		}
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
