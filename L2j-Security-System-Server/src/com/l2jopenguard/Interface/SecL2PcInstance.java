package com.l2jopenguard.Interface;

import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/*
 * Interface
 */

public class SecL2PcInstance 
{
	L2PcInstance _player;
	
	public SecL2PcInstance(L2PcInstance player)
	{
		_player = player;
	}
	
	public String getIp()
	{
		return _player.getClient().getConnectionAddress().toString();
	}
	
	public String getAccount()
	{
		return _player.getAccountName();
	}
	
	public String getName()
	{
		return _player.getName();
	}
	
	public void kickClient()
	{
		_player.getClient().closeNow();
	}
}
