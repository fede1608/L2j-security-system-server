package com.l2jopenguard;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.l2jopenguard.Client.SecClient;
import com.l2jopenguard.Interface.SecL2PcInstance;
import com.l2jopenguard.utils.Debug;

public class AllClients {
	
	private Map<String, SecClient> _clients = new ConcurrentHashMap<>();
	
	private AllClients()
	{
		//Carga de variables de objetos
	}
	
	private void deleteClient(String clientHwid)
	{
		Debug.show("Se elimino el cliente de HWID " + clientHwid);
		_clients.remove(clientHwid);
	}
	
	public void addClient(SecClient client)
	{
		Debug.show("Se agrego el cliente de HWID " + client.getHwid());
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
		Collection<SecClient> list = _clients.values();
		boolean result = false;
		
		Debug.show("La IP que se busca es " + player.getIp());
		
		Iterator<SecClient> iter = list.iterator();
		while (iter.hasNext())
		{
			
			SecClient client = iter.next();
			Debug.show("La IP de este secclient es " + client.getIP());
			
			if(client.getIP().equalsIgnoreCase(player.getIp()))
			{
				Debug.show("Hay coincidencia de IP de " + player.getName());
				
				String account = client.getAccountFromClient();
				
				Debug.show("AllClients: El account es " + account);
				
				if (account != null)
				{		
					if (account.equalsIgnoreCase(player.getAccount()))
					{
						client.addPlayer(player);
						result = true;
						Debug.show("Se agrego " + player.getName() + " al SecClient de HWID " + client.getHwid());
					}	
				}
				else
				{
					Debug.show("La cuenta del cliente " + player.getName() + " fue null");
				}

			}
			else
			{
				Debug.show("No hay coincidencia de IP de " + player.getName());
			}
		}
		
		return result;
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
