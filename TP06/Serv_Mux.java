import java.io.*;
import java.util.*; 

///////////////////////////////////////////////////////////////////////////
//         Serv_Mux creee un tableau de threads.
//               Le premier est dedie a l'ecoute sur le clavier.
//               On donne sur le ligne de commande lesquels de ces threads
//               seront des serveurs TCP.
//               Les autres sont des "threads standards"
//
///////////////////////////////////////////////////////////////////////////

public class Serv_Mux 
{

    public static void main(String[] args) 
	throws java.io.IOException, java.net.SocketException
    {
	Thread Tab_Threads [];
	int j, Port, Nbre_Standard, Nbre_Serv_TCP, Nbre_Threads = 0;
	
	// Verification des arguments passes sur la ligne de commande
	if (args.length != 3)
	    {
		System.out.println
		    ("Arguments : nbre-threads nbre-serveurs-TCP num-premier-port");
		System.exit(1);
	    }
      
      Nbre_Threads  = Integer.parseInt(args[0]);
      Nbre_Serv_TCP = Integer.parseInt(args[1]);
      Nbre_Standard = Nbre_Threads - Nbre_Serv_TCP - 1;
      Port          = Integer.parseInt(args[2]);

      if (Nbre_Serv_TCP > Nbre_Threads)
	  {
	      System.out.println
		  ("nb-de-threads nb-de-serveurs-TCP < nb-de-threads !");
	      System.exit(1);
	  }

      // Creation du tableau de threads
      Tab_Threads = new Thread[Nbre_Threads];
      
      // Creation du thread de saisie clavier
      Tab_Threads[0] = new Serv_Clav();

      // Creation des threads serveurs TCP
      for ( j = 1 ; j <= Nbre_Serv_TCP; j++) 
	  {
	      Tab_Threads[j] = new Serv_TCP (Port + j -1);
	  }
      // La ligne suivante sera decommentee quand on ajoute 
      // le serveur UDP (une entree supplememtaire dans 
      // le tableau de threads.
      // Nbre_Standard--;  
 
    //    Creation des threads "standards"
    for (j=1 ; j <= Nbre_Standard ; j++) 
	{
	    Tab_Threads[j+Nbre_Serv_TCP] = new Thread_Standard (j+Nbre_Serv_TCP);
	}
    System.out.println 	("threads banaals creee "); 

    // Creation du thread serveur UDP : A Faire !!!!!!
    // 
    //


    // Demarrage de tous les threads  
    for (j = 0; j < Nbre_Threads; j++) 
	{
	    Tab_Threads[j].start();
	}
 
    System.out.println("Attente de fin des threads ");
   
    // Attendre la fin de tous les threads 
    // Le message qui annonce la teminaison d un thread
    // doit etre affiche des qu'il s'est termine.
    // CE N EST PAS LE CAS A CAUSE DE L ORDRE 
    // impose par join  

    for (j = 0; j < Nbre_Threads; j++) 
	{	
	    try 
		{
		    Tab_Threads[j].join();
		    System.out.println
			("Thread" + Tab_Threads[j].getName() + "termine");
		} 
	    catch (InterruptedException e ) 
		{
		    System.out.println
			("Erreur join avec " +Tab_Threads[j].getName()+ e);
		    System.exit(1);
		}
	}
    
    // C'est fini ...
    System.out.println(" Fin ! ");
    
    }
    
}

///////////////////////////////////////////////////////////////////////////
//         Voici le thread de base ...
//         Il se met "iter" fois "random" secondes en etet bloque,
//         lorsqu'il se termine, main doit immediatement
//         afficher un message.
// CE N EST PAS FAIT A CAUSE DE join.
//         Pour realiser ce fonctionnement, il faudrait utiliser
//         dans le thread main
//         une variable conditionnelle, "signalee" chaque fois qu
//         un thread standard se termine. 
///////////////////////////////////////////////////////////////////////////

class Thread_Standard extends Thread
{
    int iter;
    public Thread_Standard (int Num )
    {
	super ("ThreadStandard-" + Num) ;
	iter = Num;
    }
    
    public void run ()
    {
	System.out.println("Thread " +Thread.currentThread() + "demarre ");
	int i ;
	for (i = 0 ; i < iter ; i++)
	    {
		try
		    {
			Thread.sleep ((int)(Math.random() * 10000 ));
		    }
		catch (Exception e)
		    {
			e.printStackTrace();
		    }
	    }   
	System.out.println ("--->" + Thread.currentThread() + " :  Fin !");
    }
}  // fin Thread_Standard


