/**
 * 
 */
package antibot.Antibot;

import com.l2jopenguard.Client.ClientHandler;
import com.l2jopenguard.Interface;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;

/**
 * @author Zephyr
 *
 */
public class Antibot extends Quest
{
	
	private Antibot()
	{
		super(-1, "Antibot", "antibot");
		setOnEnterWorld(true);
	}
	
	@Override
	public String onEnterWorld(L2PcInstance player)
	{
		ClientHandler handler = new ClientHandler(player);
		handler.initMatch();
		return null;
	}
	
	public static void main(String[] args)
	{
		new Antibot();
	}
}
