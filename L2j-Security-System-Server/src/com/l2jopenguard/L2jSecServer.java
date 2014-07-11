package com.l2jopenguard;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import com.l2jopenguard.Client.SecClient;


	public class L2jSecServer {
	    public
	            static
	    void
	            main(String[] arstring) {
	        try {
	            SSLServerSocketFactory sslserversocketfactory =
	                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	            SSLServerSocket sslserversocket =
	                    (SSLServerSocket) sslserversocketfactory.createServerSocket(39999);
	            
	            while (true) {
	                SSLSocket sslsocket = (SSLSocket) sslserversocket.accept();
	                new Thread(new ServerThread(sslsocket)).start();
	            }
	            

	        } catch (Exception exception) {
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
	      


