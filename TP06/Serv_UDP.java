import java.io.*;
import java.net.*;
import java.util.*;

////////////////////////////////////////////
//
// Ce serveur UDP se bloque sur un receive.
// Quand il recoit un message, il repond en envoyant la date.
//
////////////////////////////////////////////

public class Serv_UDP extends Thread
{
    int Port;
  // Socket_UDP est le socket d'ecoute du serveur
  protected DatagramSocket Socket_UDP  = null;
  
  // Le prot par defaut est 7545
  public Serv_UDP() throws IOException
    {
       super ("Serv_UDP-"+ 7545);
       Socket_UDP = new DatagramSocket(7545);
       this.Port = 7545;
    }
  
  public Serv_UDP(int Un_Port) throws IOException
    {
       super ("Serv_UDP-"+ Un_Port);
       Socket_UDP = new DatagramSocket(Un_Port);
       this.Port = Un_Port;
    }
  
  // methode run du thread
  public void run()
    {
    
      DatagramPacket Message = null;
      String La_Date         = null;
      byte[] Tampon          = new byte[256];

      System.out.println 
	  ("Serveur(" + Thread.currentThread()  + ") attend sur " + Port );
      
      while ( true )
	{
	  try
	    {
	      // Attendre un message emis par le client
	      Message  = new DatagramPacket(Tampon, Tampon.length);
	      Socket_UDP.receive(Message);
	      
	      // Preparer la reponse (recuperer la date)
	      La_Date = new Date().toString();
	      Tampon  = La_Date.getBytes();
	    
	      System.out.println (Thread.currentThread() + " va emettre : " 
				  +  new String(Tampon)
				  + " (taille : " 
				  + Tampon.length + ")");
	    
	      // Envoyer la reponse vers le client
	      // dont on recupere l'addresse IP et le port
	      InetAddress Adresse_IP = Message.getAddress();
	      Port                   = Message.getPort();
	      Message                = new DatagramPacket (Tampon, 
					  Tampon.length, Adresse_IP, 
							   Port);
 	      Socket_UDP.send(Message);
            } 
	  catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	}  // fin du while
    } // fin de run
}
