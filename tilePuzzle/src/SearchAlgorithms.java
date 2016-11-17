import java.util.*;

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

    public void startIDDFS(){

        Lifo tree;
        tree = new Lifo();
        StateNode startNode = createStartNode(this.boardSize);
        tree.addNode(startNode);

        int maxdeapth = 0;
        int currentDepth = 0;
        int nodesExpanded = 0;
        boolean nodeFound = false;

        while (!nodeFound){
            // check if tree is empty
            if (!tree.isEmpty()){
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
                    System.out.println("node depth : " + currentNode.getNodeDepth());
                    System.out.println();
                    for (int j = 0; j < blocksWorld.length; j++) {
                        System.out.println(blocksWorld[j]);
                    }
                }

                //check if it is a final state
                if ((currentNode.getaPosition()[0] == finalAPosition[0] && currentNode.getaPosition()[1] == finalAPosition[1]) &&
                        ((currentNode.getbPosition()[0] == finalBPosition[0] && currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse()) &&
                        ((currentNode.getcPosition()[0] == finalCPosition[0] && currentNode.getcPosition()[1] == finalCPosition[1])|| !currentNode.iscBlockInUse())) {

                    // if it is the final state, set nodeFound to true, return the node and how many moves it did
                    //nodeFound = true;

                    //if yes - print the final Node, how many nodes were searched and finish the system
                    char[][] blocksWorld = currentNode.getBlocksWorld();
                    System.out.println("Depth first search has been able to complete the puzzle in: " + nodesExpanded + " moves!");
                    System.out.println("the search went to a depth of " + currentNode.getNodeDepth());
                    for (int j = 0; j< blocksWorld.length; j++){
                        System.out.println(blocksWorld[j]);
                    }
                    break;
                } else {




                    // else check if curren node depth is less than max depth
                    if (currentNode.getNodeDepth() < maxdeapth){
                        // if yes pass
                        char[] move = randomDirection();

                        for (char moveTo: move) {

                            if (nodesExpanded < 30) {
                                System.out.println("child node direction: " + moveTo);
                            }

                            int[] aPosition = Arrays.copyOf(currentNode.getaPosition(), currentNode.getaPosition().length);
                            int[] bPosition = Arrays.copyOf(currentNode.getbPosition(), currentNode.getbPosition().length);
                            int[] cPosition = Arrays.copyOf(currentNode.getcPosition(), currentNode.getcPosition().length);
                            int[] agentPosition = Arrays.copyOf(currentNode.getAgentPosition(), currentNode.getAgentPosition().length);
                            int nodeDepth = currentNode.getNodeDepth();


                            StateNode childNode = new StateNode(currentNode, currentNode.getBoardSize(), aPosition,
                                    bPosition, cPosition, agentPosition, (nodeDepth +1));

                            // find its children and check if you can move there
                            if (childNode.moveAgent(moveTo)){
                                // if you  can move there, add them to the tree
                                tree.addNode(childNode);
                            }

                        }

                        nodesExpanded++;
                    } else {

                        tree.clearTree();
                        tree.addNode(startNode);
                        maxdeapth++;

                    }


                }


            } else {
                // otherwise increment max tree depth
                maxdeapth++;
                System.out.println(maxdeapth);
            }





        }
    }



    public void startAStar(){

        HashMap<StateNode, Integer > movesMap = new HashMap<>();

        StateNode startNode = createStartNode(this.boardSize);
        int movesCost = startNode.cost(0, futureCost(startNode.getaPosition(), startNode.getbPosition(), startNode.getcPosition(), startNode.getAgentPosition()));
        movesMap.put(startNode, movesCost);

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
