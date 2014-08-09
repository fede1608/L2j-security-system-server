package com.l2jopenguard.Client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import com.l2jopenguard.utils.Debug;

public class SecClient {
	
	private static final String TYPE_MESSAGE = "10";
	private String _tempaccount;
	
	BufferedReader _bufferedreader;
	DataOutputStream _salida;
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
			_salida = new DataOutputStream(_socket.getOutputStream());

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
	
	public void readAccountFromClient()
	{
		String debug = readString();
		Debug.show("readAccountFromClient -> " + debug);
		_tempaccount = debug;
		_tempaccount.notify();
	}
	
	public String getIP()
	{
		return _socket.getInetAddress().toString();
	}
	
	public String getHwid()
	{
		return _HWID;
	}
	
	public String getAccountFromClient()
	{
		writeLn(TYPE_MESSAGE);

		if (_tempaccount == null || _tempaccount == "")
		{
			try {
				_tempaccount.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String result = _tempaccount;
		_tempaccount = "";
		
		Debug.show("La cuenta que se recibio fue " + result);
		
		return result;
	}
	
	private void writeLn(String message)
	{
		try {
			_salida.writeChars(message + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOld()
	{
		return false;
	}
	
	public void addPlayer(SecL2PcInstance player)
	{
		_players.add(player);
	}
	
	public List<SecL2PcInstance> getPlayers()
	{
		return _players;
	}

}
