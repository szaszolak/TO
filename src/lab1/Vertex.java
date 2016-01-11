package lab1;

import java.util.*;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


/**
 * Created by Mikolaj on 2015-12-13.
 */
public class Vertex {
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Pair<Integer, Double>> getEgdes() {
        return egdes;
    }

    List<Pair<Integer, Double>> egdes;

    public Vertex(Node node,int number) {
        this.number = number;
        this.egdes = new LinkedList<Pair<Integer, Double>>();
        this.parseNode(node);
    }

    private void addEdge(String cost, int vertex){
        String value = cost.substring(0,cost.indexOf('e'));
        String prec = cost.substring(cost.indexOf('e')+2);
        double numericCost = Float.parseFloat(value) * Math.pow(10,Float.parseFloat(prec));
        this.egdes.add(new Pair<Integer, Double>(vertex,numericCost));
    }

    private void parseNode(Node node){
        NodeList childEdges = node.getChildNodes();
        for (int i =0;i<childEdges.getLength();i++){
            Node edge = childEdges.item(i);
            if(!edge.getNodeName().equals("edge"))
                continue;
            edge.normalize();
            addEdge(edge.getAttributes().item(0).getNodeValue(),Integer.parseInt(edge.getFirstChild().getNodeValue()));
        }
        Collections.sort(egdes,new EdgesComparator());
    }

      class EdgesComparator implements Comparator<Pair<Integer,Double>>{

        @Override
        public int compare(Pair<Integer, Double> o1, Pair<Integer, Double> o2) {
            return o1.getValue() < o2.getValue() ? -1 : o1.getValue() == o2.getValue() ? 0 : 1;
        }
    }

    @Override
    public String toString(){
        return this.number+"";
    }
}
