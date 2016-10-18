import java.util.ArrayList;

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

    int boardSize;

    boolean bBlockInUse = false;
    boolean cBlockInUse = false;

    char[][] blocksWorld;

    public StateNode(StateNode parent, int boardSizeN, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentsPostion){

        /**
            Use this constructor when you are creating the initial and final state
         */


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


    public boolean moveAgent(char direction){

        boolean couldComplete;

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

        if (moveAgentTo[0] < 0 || moveAgentTo[1] < 0 || moveAgentTo[0] > (boardSize -1) || moveAgentTo[1] > (boardSize -1)) {
            //System.out.println("in the if statment");
            // return false as you cannot do this mpve as it would move the agent of the board
            couldComplete = false;
        } else {
            // here do the change of agent position
            // move element in new agent position to old agent postion.
            if (moveAgentTo[0] == aPosition[0] && moveAgentTo[1] == aPosition[1]){
                // move the a tile into the current agent position
                this.aPosition[0] = this.agentPosition[0];
                this.aPosition[1] = this.agentPosition[1];

            } else if (moveAgentTo[0] == bPosition[0] && moveAgentTo[1] == bPosition[1] && bBlockInUse){
                this.bPosition[0] = this.agentPosition[0];
                this.bPosition[1] = this.agentPosition[1];
            } else if (moveAgentTo[0] == cPosition[0] && moveAgentTo[1] == cPosition[1] && cBlockInUse){
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
}
