import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by asherfischbaum on 17/11/2016.
 */
public class SearchAlgorithms {
    StateNode finalState;

    int boardSize;

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;

    public SearchAlgorithms(int size){
        this.boardSize = size;
        createFinalPosition(size);
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




    public void startDFS(){

        Lifo tree;
        tree = new Lifo();
        StateNode startNode = createStartNode(this.boardSize);
        tree.addNode(startNode);

        ArrayList<Character> allMoves = new ArrayList<>();
        int moves = 0;
        while(true){
            // take the next element from the tree
            StateNode currentNode = tree.getNode();

            // FOR DEBUGGING PURPOSES ONLY, SHOW CURRENT BOARD STATE
            if (moves < 100 || moves == 1500 || moves == 15000 || moves == 90000 || moves == 150000 || moves == 2500000 || moves == 25000000) {
                char[][] blocksWorld = currentNode.getBlocksWorld();


                System.out.println("Node: " + moves);
                System.out.println("a position: " + currentNode.aPosition[0] +"," + currentNode.aPosition[1]);
                System.out.println("b position: " + currentNode.bPosition[0] +"," + currentNode.bPosition[1]);
                System.out.println("c position: " + currentNode.cPosition[0] +"," + currentNode.cPosition[1]);
                System.out.println("agent position: " + currentNode.agentPosition[0] +"," + currentNode.agentPosition[1]);
                System.out.println();
                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
            }

            // check if the Node is equal to final Node
            if ((currentNode.getaPosition()[0] == finalAPosition[0] && currentNode.getaPosition()[1] == finalAPosition[1]) &&
                    ((currentNode.getbPosition()[0] == finalBPosition[0] && currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse()) &&
                    ((currentNode.getcPosition()[0] == finalCPosition[0] && currentNode.getcPosition()[1] == finalCPosition[1])|| !currentNode.iscBlockInUse())){
                // if yes - print the final Node, how many nodes were searched and finish the system
                char[][] blocksWorld = currentNode.getBlocksWorld();
                System.out.println("Depth first search has been able to complete the puzzle in: " + moves + " moves!");
                for (int j = 0; j< blocksWorld.length; j++){
                    System.out.println(blocksWorld[j]);
                }

                System.out.println(allMoves);

                break;
            } else {
                //if no

                StateNode node = new StateNode(currentNode, currentNode.boardSize, currentNode.aPosition,
                        currentNode.bPosition, currentNode.cPosition, currentNode.agentPosition);

                boolean canMove = false;
                while (!canMove) {
                    // get random direction and apply to agent,
                    char direction = getDirection();
                    boolean didMove = node.moveAgent(direction);

                    if (moves < 100){
                        System.out.println("direction: " + direction + " did move? " + didMove);
                    }

                    if (didMove){
                        currentNode.addChild(node);
                        tree.addNode(node);
                        canMove = true;
                        allMoves.add(direction);
                    }
                    // if cannot move, repeat until one is accepted
                    // if move is accepted add to the queue
                }



            }
            moves++;
        }
    }


    public void startBFS(){

        Fifo tree;
        tree = new Fifo();
        StateNode startNode = createStartNode(this.boardSize);
        tree.addConfig(startNode);

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



    public char getDirection(){

        Random random = new Random();
        int min = 0;
        int max = 3;

        ArrayList possibleDirections = new ArrayList();
        possibleDirections.add('u');
        possibleDirections.add('d');
        possibleDirections.add('l');
        possibleDirections.add('r');


        char[] directionArr = new char[4];

        int directionPosition = random.nextInt((max+1) - min) + min;

        return (char) possibleDirections.get(directionPosition);

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

}
