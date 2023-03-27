import java.io.*;
import java.util.*;

class Serv_Clav extends Thread 
{
  
  public Serv_Clav ()
    {
      super ("Serv_Clav");
    }
  
  public void run () 
    {
      String ligne;
      System.out.println 
	("Serveur(" + Thread.currentThread()  + ") attend sur le clavier");
     
      BufferedReader entree = 
	new BufferedReader(new InputStreamReader(System.in));
      try
	{	  
	  ligne=entree.readLine();
	  // <CRTL D> pour arreter
	  while(ligne.length() >= 0)
	    {
	      System.out.println ( Thread.currentThread() + " lit : " + ligne);
	      ligne=entree.readLine();
	    }
	}
      catch (Exception e)
	{
	  e.printStackTrace();
	}
      System.out.println ("Serveur(" + Thread.currentThread()  + ")  Fin !!!");
      
    }
}

