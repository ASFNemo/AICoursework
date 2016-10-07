import java.util.ArrayList;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class Lifo {

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

}
