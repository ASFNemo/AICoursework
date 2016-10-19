import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asherfischbaum on 07/10/2016.
 */
public class someTests {

    public static void main(String[] args) {
        someTests test = new someTests();

        //test.stateNode();
        //test.arraylist();
         test.BFS(); // sort this one out, I am close but need to work out why it is returning false.
        //test.randomDirection();
        //test.DFS();
        //test.testMoveDown();
        //test.testMoveRight();
        //test.IfFinalRecognized();

    }

    public void stateNode(){
        StateNode state = createStartNode(3);

        System.out.println("going left");
        state.moveAgent('l');
        char[][] agentBoard = state.getBlocksWorld();
        int[] agentPosition = state.getAgentPosition();
        int[] aPosition = state.getaPosition();
        int[] bPosition = state.getbPosition();
        int[] cPosition = state.getcPosition();

        char[][] blocksWorld = state.getBlocksWorld();

        System.out.println("a position: " + state.aPosition[0] +"," + state.aPosition[1]);
        System.out.println("b position: " + state.bPosition[0] +"," + state.bPosition[1]);
        System.out.println("c position: " + state.cPosition[0] +"," + state.cPosition[1]);
        System.out.println("agent position: " + state.agentPosition[0] +"," + state.agentPosition[1]);
        System.out.println();
        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }

        System.out.println("---------");
       // StateNode state2 = new StateNode(null, agentBoard, agentPosition, aPosition, bPosition, cPosition);

        state.moveAgent('l');
        //agentBoard = state2.getBlocksWorld();

        System.out.println("a position: " + state.aPosition[0] +"," + state.aPosition[1]);
        System.out.println("b position: " + state.bPosition[0] +"," + state.bPosition[1]);
        System.out.println("c position: " + state.cPosition[0] +"," + state.cPosition[1]);
        System.out.println("agent position: " + state.agentPosition[0] +"," + state.agentPosition[1]);
        System.out.println();
        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }

    }

    public void arraylist(){
        ArrayList a = new ArrayList();

        for (int i = 0; i < 10; i++){
            a.add(i);
        }
        System.out.println(a);
        a.get(0);
        System.out.println(a);
        a.remove(0);
        System.out.println(a);
    }

    public void BFS(){

        BFS search = new BFS(3);


    }

    public void randomDirection(){
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
            System.out.println(directionArr[i]);
            max--;
        }

    }

    public void testMoveDown(){

        int boardSize = 3;
        int[] a = {(boardSize -1), 0};
        int[] b = {(boardSize -1), (boardSize - 1)};
        int[] c = {(boardSize -1), 2};
        int[] s = {(boardSize -1), 1};

        StateNode state = new StateNode(null, boardSize, a, b, c, s);

        char[][] blocksWorld = state.getBlocksWorld();

        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }

        boolean didMove = state.moveAgent('d');

        System.out.println(didMove);

        blocksWorld = state.getBlocksWorld();

        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }



    }

    public void testMoveRight(){
        int boardSize = 3;
        int[] a = {(boardSize -2), 0};
        int[] b = {(boardSize -1), 1};
        int[] c = {(boardSize -1), 2};
        int[] s = {(boardSize -1), 0};

        StateNode state = new StateNode(null, boardSize, a, b, c, s);

        char[][] blocksWorld = state.getBlocksWorld();

        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }

        boolean didMove = state.moveAgent('r');

        System.out.println(didMove);

        blocksWorld = state.getBlocksWorld();

        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }
    }

    public void DFS(){
        DFS search = new DFS(3);
    }

    public void IfFinalRecognized(){

        StateNode state = createStartNode(3);

        int[] finalAPosition = {1,0};
        int[] finalBPosition = {2, 0};
        int[] finalCPosition = {100, 100};


        state.moveAgent('u');
        state.moveAgent('l');
        state.moveAgent('l');
        state.moveAgent('d');
        state.moveAgent('r');

        char[][] blocksWorld = state.getBlocksWorld();

        System.out.println("a position: " + state.aPosition[0] +"," + state.aPosition[1]);
        System.out.println("b position: " + state.bPosition[0] +"," + state.bPosition[1]);
        System.out.println("c position: " + state.cPosition[0] +"," + state.cPosition[1]);
        System.out.println("agent position: " + state.agentPosition[0] +"," + state.agentPosition[1]);

        for (int j = 0; j < blocksWorld.length; j++) {
            System.out.println(blocksWorld[j]);
        }

        if ((state.getaPosition()[0] == finalAPosition[0] && state.getaPosition()[1] == finalAPosition[1]) &&
                ((state.getbPosition()[0] == finalBPosition[0] && state.getbPosition()[1] == finalBPosition[1]) || !state.isbBlockInUse()) &&
                (state.getcPosition() == finalCPosition || !state.iscBlockInUse())){
            // if yes - print the final Node, how many nodes were searched and finish the system
            System.out.println("Depth first search has been able to complete!");

        }



    }

    public StateNode createStartNode(int size ){
        int boardSize = size;
        int[] a = {(boardSize -1), 0};
        int[] b = {(boardSize -1), 1};
        int[] c = {(boardSize -1), 2};
        int[] s = {(boardSize -1), (boardSize - 1)};

        StateNode state = new StateNode(null, boardSize, a, b, c, s);

        return state;
    }


}
