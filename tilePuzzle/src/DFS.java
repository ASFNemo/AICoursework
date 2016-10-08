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

        finalState = new StateNode(size, finalAPosition, finalBPosition, finalCPosition, finalAgentPosition);

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

        return new StateNode(size, a, b, c, s);

    }

    public void startSearch(){
        int moves = 0;
        while(true){
            // take the next element from the tree
            StateNode currentNode = tree.getNode();

            // FOR DEBUGGING PURPOSES ONLY, SHOW CURRENT BOARD STATE
            if (moves < 15 || moves == 1500 || moves == 15000 || moves == 90000 || moves == 150000 || moves == 2500000 || moves == 25000000) {
                char[][] blocksWorld = currentNode.getBlocksWorld();

                System.out.println("Node: " + moves);
                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
            }

            // check if the node is equal to final node
            if (currentNode.getaPosition() == finalAPosition && currentNode.getbPosition() == finalBPosition &&
                    currentNode.getcPosition() == finalCPosition){
                // if yes - print the final node, how many nodes were searched and finish the system
                char[][] blocksWorld = currentNode.getBlocksWorld();
                System.out.println("Depth first search has been able to complete the puzzle in: " + moves + " moves!");
                for (int j = 0; j< blocksWorld.length; j++){
                    System.out.println(blocksWorld[j]);
                }

                break;
            } else {
                //if no
                boolean canMove = false;
                while (!canMove) {
                    // get random direction and apply to agent,
                    if (currentNode.moveAgent(getDirection())){
                        tree.addNode(currentNode);
                        canMove = true;
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
