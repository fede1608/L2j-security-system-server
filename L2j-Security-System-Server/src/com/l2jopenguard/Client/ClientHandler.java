package com.l2jopenguard.Client;

import com.l2jopenguard.AllClients;
import com.l2jopenguard.Interface.SecL2PcInstance;
import com.l2jopenguard.utils.Debug;

public class ClientHandler {
	
	SecL2PcInstance _player;
	
	public void initMatch()
	{
		Debug.show("onEnterWorld: " + _player.getName());
		
		if (!addToAllClients())
		{
			Debug.show("El player " + _player.getName() + " fue kickeado porque no se pudo matchear con un SecClient");
			_player.kickClient();
		}
	}
	
	public ClientHandler(SecL2PcInstance player)
	{
		_player = player;
	}
	
	public boolean addToAllClients()
	{
		return AllClients.getInstance().addPlayer(_player);
	}

}
