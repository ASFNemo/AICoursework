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
        test.BFS();
        //test.randomDirection();


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
        int[] aPosition = state.getaPosition();
        int[] bPosition = state.getbPosition();
        int[] cPosition = state.getcPosition();

        System.out.println("====");
        System.out.println(agentBoard[boardSize - 2]);
        System.out.println(agentBoard[boardSize - 1]);

        System.out.println("---------");
        StateNode state2 = new StateNode(agentBoard, agentPosition, aPosition, bPosition, cPosition);

        state.moveAgent('u');
        agentBoard = state2.getBlocksWorld();

        System.out.println("[[[[[");
        for (int i = 0; i < agentBoard.length; i++){
            System.out.println(agentBoard[i]);
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

        BFS search = new BFS(4);


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


}
