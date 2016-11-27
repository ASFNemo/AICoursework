import java.util.ArrayList;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class Lifo {

    /**
     *  Last in first out that represents the tree in depth firs search
     */

    ArrayList<StateNode> nodesArray;
    int elementsInList = 0;
    int largestList = 0;

    public Lifo() {
        this.nodesArray = new ArrayList<>();
    }

    public void addNode(StateNode node){
        this.nodesArray.add(0, node);
        elementsInList++;

        if (elementsInList > largestList){
            largestList = elementsInList;
        }
    }

    public StateNode getNode(){
        elementsInList--;
        return this.nodesArray.get(0);

    }


    public int getLargestList() {
        return largestList;
    }


}
