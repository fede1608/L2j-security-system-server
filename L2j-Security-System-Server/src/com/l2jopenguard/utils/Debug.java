package com.l2jopenguard.utils;

public class Debug {
	
	private static boolean DEBUG = true;
	
	public static void show(String message)
	{
		if (DEBUG)
		{
			System.out.println("DEBUG: " + message);
		}
		else
		{
			// Do nothing
		}
	}

}
