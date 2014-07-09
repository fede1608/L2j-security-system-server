	import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		SSLSocket client = null;
        public ServerThread(SSLSocket c) {
            this.client = c;
        }
        public void run() {
            try {
                System.out.println("Connected to client : "+client.getInetAddress().toString());
                InputStream inputstream = client.getInputStream();
	            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

	            String string = null;
	            while ((string = bufferedreader.readLine()) != null) {
	                System.out.println(string);
	                System.out.flush();
	            }
	            readHWID();
	            readFilesHash();
	            
                client.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
		private void readFilesHash() {
			// TODO Auto-generated method stub
			
		}
		private void readHWID() {
			// TODO Auto-generated method stub
			
		}
        }
	      


