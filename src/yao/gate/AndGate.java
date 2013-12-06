package yao.gate;


public class AndGate extends Gate
{
	public AndGate(Wire i1,Wire i2,Wire r) throws Exception
	{  
		genEncryptedLut(0,0,0,1,i1,i2,r);
	}
}
