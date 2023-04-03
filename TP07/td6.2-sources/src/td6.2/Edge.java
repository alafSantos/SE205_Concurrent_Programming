package se205.td6_2;

import akka.actor.Props;
import akka.japi.Creator;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Edge extends UntypedActor {
  // source node of the edge.
  public final ActorRef Src;

  // weight of the edge.
  public final int Weight;

  // destination node of the edge.
  public final ActorRef Dest;

  // create a new edge actor from a given source and destination node as well
  // the given weight.
  public Edge(ActorRef src, int weight, ActorRef dest) {
    Src = src;
    Weight = weight;
    Dest = dest;
  }


  // TODO: implement a props function to create actors representing graph edges.


  // TODO: implement a method to process in-coming messages.
}
