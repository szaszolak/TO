package lab1;

import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Mikolaj on 2015-12-13.
 */
public class Grasp {
    Vertex[] graph;
    List<Pair<Double,List<Integer>>> totalResult;
    ArrayList<Integer> result;
    double cost;

    public Grasp(Graph g) {
        this.totalResult = new ArrayList<Pair<Double, List<Integer>>>();
        this.graph = g.getGraph();
        result = new ArrayList<Integer>();
        cost =0;
    }

    public void solv(){
        //start from '0' vertex
        for (int i = 0; i < 99; i++) {
            result.add(0);
            addEgde(0);
            for(Pair<Integer,Double> e : this.graph[result.get(result.size()-1)].getEgdes()){
                if(e.getKey()==this.graph[0].getNumber()){
                    cost+=e.getValue();
                    break;
                }
            }
            totalResult.add(new Pair<Double, List<Integer>>(cost,(ArrayList<Integer>)result.clone()));
            cost = 0;
            result.clear();
        }
        printResult();

    }

    private void addEgde(int vIndex){
        Vertex v = this.graph[vIndex];
        Pair<Integer,Double> nextVertex;
        List<Pair<Integer,Double>> candidates = new ArrayList<>();

        for(int i=0;i<99;i++)//
        {
            nextVertex =v.egdes.get(i);

            if(!result.contains(nextVertex.getKey()))
            {
                candidates.add(nextVertex);
            }
            if(candidates.size()>(100-i)/10)
                break;

        }
        if(candidates.size()>0){
            int index = new Random().nextInt(candidates.size());
            result.add(candidates.get(index).getKey());
            cost+=candidates.get(index).getValue();
            addEgde(candidates.get(index).getKey());
        }
        return;
    }
    void printResult(){
        double min,max,avg=0;
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
        Pair<Double,List<Integer>> bestSolution = new Pair<>(cost,result);
        for(Pair<Double,List<Integer>> sol : totalResult){
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
        for(int v : bestSolution.getValue())
            res+=(v+",");
        try {
            PrintWriter writer = new PrintWriter("files//wynikGRASP.txt", "UTF-8");
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
