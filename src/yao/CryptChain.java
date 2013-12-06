package yao;

import java.security.KeyPair;

import yao.gate.Gate;
import yao.gate.Wire;
import yao.gate.XorGate;

public class CryptChain 
{
	
	
	public static void main(String[] args) throws Exception
	{
		KeyPair kp=Utils.genRSAkeypair();
		
		Wire a1=new Wire();
		Wire a2=new Wire();
		Wire ra=new Wire();
		Gate g1=new XorGate(a1,a2,ra);
		
	}
}
