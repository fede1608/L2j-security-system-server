package com.l2jopenguard.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.SSLSocket;

import com.l2jopenguard.AllClients;
import com.l2jopenguard.Interface.SecL2PcInstance;

public class SecClient {
	
	BufferedReader _bufferedreader;
	SSLSocket _socket;
	String _HWID;
	ConcurrentHashMap<String,String> _fileHash = new ConcurrentHashMap<String,String>();
	List<SecL2PcInstance> _players = new ArrayList<SecL2PcInstance>();

	public SecClient(SSLSocket c) {
		
		_socket = c;
		InputStream inputstream;
		try {
			inputstream = _socket.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			_bufferedreader = new BufferedReader(inputstreamreader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void ReadHwID() 
	{
		_HWID=this.readString();
		AllClients.getInstance().addClient(this);
		
		System.out.println("Mensaje "+ _HWID);
	}
	
	public void ReadFileHashes() {
		//Read amount of files
		int amount=Integer.parseInt(this.readString());
		//Iterate X times and read the pair filename and hash
		for(int i=0;i<amount;i++){
			String filename= this.readString();
			System.out.println("Filepath: "+filename);
			System.out.println("Filename: "+new File(filename).getName());
			String hash= this.readString();
			System.out.println("Hash: "+hash);
			_fileHash.put(filename, hash);
		}
		
	}
	
	public String readString(){
		try {
			return _bufferedreader.readLine();
		} catch (IOException e) {
			// Debug
			//e.printStackTrace();
			return "-1";
		}
	}
	
	public String getIP()
	{
		return _socket.getInetAddress().toString();
	}
	
	public String getHwid()
	{
		return _HWID;
	}
	
	public boolean isOld()
	{
		return false;
	}

}
