import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by asherfischbaum on 29/10/2016.
 */
public class IDDFS {

    StateNode finalState;

    int boardSize;

    Lifo tree;

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;

    public IDDFS(int size){
        this.boardSize = size;
        tree = new Lifo();
        createFinalPosition(size);

        StateNode startNode = createStartNode(size);
        tree.addNode(startNode);
        startSearch();
    }


    public void createFinalPosition(int size){
        int[] finalAPosition = whereToPutA(size);
        int[] finalBPosition = whereToPutB(size);
        int[] finalCPosition = {(boardSize - 1), 1};

        int[] finalAgentPosition = {(boardSize -1), (boardSize -1)};

        finalState = new StateNode(null, size, finalAPosition, finalBPosition, finalCPosition, finalAgentPosition);

        this.finalAPosition = finalState.getaPosition();
        this.finalBPosition = finalState.getbPosition();
        this.finalCPosition = finalState.getcPosition();
    }

    public int[] whereToPutA(int size){
        int[] position = {1, 1};
        if (size < 4) {
            if (size == 2){
                position[1] = 0;
            }
        } else {
            position[0] = (size - 3);
        }
        return position;
    }

    public int[] whereToPutB(int size){
        int[] position = {(boardSize - 2),1};

        if (size == 3){
            position[0] = 2;
            position[1] = 1;
        }

        return position;
    }

    public StateNode createStartNode(int size){
        int[] a = {(size -1), 0};
        int[] b = {(size -1), 1};
        int[] c = {(size -1), 2};

        int[] s = {(size -1), (size - 1)};

        return new StateNode(null, size, a, b, c, s);

    }

    public void startSearch(){

        int maxdeapth = 0;
        int currentDepth = 0;
        int nodesExpanded = 0;
        boolean nodeFound = false;

        while (!nodeFound){
            // check if tree is empty
            if (tree.isEmpty()){
                // if yes, do all the stuff,

                // get current node
                StateNode currentNode = tree.getNode();

                if (nodesExpanded < 100 || nodesExpanded == 1500 || nodesExpanded == 15000 || nodesExpanded == 90100 || nodesExpanded == 150000
                        || nodesExpanded == 2500000 || nodesExpanded == 25000000) {
                    char[][] blocksWorld = currentNode.getBlocksWorld();


                    System.out.println("Node: " + nodesExpanded);
                    System.out.println("a-position: " + currentNode.aPosition[0] +"," + currentNode.aPosition[1]);
                    System.out.println("b-position: " + currentNode.bPosition[0] +"," + currentNode.bPosition[1]);
                    System.out.println("c-position: " + currentNode.cPosition[0] +"," + currentNode.cPosition[1]);
                    System.out.println("agent-position: " + currentNode.agentPosition[0] +"," + currentNode.agentPosition[1]);
                    System.out.println();
                    for (int j = 0; j < blocksWorld.length; j++) {
                        System.out.println(blocksWorld[j]);
                    }
                }

                //check if it is a final state
                if ((currentNode.getaPosition()[0] == finalAPosition[0] && currentNode.getaPosition()[1] == finalAPosition[1]) &&
                        ((currentNode.getbPosition()[0] == finalBPosition[0] && currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse()) &&
                        (currentNode.getcPosition() == finalCPosition || !currentNode.iscBlockInUse())) {

                    // if it is the final state, set nodeFound to true, return the node and how many moves it did
                    nodeFound = true;

                    // if yes - print the final Node, how many nodes were searched and finish the system
                    char[][] blocksWorld = currentNode.getBlocksWorld();
                    //System.out.println("Depth first search has been able to complete the puzzle in: " + moves + " moves!");
                    for (int j = 0; j< blocksWorld.length; j++){
                        System.out.println(blocksWorld[j]);
                    }
                    break;
                } else {
                    // else check if curren node depth is less than max depth
                    if (currentNode.getNodeDepth() < maxdeapth){
                        // if yes pass
                    } else {
                        char[] move = randomDirection();

                        for (char moveTo: move) {

                            if (nodesExpanded < 30) {
                                System.out.println("child node direction: " + moveTo);
                            }

                            int[] aPosition = Arrays.copyOf(currentNode.getaPosition(), currentNode.getaPosition().length);
                            int[] bPosition = Arrays.copyOf(currentNode.getbPosition(), currentNode.getbPosition().length);
                            int[] cPosition = Arrays.copyOf(currentNode.getcPosition(), currentNode.getcPosition().length);
                            int[] agentPosition = Arrays.copyOf(currentNode.getAgentPosition(), currentNode.getAgentPosition().length);


                            StateNode childNode = new StateNode(currentNode, currentNode.getBoardSize(), aPosition,
                                    bPosition, cPosition, agentPosition, (currentNode.getNodeDepth() +1));

                            // find its children and check if you can move there
                            if (childNode.moveAgent(moveTo)){
                                // if you  can move there, add them to the tree
                                tree.addNode(currentNode);
                            }

                        }

                        nodesExpanded++;

                    }


                }


            } else {
                // otherwise increment max tree depth
                maxdeapth++;
            }





        }

    }

    public char[] randomDirection(){

        Random random = new Random();
        int min = 0;
        int max = 3;

        ArrayList possibleDirections = new ArrayList();
        possibleDirections.add('u');
        possibleDirections.add('d');
        possibleDirections.add('l');
        possibleDirections.add('r');

        char[] directionArr = new char[4];

        for (int i = 0; i < 4; i++){
            int directionPosition = random.nextInt((max+1) - min) + min;

            directionArr[i] = (char) possibleDirections.get(directionPosition);
            possibleDirections.remove(directionPosition);

            max = max - 1;
        }

        return directionArr;

    }



}
