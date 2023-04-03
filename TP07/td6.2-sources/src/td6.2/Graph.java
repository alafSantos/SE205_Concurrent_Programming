package se205.td6_2;

import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.actor.ActorSelection;
import akka.actor.ReceiveTimeout;

import scala.Option;
import scala.concurrent.duration.Duration;

import java.io.FileReader;
import java.io.BufferedReader;

public class Graph extends UntypedActor {
  private final Props nodeProps = Props.create(Node.class);

  private ActorRef root = null;

  // Read a graph from a text file.
  // Each line contains the name of a source node followed by "->", which is,
  // again, followed by the name of a destination node. This implicitly creates
  // the source and destination nodes as well as an edge between the two.
  private void readGraph(String filename) {
    try
    {
      BufferedReader reader = new BufferedReader(new FileReader(filename));

      int lineNumber = 0;
      while (reader.ready()) {
        final String line = reader.readLine();

        // find position of "=" and ">"
        int srcEndIdx = line.indexOf("=");
        int destStartIdx = line.indexOf(">");
        if (srcEndIdx > 0 && destStartIdx > 0 &&
            srcEndIdx < destStartIdx &&
            destStartIdx < line.length()) {
          // extract source and destination names.
          final String srcName = line.substring(0, srcEndIdx).trim();
          final int weight = Integer.valueOf(line.substring(srcEndIdx + 1,
                                                          destStartIdx).trim());
          final String destName = line.substring(destStartIdx + 1).trim();

          // make node and edge names.
          final String srcNodeName = "Node" + srcName;
          final String destNodeName = "Node" + destName;
          final String edgeName = "Edge" + srcName + "__" + destName;

          // create a unique actors for the source and destination node.
          Option<ActorRef> src = getContext().child(srcNodeName);
          if (src.isEmpty()) {
            src = Option.apply(getContext().actorOf(nodeProps, srcNodeName));
          }

          scala.Option<ActorRef> dest = getContext().child(destNodeName);
          if (dest.isEmpty()) {
            dest = Option.apply(getContext().actorOf(nodeProps, destNodeName));

            // create artificial edge back to to graph
            getContext().actorOf(Edge.props(dest.get(), 0, getSelf()),
                                 "Edge" + destName + "__");
          }

          // create an actor for the edge between the two nodes.
          getContext().actorOf(Edge.props(src.get(), weight, dest.get()),
                               edgeName);

          // keep first node as root node.
          if (root == null) {
            root = src.get();
          }
        }
        else {
          // signal an error, but keep going.
          System.err.println("error: invalid graph specification line " +
                            lineNumber + ": '" + line + "'");
        }

        lineNumber = lineNumber + 1;
      }
    }
    catch (Exception e) {
      // display an error message in case of an error.
      System.err.println(e);
    }
  }

  @Override
  public void preStart() {
    // time-out to terminate the actor system after 2 seconds.
    getContext().setReceiveTimeout(Duration.create(2, "seconds"));
  }

  @Override
  public void onReceive(Object msg) {
    if (msg instanceof String) {
      // message from the program's main function, supplying a file name.

      // read the graph and create a corresponding data structure.
      readGraph((String)msg);

      // start processing the graph.
      root.tell(0, getSelf());
    }
    else if (msg instanceof ReceiveTimeout) {
      // message from the actor system indicating a 2 seconds IDLE period,
      // shut the actor system down.
      getContext().system().shutdown();
    }
    else if (msg instanceof Integer) {
      // message from all graph nodes indicating the shortest path to the
      // respective node from the root node.

      final String edgeName = getSender().path().name();

      // print the distance to the source node of the sending edge.
      System.out.println("Node distance: " +
                         edgeName.substring(4, edgeName.length() - 2) +
                         " = " + msg);
    }
    else {
      // signal that an unexpected message was received
      unhandled(msg);
    }
  }
}
