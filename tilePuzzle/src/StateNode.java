import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by asherfischbaum on 06/10/2016.
 */
public class StateNode {

    //private final char wall = 'w';
//    private final char plainTile = 'p';
//    private final char tileA = 'a';
//    private final char tileB = 'b';
//    private final char tileC = 'c';
//    private final char smileTile = 's';

    StateNode parentNode;
    ArrayList<StateNode> children;

    int[] aPosition;
    int[] bPosition;
    int[] cPosition;
    int[] agentPosition;
    int[][] xPositions;

    int currentCost = 0;
    int projectedCost = 0;

    int boardSize;

    boolean bBlockInUse = false;
    boolean cBlockInUse = false;
    boolean xPositionUse = false;

    char[][] blocksWorld;

    int nodeDepth;

    public StateNode(StateNode parent, int boardSizeN, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion) {

        /**
         Use this constructor when you are creating the initial and final state
         */

        createNode(parent, boardSizeN, TAPosition, TBPosition, TCPosition, agentsPostion);


    }

    public StateNode(StateNode parent, int boardSizeN, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion, int depth) {

        /**
         Use this constructor when you are creating the initial and final state
         */

        createNode(parent, boardSizeN, TAPosition, TBPosition, TCPosition, agentsPostion);

        this.nodeDepth = depth;

    }


    public StateNode(StateNode parent, int boardSizeN, int[][] XTilesPosition, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion, int depth) {

        /**
         Use this constructor when you want to specify where to put the X-Tiles
         */

        createNodeWithX(parent, boardSizeN, XTilesPosition, TAPosition, TBPosition, TCPosition, agentsPostion);

        this.nodeDepth = depth;

    }

    public StateNode(StateNode parent, int boardSizeN, int numXTiles, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion, int depth) {

        /**
         Use this constructor when you want to put in a specific number of randomly placed Xtiles.
         */

        Random rn = new Random();

        int[][] XTilesPosition = new int[numXTiles][2];

        int i = 0;

        while (i < numXTiles) {
            int[] newArray = new int[2];
            newArray[0] = rn.nextInt((boardSizeN - 2) + 1);
            System.out.println("i" + newArray[0]);
            newArray[1] = rn.nextInt((boardSizeN - 1) + 1);
            System.out.println("ii" + newArray[1]);

//            int k = 0;
//            int j = 0;
//            while (j < XTilesPosition.length)
            if (!Arrays.equals(XTilesPosition[i], newArray) && !Arrays.equals(TAPosition, newArray)
                    && !Arrays.equals(TBPosition, newArray) && !Arrays.equals(TCPosition, newArray)
                    && !Arrays.equals(agentsPostion, newArray)) {
                //k++;
                XTilesPosition[i][0] = newArray[0];
                XTilesPosition[i][1] = newArray[1];
                i++;
            }

        }

        for (int l = 0; l < XTilesPosition.length; l++ ){
                System.out.println(XTilesPosition[l][0] + "," + XTilesPosition[l][1]);

        }


        createNodeWithX(parent, boardSizeN, XTilesPosition, TAPosition, TBPosition, TCPosition, agentsPostion);

        this.nodeDepth = depth;
    }





    // add another constructor that only takes the matrix and the current agent position.


//    public StateNode(StateNode parent, char[][] blocksWorld, int[] agentPosition, int[] positionA, int[] positionB, int[] positionC) {
//        /**
//         * use this constructor when you are creating subsequent nodes
//         */
//
//        this.parentNode = parent;
//        children = new ArrayList<>();
//
//        this.blocksWorld = blocksWorld;
//        this.agentPosition = agentPosition;
//        this.aPosition = positionA;
//        this.bPosition = positionB;
//        this.cPosition = positionC;
//
////        System.out.println("==========");
////        for (int i = 0; i < blocksWorld.length; i++){
////            System.out.println(blocksWorld[i]);
////        }
//    }

