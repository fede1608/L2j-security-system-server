package com.l2jopenguard.utils;

public class Version {
	
	static String VERSION = "0.1";

	private static String getVersion()
	{
		return VERSION;
	}
	
	public static void showInfo()
	{
    	System.out.println("L2jOpenGuard - Server side");
    	System.out.println("Made by fede1608 and Zephyr");
    	System.out.println("Version: " + Version.getVersion());
	}
	
}
