import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by asherfischbaum on 07/10/2016.
 */
public class BFS {

    int boardSize;

    Fifo tree;
    StateNode finalState;

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;

    public BFS(int size) {
        this.boardSize = size;
        tree = new Fifo();

        createFinalPosition(size);

        StateNode startNode = createStartNode(size);
        tree.addConfig(startNode);
        start();



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
            switch (size) {
                case 2:
                    position[1] = 0;
                    break;
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

    public StateNode createStartNode(int boardSize){
        int[] a = {(boardSize -1), 0};
        int[] b = {(boardSize -1), 1};
        int[] c = {(boardSize -1), 2};
        int[] s = {(boardSize -1), (boardSize - 1)};

        return new StateNode(null, boardSize, a, b, c, s);
    }


    public void start(){

        int nodesExpanded = 0;

        while (true){

            StateNode node = tree.getNextNode();

            // check if it is the final node
            // if yes - print that it is in final state and how many expanded nodes it took
//            if ((node.getaPosition()[0] == finalAPosition[0] && node.getaPosition()[1] == finalAPosition[1]) &&
//                    ((node.getbPosition()[0] == finalBPosition[0] && node.getbPosition()[1] == finalBPosition[1]) || !node.isbBlockInUse()) &&
//                    ((node.getcPosition()[0] == finalCPosition[0] && node.getcPosition()[1] == finalCPosition[1])|| !node.iscBlockInUse())) {
            if (
                    ((node.getbPosition()[0] == finalBPosition[0] && node.getbPosition()[1] == finalBPosition[1]) || !node.isbBlockInUse()) &&
                    ((node.getcPosition()[0] == finalCPosition[0] && node.getcPosition()[1] == finalCPosition[1])|| !node.iscBlockInUse())) {
                // end and report how many nodes where expanded
                char[][] blocksWorld = node.getBlocksWorld();

                System.out.println("whe have gotten to the final position in: " + nodesExpanded + " expanded nodes");

                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }

                break;
            } else {
                // else
                // get the list of possible other directions
                char[] directions = randomDirection();
                // for each direction create a new node and check if it can be moved in that direction


                // for testing

                if (nodesExpanded < 30){
                    char[][] blocksWorld = node.getBlocksWorld();
                    System.out.println("the parent node");
                    for (int j = 0; j < blocksWorld.length; j++) {
                        System.out.println(blocksWorld[j]);
                    }
                }
                for(char direction: directions){

                    if (nodesExpanded < 30) {
                        System.out.println("child node direction: " + direction);
                    }

                    int[] aPosition = Arrays.copyOf(node.getaPosition(), node.getaPosition().length);
                    int[] bPosition = Arrays.copyOf(node.getbPosition(), node.getbPosition().length);
                    int[] cPosition = Arrays.copyOf(node.getcPosition(), node.getcPosition().length);
                    int[] agentPosition = Arrays.copyOf(node.getAgentPosition(), node.getAgentPosition().length);

                    StateNode childNode = new StateNode(node, node.getBoardSize(), aPosition,
                            bPosition, cPosition, agentPosition);
                    // if yes add them to the states children and add them to the tree
                    if (childNode.moveAgent(direction)){

                        if (nodesExpanded < 30){
                            char[][] blocksWorld = childNode.getBlocksWorld();
                            for (int j = 0; j < blocksWorld.length; j++) {
                                System.out.println(blocksWorld[j]);
                            }
                        }

                        node.addChild(childNode);
                        tree.addConfig(childNode);
                    }
                }

                // otherwise just move onto the next one.
                //

            }

            nodesExpanded++;
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

            max--;
        }

        return directionArr;

    }

    public Boolean checkIfFinished(StateNode node) {
        return false;
    }

}
