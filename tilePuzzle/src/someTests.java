/**
 * Created by asherfischbaum on 07/10/2016.
 */
public class someTests {

    public static void main(String[] args) {
        someTests test = new someTests();
        test.stateNode();
    }

    public void stateNode(){
        int boardSize = 3;
        int[] a = {(boardSize -1), 0};
        int[] b = {(boardSize -1), 1};
        int[] c = {(boardSize -1), 2};
        int[] s = {(boardSize -1), (boardSize - 1)};

        StateNode state = new StateNode(boardSize, a, b, c, s);

        System.out.println("going up");
        state.moveAgent('l');
        char[][] agentBoard = state.getBlocksWorld();
        int[] agentPosition = state.getAgentPosition();

        System.out.println("====");
        System.out.println(agentBoard[boardSize - 2]);
        System.out.println(agentBoard[boardSize - 1]);

        System.out.println("---------");
        StateNode state2 = new StateNode(agentBoard, agentPosition);

        state.moveAgent('u');
        agentBoard = state2.getBlocksWorld();

        System.out.println(agentBoard[boardSize - 2]);
        System.out.println(agentBoard[boardSize - 1]);

    }
}
