package ar.edu.itba.sia;

import ar.edu.itba.sia.gps.GPSNode;
import ar.edu.itba.sia.gps.GPSObserver;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class TreePlotter implements GPSObserver {

        private Graph graph;

        @Override
        public void start(final GPSNode root) {
            graph = new SingleGraph("graph");
            graph.addNode(root.getId());
            Node rootNode = graph.getNode(root.getId());
            rootNode.setAttribute("ui.label",root.getId());
            rootNode.setAttribute("ui.color","RED");
            graph.display();

        }

        @Override
        public void observe(final GPSNode node) {
            String nodeId = node.getId();
            String parentId = node.getParent().getId();
            String edgeId = node.getGenerationRule().getName() + " (" + parentId + ", " + nodeId + ")";
            graph.addNode(node.getId());
            graph.getNode(nodeId).setAttribute("ui.label",node.getId());
            graph.addEdge(edgeId,parentId,nodeId,true);
            graph.getEdge(edgeId).setAttribute("ui.label",node.getGenerationRule().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void finalize() {
            //graph.display();
        }
}

