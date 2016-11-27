import javax.xml.soap.Node;
import java.util.*;

/**
 * Created by asherfischbaum on 17/11/2016.
 */
public class SearchAlgorithms {
    StateNode finalState;

    int boardSize;
    int numXTiles;

    int[] finalAPosition;
    int[] finalBPosition;
    int[] finalCPosition;
    int[][] xPositions;

    boolean xPositionUsed;



    public SearchAlgorithms(int size){
        this.boardSize = size;
        createFinalPosition(size);

        this.xPositionUsed = false;
    }

    public SearchAlgorithms(int size, int numXTiles){
        this.boardSize = size;
        this.numXTiles = numXTiles;

        createFinalPositionWithXUndefined(size, numXTiles);

        this.xPositionUsed = true;

    }

    public SearchAlgorithms(int size, int[][] Xpositions){
        this.numXTiles = xPositions.length;
        createFinalPositionWithXDefined(size, xPositions);
        this.xPositionUsed = true;
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

    public void createFinalPositionWithXUndefined(int size, int numXTiles){
        int[] finalAPosition = whereToPutA(size);
        int[] finalBPosition = whereToPutB(size);
        int[] finalCPosition = {(boardSize - 1), 1};

        int[] finalAgentPosition = {(boardSize -1), (boardSize -1)};

        finalState = new StateNode(null, size, numXTiles, finalAPosition, finalBPosition, finalCPosition, finalAgentPosition, 0);

        this.finalAPosition = finalState.getaPosition();
        this.finalBPosition = finalState.getbPosition();
        this.finalCPosition = finalState.getcPosition();
        this.xPositions = finalState.getxPositions();
    }

    public void createFinalPositionWithXDefined(int size, int[][] xPositions){
        int[] finalAPosition = whereToPutA(size);
        int[] finalBPosition = whereToPutB(size);
        int[] finalCPosition = {(boardSize - 1), 1};

        for (int i = 0; i < xPositions.length; i++){
            if (Arrays.equals(xPositions[i], finalAPosition) || Arrays.equals(finalBPosition, xPositions[i])
                    || Arrays.equals(xPositions[i], finalCPosition) || xPositions[i][0] == (size -1)){
                // todo: remove the element from the ARRAy!!!
            }
        }

        int[] finalAgentPosition = {(boardSize -1), (boardSize -1)};

        finalState = new StateNode(null, size, numXTiles, finalAPosition, finalBPosition, finalCPosition, finalAgentPosition, 0);

        this.finalAPosition = finalState.getaPosition();
        this.finalBPosition = finalState.getbPosition();
        this.finalCPosition = finalState.getcPosition();
        this.xPositions = xPositions;
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

        return new StateNode(null, size, a, b, c, s, 0);

    }

    public StateNode createStartNodeWithx(int size, int[][] xPositions){
        int[] a = {(size -1), 0};
        int[] b = {(size -1), 1};
        int[] c = {(size -1), 2};
        int[] s = {(size -1), (size - 1)};

        return new StateNode(null, size, xPositions, a, b, c, s, 0);
    }




    public StateNode startDFS() {

        Fifo tree;
        tree = new Fifo();



        StateNode startNode;

        StateNode finalNode;

        if (!this.xPositionUsed) {
            startNode = createStartNode(this.boardSize);
        } else {
            startNode = createStartNodeWithx(this.boardSize, this.xPositions);
        }

        tree.addConfig(startNode);

//        ArrayList<Character> allMoves = new ArrayList<>();
        int moves = 0;
        while (true) {
            // take the next element from the tree
            StateNode currentNode = tree.getNextNode();

            // FOR DEBUGGING PURPOSES ONLY, SHOW CURRENT BOARD STATE
            if (moves < 100 || moves == 1500 || moves == 15000 || moves == 90000 || moves == 150000 || moves == 2500000 || moves == 25000000) {
                char[][] blocksWorld = currentNode.getBlocksWorld();


                System.out.println("Node: " + moves);
                System.out.println("a position: " + currentNode.aPosition[0] + "," + currentNode.aPosition[1]);
                System.out.println("b position: " + currentNode.bPosition[0] + "," + currentNode.bPosition[1]);
                System.out.println("c position: " + currentNode.cPosition[0] + "," + currentNode.cPosition[1]);
                System.out.println("agent position: " + currentNode.agentPosition[0] + "," + currentNode.agentPosition[1]);
                System.out.println();
                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
            }

            // check if the Node is equal to final Node
            if ((currentNode.getaPosition()[0] == finalAPosition[0] && currentNode.getaPosition()[1] == finalAPosition[1]) &&
                    ((currentNode.getbPosition()[0] == finalBPosition[0] && currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse()) &&
                    ((currentNode.getcPosition()[0] == finalCPosition[0] && currentNode.getcPosition()[1] == finalCPosition[1]) || !currentNode.iscBlockInUse())) {
                // if yes - print the final Node, how many nodes were searched and finish the system
//                char[][] blocksWorld = currentNode.getBlocksWorld();
//                System.out.println("Depth first search has been able to complete the puzzle in: " + moves + " moves!");
//                for (int j = 0; j < blocksWorld.length; j++) {
//                    System.out.println(blocksWorld[j]);
//                }

                finalNode = currentNode;
                //System.out.println(allMoves);
                currentNode.setTotalNodesExpanded(tree.getLargestList());

                break;
            } else {
                //if no

                StateNode node;

                if (!this.xPositionUsed) {
                    node = new StateNode(currentNode, currentNode.boardSize, currentNode.aPosition,
                            currentNode.bPosition, currentNode.cPosition, currentNode.agentPosition, (currentNode.nodeDepth +1));
                } else {
                    node = new StateNode(currentNode, currentNode.boardSize, currentNode.getxPositions(), currentNode.aPosition,
                            currentNode.bPosition, currentNode.cPosition, currentNode.agentPosition, (currentNode.nodeDepth + 1));
                }

                boolean canMove = false;
                while (!canMove) {
                    // get random direction and apply to agent,
                    char direction = getDirection();
                    currentNode.setDirection(direction);
                    boolean didMove = node.moveAgent(direction);

                    if (moves < 100) {
                        System.out.println("direction: " + direction + " did move? " + didMove);
                    }

                    if (didMove) {
                        currentNode.addChild(node);
                        tree.addConfig(node);
                        canMove = true;
                        //allMoves.add(direction);
                    }
                    // if cannot move, repeat until one is accepted
                    // if move is accepted add to the queue
                }


            }
            moves++;
        }

        return finalNode;
    }




    public StateNode startBFS(){

        Fifo tree;
        tree = new Fifo();
        StateNode startNode;
        if (!this.xPositionUsed) {
            startNode = createStartNode(this.boardSize);
        } else {
            startNode = createStartNodeWithx(this.boardSize, this.xPositions);
        }
        tree.addConfig(startNode);

        int nodesExpanded = 0;
        StateNode finalNode;

        while (true){

            StateNode node = tree.getNextNode();

            // check if it is the final node
            // if yes - print that it is in final state and how many expanded nodes it took
//            if ((node.getaPosition()[0] == finalAPosition[0] && node.getaPosition()[1] == finalAPosition[1]) &&
//                    ((node.getbPosition()[0] == finalBPosition[0] && node.getbPosition()[1] == finalBPosition[1]) || !node.isbBlockInUse()) &&
//                    ((node.getcPosition()[0] == finalCPosition[0] && node.getcPosition()[1] == finalCPosition[1])|| !node.iscBlockInUse())) {
            if (((node.getaPosition()[0] == finalAPosition[0] && node.getaPosition()[1] == finalAPosition[1])) &&
                    ((node.getbPosition()[0] == finalBPosition[0] && node.getbPosition()[1] == finalBPosition[1]) || !node.isbBlockInUse()) &&
                            ((node.getcPosition()[0] == finalCPosition[0] && node.getcPosition()[1] == finalCPosition[1])|| !node.iscBlockInUse())) {
                // end and report how many nodes where expanded
                char[][] blocksWorld = node.getBlocksWorld();

                System.out.println("whe have gotten to the final position in: " + nodesExpanded + " expanded nodes");

                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
                finalNode = node;
                finalNode.setNoElementsInTree(tree.getLargestList());
                finalNode.setTotalNodesExpanded(nodesExpanded);
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

                    StateNode childNode;

                    if (!this.xPositionUsed) {
                       childNode = new StateNode(node, node.getBoardSize(), aPosition,
                                bPosition, cPosition, agentPosition);
                    }  else {
                        childNode = new StateNode(node, node.getBoardSize(),node.getxPositions(), aPosition,
                                bPosition, cPosition, agentPosition, 0);
                    }

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
        return finalNode;
    }


    public StateNode IDDFS(){
        StateNode startNode;
        if (!this.xPositionUsed) {
            startNode = createStartNode(this.boardSize);
        } else {
            startNode = createStartNodeWithx(this.boardSize, this.xPositions);
        }

        int i = 0;
        setNodesExpandedToZero();


        while (true){
            setNumberofNodesThisTimeToZero();
            StateNode found = DLS(startNode, i);
            System.out.println("current depth is: " + i);
            //System.out.println(" I am back from recursion " + i);

            if (found != null){
                //System.out.println("we found something at depth " + i);
                found.setNodeDepth(i);
                return found;
            }

            //System.out.println("not at this depth");
            i++;
        }
    }

    public StateNode DLS(StateNode currentNode, int depth){
        //System.out.println("here");
        if (depth == 0 && (currentNode.getaPosition()[0] == finalAPosition[0] &&
                currentNode.getaPosition()[1] == finalAPosition[1]) &&
                ((currentNode.getbPosition()[0] == finalBPosition[0] &&
                        currentNode.getbPosition()[1] == finalBPosition[1]) || !currentNode.isbBlockInUse())
                && ((currentNode.getcPosition()[0] == finalCPosition[0] &&
                currentNode.getcPosition()[1] == finalCPosition[1]) || !currentNode.iscBlockInUse())){
            //System.out.println("in the final state");
            return currentNode;
        }

        if (depth > 0){

            //System.out.println(" in the depth greater than zero");

            char[] move = {'u', 'd', 'l', 'r'};

            for (char moveTO : move){
                int[] aPosition = Arrays.copyOf(currentNode.getaPosition(), currentNode.getaPosition().length);
                int[] bPosition = Arrays.copyOf(currentNode.getbPosition(), currentNode.getbPosition().length);
                int[] cPosition = Arrays.copyOf(currentNode.getcPosition(), currentNode.getcPosition().length);
                int[] agentPosition = Arrays.copyOf(currentNode.getAgentPosition(), currentNode.getAgentPosition().length);

                StateNode child;

                if (!this.xPositionUsed) {
                    child = new StateNode(currentNode, currentNode.boardSize, aPosition,
                            bPosition, cPosition, agentPosition, 0);
                } else {
                    child = new StateNode(currentNode, currentNode.boardSize, currentNode.getxPositions(),
                            aPosition, bPosition, cPosition, agentPosition, 0);
                }
                if (child.moveAgent(moveTO)) {
                    incrementNumberofNodesThisTime();
                    incrementExpandedNodes();
                    child.setDirection(moveTO);
                    StateNode found = DLS(child, (depth - 1));

                    if (found != null){
                        found.setNoElementsInTree(totalnumberofNodesThisTime());
                        found.setTotalNodesExpanded(totalNodesExpanded());
                        child.setDirection(moveTO);
                        return found;
                    }
                }



            }



        }
        //System.out.println("about to return null");
        return null;
    }

    private int numberofNodesThisTime = 0;
    private void setNumberofNodesThisTimeToZero(){
        this.numberofNodesThisTime = 0;
    }
    private void incrementNumberofNodesThisTime(){
        this.numberofNodesThisTime++;

    }
    private int totalnumberofNodesThisTime(){
        return this.numberofNodesThisTime;
    }

    int nodesExpanded = 0;
    private void setNodesExpandedToZero(){
        this.nodesExpanded = 0;
    }
    private void incrementExpandedNodes(){
        this.nodesExpanded++;

    }
    private int totalNodesExpanded(){
        return this.nodesExpanded;
    }


    public StateNode StartAStar(){

        StateNode finalNode = null;
        int moves = 0;
        int nodesExpanded = 0;

        // create a hashmap that takes in a key = total cost and a value = the node
        HashMap<StateNode, Integer> movesMap = new HashMap<>();

        // create the startNode and add it to the hashmap;
        StateNode startNode;

        if (!this.xPositionUsed) {
            startNode = createStartNode(this.boardSize);
        } else {
            startNode = createStartNodeWithx(this.boardSize, this.xPositions);
        }

        // Manhattan Distance
        System.out.println("using manhattan distance");
        int movesCost = startNode.cost(0, futureCost(startNode.getaPosition(), startNode.getbPosition(), startNode.getcPosition()));
        // Hamming
        //System.out.println("using hamming distance");
        //int movesCost = startNode.cost(0, hammingFutureCost(startNode.getaPosition(), startNode.getbPosition(), startNode.getcPosition()));
        movesMap.put(startNode, movesCost);

        while (true) {
            // find the total least expensive node
            StateNode cheapestMoveNode = getMinKey(movesMap);
            movesMap.remove(cheapestMoveNode);


            // FOR DEBUGGING PURPOSES ONLY, SHOW CURRENT BOARD STATE
            if (moves < 100 || moves == 1500 || moves == 15000 || moves == 90000 || moves == 150000 || moves == 2500000 || moves == 25000000) {
                char[][] blocksWorld = cheapestMoveNode.getBlocksWorld();


                System.out.println("Node: " + moves);
                System.out.println("a position: " + cheapestMoveNode.aPosition[0] + "," + cheapestMoveNode.aPosition[1]);
                System.out.println("b position: " + cheapestMoveNode.bPosition[0] + "," + cheapestMoveNode.bPosition[1]);
                System.out.println("c position: " + cheapestMoveNode.cPosition[0] + "," + cheapestMoveNode.cPosition[1]);
                System.out.println("agent position: " + cheapestMoveNode.agentPosition[0] + "," + cheapestMoveNode.agentPosition[1]);
                System.out.println("curret cost: " + cheapestMoveNode.getNodeDepth());

                for (int j = 0; j < blocksWorld.length; j++) {
                    System.out.println(blocksWorld[j]);
                }
            }



            // check if the node is the final node
            if ((cheapestMoveNode.getaPosition()[0] == finalAPosition[0] && cheapestMoveNode.getaPosition()[1] == finalAPosition[1]) &&
                    ((cheapestMoveNode.getbPosition()[0] == finalBPosition[0] && cheapestMoveNode.getbPosition()[1] == finalBPosition[1]) || !cheapestMoveNode.isbBlockInUse()) &&
                    ((cheapestMoveNode.getcPosition()[0] == finalCPosition[0] && cheapestMoveNode.getcPosition()[1] == finalCPosition[1]) || !cheapestMoveNode.iscBlockInUse())) {



                finalNode = cheapestMoveNode;
                finalNode.setNoElementsInTree(movesMap.size());
                finalNode.setTotalNodesExpanded(nodesExpanded);
                break;
            } else {
                // create a 4 children that move in the 4 directions

                char[] move = {'u', 'd', 'l', 'r'};

                for (char moveTo : move) {
                    int[] aPosition = Arrays.copyOf(cheapestMoveNode.getaPosition(), cheapestMoveNode.getaPosition().length);
                    int[] bPosition = Arrays.copyOf(cheapestMoveNode.getbPosition(), cheapestMoveNode.getbPosition().length);
                    int[] cPosition = Arrays.copyOf(cheapestMoveNode.getcPosition(), cheapestMoveNode.getcPosition().length);
                    int[] agentPosition = Arrays.copyOf(cheapestMoveNode.getAgentPosition(), cheapestMoveNode.getAgentPosition().length);

                    StateNode child;

                    if (!this.xPositionUsed) {
                        child = new StateNode(cheapestMoveNode, cheapestMoveNode.boardSize, aPosition,
                                bPosition, cPosition, agentPosition, (cheapestMoveNode.nodeDepth + 1));
                    } else {
                        child = new StateNode(cheapestMoveNode, cheapestMoveNode.boardSize, cheapestMoveNode.getxPositions(),
                                aPosition, bPosition, cPosition, agentPosition, (cheapestMoveNode.nodeDepth + 1));
                    }
                    if (child.moveAgent(moveTo)) {
                        //Manhattan distance
                        int cost = child.getNodeDepth() + futureCost(child.getaPosition(), child.getbPosition(), child.getcPosition());
                        // HAMMING DISTANCE
                        //int cost = child.getNodeDepth() + hammingFutureCost(child.getaPosition(), child.getbPosition(), child.getcPosition());

                        // the ones that can be moved, find their total cost and add to the tree
                        movesMap.put(child, cost);
                        nodesExpanded++;
                        child.setDirection(moveTo);

                    }


                }

            }
            moves++;
        }


        return finalNode;

    }

    private StateNode getMinKey(HashMap<StateNode, Integer> movesMap){
        StateNode minKey = null;
        int minValue = Integer.MAX_VALUE;

        for (StateNode key: movesMap.keySet()){
            int cost = movesMap.get(key);
            if (cost < minValue){
                minValue = cost;
                minKey = key;
            }
        }
        return minKey;


    }

    // TODO IMPLEMENT ASTAR WITH MANHATTAN WHICH USES THE DISTANCE FROM THE CORRECT POSITION
    // TODO IMPLEMENT ASTAR WITH HAMMING DISTANCE WHIH USES HOW MANY NODES ARE OUT OF PLACE


//    public int h1n(){
//
//    }



    public int futureCost(int[] currentA, int[] currentB, int[] currentC){
        int cost = 0;

        int cost1 = Math.abs(currentA[0] - finalAPosition[0]) + Math.abs(currentA[1] - finalAPosition[1]);
        int cost2 = Math.abs(currentB[0] - finalBPosition[0]) + Math.abs(currentB[1] - finalBPosition[1]);
        int cost3 = Math.abs(currentC[0] - finalCPosition[0]) + Math.abs(currentC[1] - finalCPosition[1]);

        cost = cost + cost1 + cost2 + cost3;

        return cost;

    }

    public int hammingFutureCost(int[] currentA, int[] currentB, int[] currentC){
        int cost = 0;

        if (!(currentA[0] == finalAPosition[0] &&currentA[1] == finalAPosition[1])){
            cost++;
        }

        if (!(currentB[0] == finalBPosition[0] && currentB[1] == finalBPosition[1])){
            cost++;
        }

        if (!(currentC[0] == finalCPosition[0] && currentC[1] == finalCPosition[1])){
            cost++;
        }

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
