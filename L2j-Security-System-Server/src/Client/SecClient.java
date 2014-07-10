package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLSocket;

import Interface.SecL2PcInstance;

public class SecClient {
	
	BufferedReader bufferedreader;
	SSLSocket socket;
	String HWID;
	ConcurrentHashMap<String,String> fileHash=new ConcurrentHashMap<String,String>();
	List<SecL2PcInstance> player= new ArrayList<SecL2PcInstance>();
	
	public SecClient(SSLSocket c) {
		this.socket=c;
		InputStream inputstream;
		try {
			inputstream = this.socket.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			bufferedreader = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void ReadHwID() {
		this.HWID=this.readString();
		System.out.println("Mensaje "+ this.HWID);
	}
	public void ReadFileHashes() {
		//Read amount of files
		int amount=Integer.parseInt(this.readString());
		//Iterate X times and read the pair filename and hash
		for(int i=0;i<amount;i++){
			String filename= this.readString();
			System.out.println("Filename: "+filename);
			String hash= this.readString();
			System.out.println("Hash: "+hash);
			this.fileHash.put(filename, hash);
		}
		
	}
	
	public String readString(){
		try {
			return this.bufferedreader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "-1";
		}
	}
	
	public String getIP(){
		return this.socket.getInetAddress().toString();
	}
	

}
