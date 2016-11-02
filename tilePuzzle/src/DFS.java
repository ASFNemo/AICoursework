import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asherfischbaum on 08/10/2016.
 */
public class DFS {

    Lifo tree;
    StateNode finalState;

    int boardSize;

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;

    public DFS(int size){
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
}
