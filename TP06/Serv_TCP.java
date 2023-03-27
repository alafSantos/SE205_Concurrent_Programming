import java.util.*;
import java.io.*;
import java.net.*;

///////////////////////////////////////////////////////
//
//  Ce serveur repond a un client telnet.
//  Il fait l'echo du message envoye.
//
//////////////////////////////////////////////////////
public class Serv_TCP extends Thread
{
  int Port;
  
  public Serv_TCP (int Port)
    {
      super ("Serv_TCP-"+ Port);
      this.Port = Port;
    }
  
  public void run ()
    {
      Socket Sock_Comm;
	 try
	{
	  ServerSocket Sock_Ecou =  new ServerSocket(Port);
	  System.out.println 
	    ("Serveur(" + Thread.currentThread()  + ") attend sur " + Port );

// Attente de demande d'etablissement de
// communication sur le port d'ecoute (Sock_Ecou).
// Si tout se passe bien la communication
// est etablie entre le client et le port 
// renvoye par accept sur Sock_Ecou.
// Ce port (Sock_Comm) sera utilise pour la comm. 
// avec le client.

// Cette comm. sera geree par un thread du type Echo_TCP_Thread.

	  while (true)
	    {
		sleep (1000);// MODIF A FAIRE : attente sur le port d'ecoute.
		// MODIF A FAIRE : lancer thread de gestion de comm.
	    }
	}
      catch (Exception e)
	{
	  e.printStackTrace();
	}
      
    }
  
}

///////////////////////////////////////////////////////////
//
// Thread fils du serveur qui va gerer la communication
// avec un client
//////////////////////////////////////////////////////////

class Echo_TCP_Thread extends Thread
{
  Socket Sock_Thr;

  public Echo_TCP_Thread (Socket Le_Socket)
    {
      this.Sock_Thr = Le_Socket;
    }
  
  public void run()
    {
      try 
	{
	  PrintWriter output  = new PrintWriter(Sock_Thr.getOutputStream());
         InputStreamReader input = 
	new InputStreamReader(Sock_Thr.getInputStream());
	  BufferedReader binput   = new BufferedReader(input);
	  
	  String temp;

	  while ((temp=binput.readLine()) != null)
	    {
	      output.print(this.getName() + " repond -> ");
	      output.println(temp);
	      output.flush();
	  System.out.println ("Fils serveur : " + Thread.currentThread()  
            + " a recu"  + temp);

	    }	
	}
      catch (Exception e)
	{
	  return;
	}
      finally
	{
	  try 
	    {
	      Sock_Thr.close();
		  	  System.out.println 
	    ("Fils Serveur " + Thread.currentThread()  + " : Fin !!! ");
	  } 
	  catch (IOException e)
	    {
	    }
	}
    }
}
