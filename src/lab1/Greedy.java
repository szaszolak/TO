package lab1;

import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mikolaj on 2015-12-13.
 */
public class Greedy {
    Vertex[] baseGraph;
    ArrayList<Vertex> resultGraph;
    List<Pair<Double,List<Vertex>>> totalResult;
   // ArrayList<Integer> result;
    double cost;

    public Greedy(Graph g) {
        this.baseGraph = g.getGraph();
        this.resultGraph = new ArrayList<Vertex>();
        this.totalResult = new ArrayList<>();
        cost =0;

    }

    public void solv(){
        //start from '0' vertex
        for (int i = 0; i < 99; i++) {
            resultGraph.add(this.baseGraph[i]);
            while(resultGraph.size()<100){
                addEgde();
            }
            for(Pair<Integer,Double> e : this.resultGraph.get(this.resultGraph.size()-1).getEgdes()){
                if(e.getKey()==this.resultGraph.get(0).getNumber()){
                    cost+=e.getValue();
                    break;
                }
            }
            totalResult.add(new Pair<>(cost,(ArrayList<Vertex>)resultGraph.clone()));
            cost = 0;
            resultGraph.clear();
        }

        printResult();

    }

    private Edge findShortEdge(Vertex v){
        Edge edge = new Edge();
        edge.v1 = v;
        double cost = Double.MAX_VALUE;
        Pair<Integer,Double> candidate = v.getEgdes().get(0);
        for(Pair<Integer,Double> e : v.getEgdes()){
            if(cost>=e.getValue() && !this.resultGraph.contains(this.baseGraph[e.getKey()])){
                cost = e.getValue();
                candidate = e;
            }
        }
        edge.v2 = baseGraph[candidate.getKey()];
        edge.cost = cost;
        return edge;
    }

    private void addEgde(){
        Edge edge = new Edge();
        edge.cost = Double.MAX_VALUE;
        for(Vertex v : resultGraph){
            Edge e = findShortEdge(v);
            if(e.cost<=edge.cost)
                edge = e;
        }

        this.resultGraph.add(this.resultGraph.indexOf(edge.v1)+1,edge.v2);
        cost+=edge.cost;
    }
    void printResult(){
        double min,max,avg=0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        Pair<Double,List<Vertex>> bestSolution = new Pair<>(cost,resultGraph);
        for(Pair<Double,List<Vertex>> sol : totalResult){
            double key =(double)sol.getKey();
            if(key>=max)
            {
                max = key;
            }
            if(key<=min)
            {
                min = key;
                bestSolution = sol;
            }
            avg+=key;
        }
        avg = avg/totalResult.size();
        String res = "[";
        for(Vertex v : bestSolution.getValue())
            res+=(v+",");
        try {
            PrintWriter writer = new PrintWriter("files//wynikGreedy.txt", "UTF-8");
            writer.println(res + "]");
            writer.println("koszt: " + bestSolution.getKey());
            writer.println("MIN: " + min);
            writer.println("MAX: " + max);
            writer.println("AVG: " + avg);
            writer.println("-----------------------");
            writer.println("");
            writer.close();
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
}
