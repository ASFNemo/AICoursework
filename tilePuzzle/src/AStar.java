import java.util.*;

/**
 * Created by asherfischbaum on 10/11/2016.
 */
public class AStar {
    int boardSize;

    StateNode finalState;

    HashMap<StateNode, Integer > movesMap = new HashMap<>();

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;

    public AStar(int size) {
        this.boardSize = size;

        createFinalPosition(size);

        StateNode startNode = createStartNode(size);
        int movesCost = startNode.cost(0, futureCost(startNode.getaPosition(), startNode.getbPosition(), startNode.getcPosition(), startNode.getAgentPosition()));
        movesMap.put(startNode, movesCost);
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
        // this is just to initialize. the first will get changed as soon as we get into the loop
        StateNode currentNode = movesMap.entrySet().iterator().next().getKey();
        int currentCheapest = (int) Math.pow(boardSize, Math.pow(boardSize, boardSize));
        int nodesExpanded  = 0;
        // while a solution has not been found
        while (true) {
            // loop through the list
            int i = 0;
            for (Map.Entry<StateNode, Integer> state : movesMap.entrySet()) {
                if (i == 0) {
                    currentNode = state.getKey();
                    currentCheapest = state.getValue();
                    //movesMap.remove(state.getKey());
                    i++;
                }


                // check which node has the lowest cost, hold it in move node until we have found something cheaper.
                if (state.getValue() < currentCheapest) {
                    //movesMap.put(currentNode, currentCheapest);
                    currentNode = state.getKey();
                    currentCheapest = state.getValue();

                }
            }
            movesMap.remove(movesMap.remove(currentNode));
            if (nodesExpanded < 100 || nodesExpanded == 1000 || nodesExpanded == 10000){
                System.out.println("node number: " + nodesExpanded);
                char[][] blocksWorld = currentNode.getBlocksWorld();
                System.out.println("the cost is: " + currentCheapest);
                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
            }

            if ((currentNode.getaPosition()[0] == finalAPosition[0] && currentNode.getaPosition()[1] == finalAPosition[1]) &&
                    ((currentNode.getbPosition()[0] == finalBPosition[0] && currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse()) &&
                    ((currentNode.getcPosition()[0] == finalCPosition[0] && currentNode.getcPosition()[1] == finalCPosition[1]) || !currentNode.iscBlockInUse())) {

                // if it is the final state, set nodeFound to true, return the node and how many moves it did
                //nodeFound = true;

                //if yes - print the final Node, how many nodes were searched and finish the system
                char[][] blocksWorld = currentNode.getBlocksWorld();
                System.out.println("A* search has been able to complete the puzzle in: " + nodesExpanded + " moves!");
                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
                break;
            }

            //once we have the cheapes node
            // expand and get all potential children direction

            char[] directions = randomDirection();
            for (char direction : directions) {


                int[] aPosition = Arrays.copyOf(currentNode.getaPosition(), currentNode.getaPosition().length);
                int[] bPosition = Arrays.copyOf(currentNode.getbPosition(), currentNode.getbPosition().length);
                int[] cPosition = Arrays.copyOf(currentNode.getcPosition(), currentNode.getcPosition().length);
                int[] agentPosition = Arrays.copyOf(currentNode.getAgentPosition(), currentNode.getAgentPosition().length);
                int ammassedCost = currentNode.getCurrentCost();

                StateNode childNode = new StateNode(currentNode, currentNode.getBoardSize(), aPosition,
                        bPosition, cPosition, agentPosition);
                childNode.setCurrentCost(ammassedCost);
                // if yes add them to the states children and add them to the tree
                if (childNode.moveAgent(direction)) {
                    currentNode.addChild(childNode);
                    int[] childA = Arrays.copyOf(childNode.getaPosition(), childNode.getaPosition().length);
                    int[] childB = Arrays.copyOf(childNode.getbPosition(), childNode.getbPosition().length);
                    int[] childC = Arrays.copyOf(childNode.getcPosition(), childNode.getcPosition().length);
                    int[] childAgent = Arrays.copyOf(childNode.getAgentPosition(), childNode.getAgentPosition().length);
                    // increment current cost
                    childNode.incrementCost();

                    // for each direction work out the cost of choosing that node
                    int cost = futureCost(childA, childB, childC, childAgent);

                    //System.out.println("current cost: "  + childNode.getCurrentCost() + ", Future cost: " + cost);
                    int totalCost = childNode.cost(childNode.getCurrentCost(), cost);
                    //System.out.println("total cost: " + totalCost);
                    movesMap.put(childNode, totalCost);
                }
            }
            nodesExpanded++;

        }


    }

    public int futureCost(int[] currentA, int[] currentB, int[] currentC, int[] currentAgent){
        int cost = 0;

        int cost1 = Math.abs(currentA[0] - finalAPosition[0]) + Math.abs(currentA[1] - finalAPosition[1]);
        int cost2 = Math.abs(currentB[0] - finalBPosition[0]) + Math.abs(currentB[1] - finalBPosition[1]);
        int cost3 = Math.abs(currentC[0] = finalCPosition[0]) + Math.abs(currentC[1] - finalCPosition[1]);

        if (cost1 == 0 && cost2 == 0){
            // distance from c
            cost = Math.abs(finalCPosition[0] - currentAgent[0]) + Math.abs(finalCPosition[1] - currentAgent[1]);
        } else if (cost1 == 0 && cost3 == 0){
            // distance from b
            cost = Math.abs(finalBPosition[0] - currentAgent[0]) + Math.abs(finalBPosition[1] - currentAgent[1]);
        } else if (cost2 == 0 &&  cost3 == 0){
            // distance from a
            cost = Math.abs(finalAPosition[0] - currentAgent[0]) + Math.abs(finalAPosition[1] - currentAgent[1]);
        }

        cost = cost + cost1 + cost2 + cost3;

        return cost;

    }

    public char[] randomDirection(){

        Random random = new Random();
        int min =0;
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
