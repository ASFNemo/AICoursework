import java.util.ArrayList;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class Lifo {

    /**
     *  Last in first out that represents the tree in depth firs search
     */

    ArrayList<StateNode> nodesArray;

    public Lifo() {
        this.nodesArray = new ArrayList<>();
    }

    public void addNode(StateNode node){
        this.nodesArray.add(0, node);
    }

    public StateNode getNode(){
        return this.nodesArray.get(0);
    }

    public boolean isEmpty(){
        return nodesArray.isEmpty();
    }

    public void clearTree(){
        this.nodesArray.clear();
    }


}
