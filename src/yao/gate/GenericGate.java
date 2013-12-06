package yao.gate;


public class GenericGate extends Gate 
{
	public GenericGate(Wire i1,Wire i2,Wire r,int i00,int i01,int i10,int i11) throws Exception
	{  
		genEncryptedLut(i00,i01,i10,i11,i1,i2,r);
	}
}
