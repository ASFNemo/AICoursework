import java.util.ArrayList;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class Fifo {

    ArrayList<StateNode> nodesArray;

    public Fifo() {
        this.nodesArray = nodesArray;
    }

    public void addConfig(StateNode node){
        this.nodesArray.add(node);
    }

    public StateNode getNextNode(){
        return this.nodesArray.get(0);
    }
}
