package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLSocket;

import Interface.SecL2PcInstance;

public class SecClient {
	SSLSocket socket;
	String HWID;
	ConcurrentHashMap<String,String> fileHash;
	List<SecL2PcInstance> player= new ArrayList<SecL2PcInstance>();
	

}
