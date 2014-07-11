package com.l2jopenguard;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.l2jopenguard.Client.SecClient;


	public class L2jSecServer {
		
		static L2jSecServer secserver;
		
	    public static void main(String[] arstring) {
	    	secserver = new L2jSecServer();
	    }
	    
	    public L2jSecServer()
	    {
	    		Version.showInfo();
	        	
	        	new Thread(new ConectionsHandler()).start();
	        	
	        	Debug.show("Esta corriendo el Thread del SecServer");
	    }
	}
	
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
	
	class ServerThread implements Runnable {
		SSLSocket socket = null;
		SecClient cliente;
        public ServerThread(SSLSocket c) {
            this.socket = c;
            this.cliente= new SecClient(c);
            
        }
        public void run() {
            try {
                System.out.println("Connected to client : "+socket.getInetAddress().toString());
                

	            String string = null;
	            while (true){
		            while ((string = this.cliente.readString()) != null) {
		            	System.out.println("Mensaje "+string+ " from: "+ this.cliente.getIP());
		                switch(Integer.parseInt(string)){
		                	case 0: //Read HwID
		                		this.cliente.ReadHwID();
		                		break;
		                	case 1: //Check System Hashes
		                		this.cliente.ReadFileHashes();
			                	break;
		                	case 2:
			                	break;
		                	case 3:
			                	break;
		                	default:
		                		System.out.println("Cliente Desconectado");
		                		this.socket.close();
		                		return;
		                };
		            }
	            }
                //socket.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        }
	      


