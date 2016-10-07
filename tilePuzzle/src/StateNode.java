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

    int[] aPosition;
    int[] bPosition;
    int[] cPosition;
    int[] agentPosition;

    int boardSize;

    char[][] blocksWorld;

    public StateNode(int boardSizeN, int[] TAPosition, int[] TBPosition, int[] TCPosition, int[] agentPostion){

        blocksWorld = new char[boardSizeN][boardSizeN];

        this.boardSize = boardSizeN;

        if (boardSizeN > 1) {
            for (int i = 0; i < boardSizeN; i++) { // this will be to loop through the rows
                for (int j = 0; j < boardSizeN; j++) { // this if for looping through the columns

                    // we check if this tile should be one of the specified, otherwise it becomes a plane tile.
                    if (i == TAPosition[0] && j == TAPosition[1]) {
                        blocksWorld[i][j] = 'a';
                        aPosition = TAPosition;
                        System.out.println("a");
                    } else if (i == TBPosition[0] && j == TBPosition[1] && boardSizeN > 2) {
                        blocksWorld[i][j] = 'b';
                        bPosition = TBPosition;
                        System.out.println('b');
                    } else if (i == TCPosition[0] && j == TCPosition[1] && boardSizeN > 3) {
                        blocksWorld[i][j] = 'c';
                        cPosition = TCPosition;
                        System.out.println('c');

                    } else if (i == agentPostion[0] && j == agentPostion[1]) {
                        blocksWorld[i][j] = 's';
                        this.agentPosition = agentPostion;
                        System.out.println('s');

                    } else {
                        blocksWorld[i][j] = 'p';
                        System.out.println('p');
                    }
                }
            }

            System.out.println(blocksWorld[boardSizeN - 2]);
            System.out.println(blocksWorld[boardSizeN - 1]);
        } else {
            System.out.println(" the board size has to be greater than one");
            System.exit(0);
        }
    }

    // add another constructor that only takes the matrix and the current agent position.


    public StateNode(char[][] blocksWorld, int[] agentPosition) {
        this.blocksWorld = blocksWorld;
        this.agentPosition = agentPosition;
    }

    public boolean moveAgent(char direction){

        int[] moveAgentTo = {0,0};

        switch (direction){
            case 'u':
                // take the current position of the agent and add one
                moveAgentTo[0] = agentPosition[0] - 1;
                moveAgentTo[1] = agentPosition[1];
                System.out.println('u');
                break;
            case 'd':
                moveAgentTo[0] = agentPosition[0] + 1;
                moveAgentTo[1] = agentPosition[1];
                System.out.println('d');
                break;
            case 'l':
                moveAgentTo[0] = agentPosition[0];
                moveAgentTo[1] = agentPosition[1] - 1;
                System.out.println('l');
                break;
            case 'r':
                moveAgentTo[0] = agentPosition[0];
                moveAgentTo[1] = agentPosition[1] +  1;
                System.out.println('r');
                break;
        }
        System.out.println(moveAgentTo[0] + ", " + moveAgentTo[1]);

        if (moveAgentTo[0] < 0 || moveAgentTo[1] < 0 || moveAgentTo[0] >= boardSize || moveAgentTo[1] >= boardSize) {
            System.out.println("in the if statment");
            // return false as you cannot do this mpve as it would move the agent of the board
            return false;
        } else {
            // here do the change of agent position
            // move element in new agent position to old agent postion.
            if (moveAgentTo == aPosition){
                // move the a tile into the current agent position
            }
            blocksWorld[agentPosition[0]][agentPosition[1]] = blocksWorld[moveAgentTo[0]][moveAgentTo[1]];
            // move agent position to new position
            blocksWorld[moveAgentTo[0]][moveAgentTo[1]] = 's';
            agentPosition[0] = moveAgentTo[0];
            agentPosition[1] = moveAgentTo[1];
        }
        // if the move was done return true, if it cannot be done return false
        return true;
    }


    public char[][] getBlocksWorld(){
        return this.blocksWorld;
    }

    public int[] getAgentPosition(){
        return this.agentPosition;
    }

}
