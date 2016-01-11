package lab1;

/**
 * Created by Mikolaj on 2015-12-12.
 */
public class Main {
    public static void main(String args[]){
        Graph g = new Graph("files//kroA100.xml");
        NN neigh = new NN(g);
        neigh.solv();
        Grasp gr = new Grasp(g);
        gr.solv();
        Greedy greedy = new Greedy(g);
        greedy.solv();
    }

}
