package se205.td6_2;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSelection;

public class Node extends UntypedActor {
  // the shortest path from the root node to the current node
  // (may change in the course of the Bellman-Ford algorithm.
  private int shortestPathWeight = Integer.MAX_VALUE;

  // TODO: implement a method to process in-coming messages.
}