    private void createNode(StateNode parent, int boardSizeN, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion){
        this.parentNode = parent;
        children = new ArrayList<>();

        aPosition = new int[2];
        bPosition = new int[2];
        cPosition = new int[2];
        agentPosition = new int[2];
        blocksWorld = new char[boardSizeN][boardSizeN];

        this.boardSize = boardSizeN;

        if (boardSizeN > 1) {
            for (int i = 0; i < boardSizeN; i++) { // this will be to loop through the rows
                for (int j = 0; j < boardSizeN; j++) { // this if for looping through the columns



                    // we check if this tile should be one of the specified, otherwise it becomes a plane tile.
                    if (i == TAPosition[0] && j == TAPosition[1]) {
                        blocksWorld[i][j] = 'a';
                        aPosition = TAPosition;
                        //System.out.println("a");
                    } else if (i == TBPosition[0] && j == TBPosition[1] && boardSizeN > 2) {
                        blocksWorld[i][j] = 'b';
                        bPosition = TBPosition;
                        bBlockInUse = true;
                        //System.out.println('b');
                    } else if (i == TCPosition[0] && j == TCPosition[1] && boardSizeN > 3) {
                        blocksWorld[i][j] = 'c';
                        cPosition = TCPosition;
                        //System.out.println('c');
                        cBlockInUse = true;

                    } else if (i == agentsPostion[0] && j == agentsPostion[1]) {
                        blocksWorld[i][j] = 's';
                        this.agentPosition = agentsPostion;
                        //System.out.println('s');

                    } else {
                        blocksWorld[i][j] = 'p';
                        //System.out.println('p');
                    }
                }
            }

//            System.out.println("This is tge initial state of the board");
//            for (int i = 0; i < blocksWorld.length; i++){
//                System.out.println(blocksWorld[i]);
//            }
        } else {
            System.out.println(" the board size has to be greater than one");
            System.exit(0);
        }
    }

    public void createNodeWithX(StateNode parent, int boardSizeN, int[][] xPositions, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion){
        this.parentNode = parent;
        children = new ArrayList<>();

        aPosition = new int[2];
        bPosition = new int[2];
        cPosition = new int[2];
        agentPosition = new int[2];
        blocksWorld = new char[boardSizeN][boardSizeN];

        this.boardSize = boardSizeN;
        this.xPositions = xPositions;
        this.xPositionUse = true;

        if (boardSizeN > 1) {
            for (int i = 0; i < boardSizeN; i++) { // this will be to loop through the rows
                for (int j = 0; j < boardSizeN; j++) { // this if for looping through the columns


                    boolean inX = false;

                    for (int l = 0; l < xPositions.length; l++ ){
                        if (i == xPositions[l][0] && j == xPositions[l][1]){
                            blocksWorld[i][j] = 'x';
                            inX = true;
                            break;
                        }

                    }


                    if (!inX) {
                        // we check if this tile should be one of the specified, otherwise it becomes a plane tile.
                        if (i == TAPosition[0] && j == TAPosition[1]) {
                            blocksWorld[i][j] = 'a';
                            aPosition = TAPosition;
                            //System.out.println("a");
                        } else if (i == TBPosition[0] && j == TBPosition[1] && boardSizeN > 2) {
                            blocksWorld[i][j] = 'b';
                            bPosition = TBPosition;
                            bBlockInUse = true;
                            //System.out.println('b');
                        } else if (i == TCPosition[0] && j == TCPosition[1] && boardSizeN > 3) {
                            blocksWorld[i][j] = 'c';
                            cPosition = TCPosition;
                            //System.out.println('c');
                            cBlockInUse = true;

                        } else if (i == agentsPostion[0] && j == agentsPostion[1]) {
                            blocksWorld[i][j] = 's';
                            this.agentPosition = agentsPostion;
                            //System.out.println('s');

                        } else {
                            blocksWorld[i][j] = 'p';
                            //System.out.println('p');
                        }
                    }
                }
            }

//            System.out.println("This is the final state of the board");
//            for (int i = 0; i < blocksWorld.length; i++){
//                System.out.println(blocksWorld[i]);
//            }
        } else {
            System.out.println(" the board size has to be greater than one");
            System.exit(0);
        }
    }




