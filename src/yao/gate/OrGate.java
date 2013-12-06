package yao.gate;


public class OrGate extends Gate 
{
	public OrGate(Wire i1,Wire i2,Wire r) throws Exception
	{  
		genEncryptedLut(0,1,1,1,i1,i2,r);
	}
}
