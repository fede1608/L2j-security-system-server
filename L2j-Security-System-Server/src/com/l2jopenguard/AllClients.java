package com.l2jopenguard;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.l2jopenguard.Client.SecClient;
import com.l2jopenguard.Interface.SecL2PcInstance;

public class AllClients {
	
	private Map<String, SecClient> _clients = new ConcurrentHashMap<>();
	
	private AllClients()
	{
		//Carga de variables de objetos
	}
	
	private void deleteClient(String clientHwid)
	{
		_clients.remove(clientHwid);
	}
	
	public void addClient(SecClient client)
	{
		_clients.put(client.getHwid(), client);
	}
	
	//TODO: Tiene que llamarlo un task cada X tiempo
	public void deleteOldClients()
	{
		Collection<SecClient> list = _clients.values();
		
		Iterator<SecClient> iter = list.iterator();
		while (iter.hasNext())
		{
			if(iter.next().isOld())
			{
				deleteClient(iter.next().getHwid());
			}
		}
	}
	
	public boolean addPlayer(SecL2PcInstance player)
	{
		//TODO: programar
		return true;
	}
	
	public static AllClients getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		private static final AllClients _instance = new AllClients();
	}
}