    public boolean moveAgent(char direction){



        boolean couldComplete = false;

        int[] moveAgentTo = {0,0};

        switch (direction){
            case 'u':
                // take the current position of the agent and add one
                moveAgentTo[0] = agentPosition[0] - 1;
                moveAgentTo[1] = agentPosition[1];
                //System.out.println('u');
                //System.out.println(moveAgentTo[0] +"," + moveAgentTo[1]);
                break;
            case 'd':
                moveAgentTo[0] = agentPosition[0] + 1;
                moveAgentTo[1] = agentPosition[1];
                //System.out.println('d');
                //System.out.println(moveAgentTo[0] +"," + moveAgentTo[1]);
                break;
            case 'l':
                moveAgentTo[0] = agentPosition[0];
                moveAgentTo[1] = agentPosition[1] - 1;
                //System.out.println('l');
                //System.out.println(moveAgentTo[0] +"," + moveAgentTo[1]);
                break;
            case 'r':
                moveAgentTo[0] = agentPosition[0];
                moveAgentTo[1] = agentPosition[1] +  1;
                //System.out.println('r');

               // System.out.println(moveAgentTo[0] +"," + moveAgentTo[1]);
                break;
        }
        //System.out.println(moveAgentTo[0] + ", " + moveAgentTo[1]);


        boolean thereIsAnX = false;

        if (this.xPositionUse) {
            for (int l = 0; l < xPositions.length; l++) {
                if (moveAgentTo[0] == xPositions[l][0] && moveAgentTo[1] == xPositions[l][1]) {
                    thereIsAnX = true;
                    couldComplete = false;
                    break;
                }

            }
        }


        if (!thereIsAnX) {

            if (moveAgentTo[0] < 0 || moveAgentTo[1] < 0 || moveAgentTo[0] > (boardSize - 1) || moveAgentTo[1] > (boardSize - 1)) {
                //System.out.println("in the if statment");
                // return false as you cannot do this mpve as it would move the agent of the board
                couldComplete = false;
            } else {
                // here do the change of agent position
                // move element in new agent position to old agent postion.
                if (moveAgentTo[0] == aPosition[0] && moveAgentTo[1] == aPosition[1]) {
                    // move the a tile into the current agent position
                    this.aPosition[0] = this.agentPosition[0];
                    this.aPosition[1] = this.agentPosition[1];

                } else if (moveAgentTo[0] == bPosition[0] && moveAgentTo[1] == bPosition[1] && bBlockInUse) {
                    this.bPosition[0] = this.agentPosition[0];
                    this.bPosition[1] = this.agentPosition[1];
                } else if (moveAgentTo[0] == cPosition[0] && moveAgentTo[1] == cPosition[1] && cBlockInUse) {
                    this.cPosition[0] = this.agentPosition[0];
                    this.cPosition[1] = this.agentPosition[1];
                }
                blocksWorld[agentPosition[0]][agentPosition[1]] = blocksWorld[moveAgentTo[0]][moveAgentTo[1]];
                // move agent position to new position
                blocksWorld[moveAgentTo[0]][moveAgentTo[1]] = 's';
                agentPosition[0] = moveAgentTo[0];
                agentPosition[1] = moveAgentTo[1];

                couldComplete = true;
            }
        }

        //System.out.println(couldComplete);
        // if the move was done return true, if it cannot be done return false
        return couldComplete;
    }


    public char[][] getBlocksWorld(){
        return this.blocksWorld;
    }

    public int[] getAgentPosition(){
        return this.agentPosition;
    }

    public int[] getaPosition(){
        return this.aPosition;
    }

    public int[] getbPosition() {
        return bPosition;
    }

    public int[] getcPosition() {
        return cPosition;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void addChild(StateNode node){
        children.add(node);
    }

    public boolean isbBlockInUse() {
        return bBlockInUse;
    }

    public boolean iscBlockInUse() {
        return cBlockInUse;
    }

    public int getNodeDepth() {
        return nodeDepth;
    }

    public StateNode getParentNode() {
        return parentNode;
    }

    public ArrayList<StateNode> getChildren() {
        return children;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
    }

    public void incrementCost(){
        this.currentCost = this.currentCost + 1;
    }

    public int cost(int cCost, int pCost){
        this.currentCost = cCost;
        this.projectedCost = pCost;

        return (cCost + pCost);
    }
    public void setNodeDepth(int nodeDepth) {
        this.nodeDepth = nodeDepth;
    }
    public int[][] getxPositions() {
        return xPositions;
    }
}
