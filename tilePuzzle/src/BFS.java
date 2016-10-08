import java.util.ArrayList;
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


//        char[][] blockworld = finalState.getBlocksWorld();
//
//        System.out.println("[[[[[");
//        for (int i = 0; i < blockworld.length; i++){
//            System.out.println(blockworld[i]);
      //  }



    }

    public void createFinalPosition(int size){
        int[] finalAPosition = whereToPutA(size);
        int[] finalBPosition = whereToPutB(size);
        int[] finalCPosition = {(boardSize - 1), 1};
        int[] finalAgentPosition = {(boardSize -1), (boardSize -1)};

        finalState = new StateNode(size, finalAPosition, finalBPosition, finalCPosition, finalAgentPosition);

        finalAPosition = finalState.getaPosition();
        finalBPosition = finalState.getbPosition();
        finalCPosition = finalState.getcPosition();
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

        return new StateNode(boardSize, a, b, c, s);
    }

    public void start(){

        //int nodesSeen = 0;
        int i = 0;
        while (i < 10) {
            StateNode currentNode = tree.getNextNode(); // make this into a new variable so that i can reassign it later in loops
            //nodesSeen++;
            if (i < 10){
                char[][] blocksWorld = currentNode.getBlocksWorld();

                System.out.println("node: " + i);
                System.out.println("A; " + currentNode.getaPosition()[0] +"," + currentNode.getaPosition()[1] + " B: "
                        + currentNode.getbPosition()[0] +"," +currentNode.getbPosition()[1] + " C: " +
                        currentNode.getcPosition()[0]+","+ currentNode.getcPosition()[1] + " Agent: " +
                        currentNode.getAgentPosition()[0]+","+currentNode.getAgentPosition()[1] + " board size: " +
                        currentNode.getBoardSize());
                for (int j = 0; j< blocksWorld.length; j++){
                    System.out.println(blocksWorld[j]);
                }
                i++;
            }

            // check if it is equivilant to the final state

            if (currentNode.getaPosition() == finalAPosition && currentNode.getbPosition() == finalBPosition &&
                    currentNode.getcPosition() == finalCPosition) {
                // end and report how many nodes where expanded
                System.out.println("whe have gotten to the final position, here you go: :)");

                char[][] blocksWorld = currentNode.getBlocksWorld();

                System.out.println("node: " + i);
                for (int j = 0; j< blocksWorld.length; j++){
                    System.out.println(blocksWorld[j]);
                }

                break;
            } else {

                // get a set of directions to move the agent

                char[] moveDirections = randomDirection();

                // apply each direction to the start node

                for (char direction : moveDirections) {
                    StateNode n = new StateNode(currentNode.getBlocksWorld(), currentNode.getAgentPosition(),
                            currentNode.getaPosition(), currentNode.getbPosition(), currentNode.getcPosition());
                    // add each new board state to the Fifo list (tree)
                    if (n.moveAgent(direction)) {
                        tree.addConfig(n);
                    } else {
                        // work out what to do if it does not work, this will be due to the move taking the agent of the board
                    }

                    // then remove the first elemetn from the tree (list)
                    //tree.removeUsedNode();


                }


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

            max--;
        }

        return directionArr;

    }

    public Boolean checkIfFinished(StateNode node) {
        return false;
    }

}
