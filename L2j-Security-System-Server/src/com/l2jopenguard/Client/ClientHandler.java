package com.l2jopenguard.Client;

import com.l2jopenguard.Debug;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

public class ClientHandler {
	
	public static void check(L2PcInstance player)
	{
		//Aca va todo el comportamiento que se quiera hacer con el pj
		Debug.show("Testing: " + player.getName());
	}

}
