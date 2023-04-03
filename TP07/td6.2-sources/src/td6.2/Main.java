package se205.td6_2;

import akka.actor.ActorSystem;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Main {
  // start-up of the Akka application.
  public static void main(String[] args) {
    // make sure that a command-line argument was given.
    if (args.length == 1)
    {
      // create the actor system.
      ActorSystem system = ActorSystem.create("Bellman-Ford");

      // create the first actor ...
      final ActorRef graph = system.actorOf(Props.create(Graph.class), "Graph");

      // off we go ...
      graph.tell(args[0], ActorRef.noSender());

      // wait for the actor system to terminate.
      system.awaitTermination();
    }
    else {
      // print an error message otherwise.
      System.err.println("error: invalid number of arguments: " +
                         "expecting a single file name.");
    }
  }
}
