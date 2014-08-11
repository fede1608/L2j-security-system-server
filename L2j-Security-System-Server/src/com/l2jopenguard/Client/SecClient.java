package com.l2jopenguard.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
	BufferedWriter _bufferSalida;
	SSLSocket _socket;
	String _HWID;
	ConcurrentHashMap<String,String> _fileHash = new ConcurrentHashMap<String,String>();
	List<SecL2PcInstance> _players = new ArrayList<SecL2PcInstance>();

	public SecClient(SSLSocket c) {
		
		_socket = c;
		InputStream inputstream;
		OutputStream outputstream;
		try {
			inputstream = _socket.getInputStream();
			InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
			_bufferedreader = new BufferedReader(inputstreamreader);
			
			outputstream = _socket.getOutputStream();
			OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
			_bufferSalida = new BufferedWriter(outputstreamwriter);

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
		Debug.show("getAccountFromClient se inicia el metodo");
		writeLn(TYPE_MESSAGE);
		
		Debug.show("getAccountFromClient se espera el mensaje");
		
		
		while (_tempaccount == null || _tempaccount == "")
		{
			Debug.show("Esperando la carroza");
		}
		
		Debug.show("getAccountFromClient salio del while");
		
		String result = _tempaccount;
		
		_tempaccount = "";
		
		Debug.show("La cuenta que se recibio fue " + result);
		
		return result;
	}
	
	private void writeLn(String message)
	{
		try {
			_bufferSalida.write(message + '\n');
			_bufferSalida.flush();
			Debug.show("Se mando el mensaje supuestamente");
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
