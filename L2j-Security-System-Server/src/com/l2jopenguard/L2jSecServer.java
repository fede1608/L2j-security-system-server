package com.l2jopenguard;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.l2jopenguard.Client.SecClient;
import com.l2jopenguard.utils.Debug;
import com.l2jopenguard.utils.Version;

	
	public class L2jSecServer {
		
		static L2jSecServer secserver;
		
		// Una sola instancia de main
	    public static void main(String[] args) throws Throwable {
	    	secserver = new L2jSecServer();
	    	
	    	//-------------- Load L2J Server --------------//
	        Class<?> clazz = null;
	        try
	        {
	          clazz = Class.forName(args[0]);
	        }
	        catch (Exception e) {}
	    	
	        Method main = clazz.getDeclaredMethod("main", new Class[] { String[].class });
	        args = (String[])Arrays.copyOfRange(args, 1, args.length);
	        
	        main.invoke(null, new Object[] { args });
	        
	        //-------------- L2J Server Loaded --------------//
	        
	        Debug.show("FINISH!");
	    }
	    
	    public L2jSecServer()
	    {
	    		Version.showInfo();
	        	
	        	new Thread(new ConectionsHandler()).start();
	        	
	        	Debug.show("Esta corriendo el Thread del SecServer");
	    }
	}
	
	// Una sola instancia de ConectionsHandler (atiende de una conexión a la vez)
	class ConectionsHandler implements Runnable {
		public void run ()
		{
	        try 
	        {   
	        	Debug.show("Se crean los sockets");
	            SSLServerSocketFactory sslserversocketfactory =
	                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	            SSLServerSocket sslserversocket =
	                    (SSLServerSocket) sslserversocketfactory.createServerSocket(39999);
	            
	            while (true) {
	            	Debug.show("Adentro del while");
	                SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
	                Debug.show("Se acepto una nueva conexion");
	                new Thread(new ServerThread(sslsocket)).start();
	                Debug.show("Esta corriendo el Thread del cliente anterior");
	                
	            }
	            
	
	        } 
	        catch (Exception exception) 
	        {
	            exception.printStackTrace();
	        }
		}
	}
	
	// Conexión con el cliente: es un thread por cada cliente
	class ServerThread implements Runnable {
		
		SSLSocket socket = null;
		SecClient cliente;
		
        public ServerThread(SSLSocket c) 
        {
            this.socket = c;
            this.cliente= new SecClient(c);
        }
        
        public void run() {
            try {
            	
                System.out.println("Connected to client : "+socket.getInetAddress().toString());
                
	            String string = null;
	            
	            while (true)
	            {
		            while ((string = this.cliente.readString()) != null) 
		            {
		            	System.out.println("Mensaje "+string+ " from: "+ this.cliente.getIP());
		            	
		            	int type = Integer.parseInt(string);
		            	
		            	if (type == 0)
		            	{
		            		this.cliente.ReadHwID();
		            	}
		            	else
		            	{
		            		if (this.cliente.getHwid() != null)
		            		{
				                switch(Integer.parseInt(string)){
			                	case 1: //Check System Hashes
			                		this.cliente.ReadFileHashes();
				                	break;
			                	case 2:
				                	break;
			                	case 3:
				                	break;
			                	case 10:
			                		this.cliente.readAccountFromClient();
			                		break;
			                	default:
			                		System.out.println("Cliente Desconectado");
			                		this.cliente.disconnectClient();
			                		return;
				                };
		            		}
		            		else
		            		{
		                		System.out.println("Cliente Desconectado");
		                		this.socket.close();
		                		return;
		            		}
		            	 }
		            }
	            }
                //socket.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        }
