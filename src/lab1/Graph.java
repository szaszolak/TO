package lab1;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created by Mikolaj on 2015-12-13.
 */
public class Graph {
    Vertex[] graph;

    public Vertex[] getGraph() {
        return graph;
    }

    public Graph(String path) {
        this.graph = new Vertex[100];
        this.readGraph(path);
    }

    private void readGraph(String path) {
        try {

            File file = new File(path);

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodes;
            if (doc.hasChildNodes()) {
                nodes = doc.getDocumentElement().getElementsByTagName("vertex");
                for (int i = 0; i <nodes.getLength() ; i++) {
                    this.graph[i]=new Vertex(nodes.item(i),i);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}


