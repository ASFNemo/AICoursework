import java.util.ArrayList;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class Fifo {

    /**
     * First in first out list that represents a the tree in breadth first search
     */

    ArrayList<StateNode> nodesArray;
    int elementsInList = 0;
    int largestList = 0;

    public Fifo() {
        this.nodesArray = new ArrayList<>();
    }

    public void addConfig(StateNode node){
        this.nodesArray.add(node);

        elementsInList++;

        if (elementsInList > largestList){
            largestList = elementsInList;
        }
        //System.out.println(nodesArray.size());


    }

    public StateNode getNextNode() {
        StateNode node = this.nodesArray.get(0);
        removeUsedNode();
        elementsInList--;
        return node;
    }

    public void removeUsedNode(){
        this.nodesArray.remove(0);
    }


    public int getLargestList() {
        return largestList;
    }
}
