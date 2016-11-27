import java.util.ArrayList;

/**
 * Created by asherfischbaum on 23/11/2016.
 */
public class StartAlgorithms {
    public static void main(String[] args) {
        StartAlgorithms SA = new StartAlgorithms();
        SA.testBFS();

    }

    public void testDFS(){
        try {
            SearchAlgorithms DFS = new SearchAlgorithms(4);
            StateNode finalNode = DFS.startDFS();

            int nodesExpanded = finalNode.getNodeDepth();

            ArrayList<Character> moves = new ArrayList<>();
            moves.add(0, finalNode.getDirection());
            StateNode parentNode = finalNode.getParentNode();

            while (parentNode.getParentNode() != null) {

                moves.add(0, parentNode.getDirection());

                parentNode = parentNode.getParentNode();

            }

            System.out.println("DFS expanded: " + nodesExpanded + " nodes");
            System.out.println("the solution was reached in: " + moves.size() + " steps");
            System.out.println("the moves taken were" + moves);

        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }
    }





    public void testIDDFS(){
        try {
            SearchAlgorithms IDDFS = new SearchAlgorithms(4);
            StateNode finalNode = IDDFS.IDDFS();

            int nodesExpanded = finalNode.getTotalNodesExpanded();
            int spaceUsed = finalNode.getNoElementsInTree();

            ArrayList<Character> moves = new ArrayList<>();
            moves.add(0, finalNode.getDirection());
            StateNode parentNode = finalNode.getParentNode();

            while (parentNode.getParentNode() != null) {

                moves.add(0, parentNode.getDirection());

                parentNode = parentNode.getParentNode();

            }

            System.out.println("IDDFS expanded: " + nodesExpanded + " nodes");
            System.out.println("IDDFS number of nodes stored in the tree at most: " + spaceUsed + " nodes");
            System.out.println("the solution was reached in: " + moves.size() + " steps");
            System.out.println("the moves taken were" + moves);
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }
    }


    public void testAStar(){
        try {
            SearchAlgorithms AS = new SearchAlgorithms(4);
            StateNode finalNode = AS.StartAStar();


            int nodesExpanded = finalNode.getTotalNodesExpanded();
            int spaceUsed = finalNode.getNoElementsInTree();

            ArrayList<Character> moves = new ArrayList<>();

            moves.add(0, finalNode.getDirection());
            StateNode parentNode = finalNode.getParentNode();

            while (parentNode.getParentNode() != null) {

                moves.add(0, parentNode.getDirection());

                parentNode = parentNode.getParentNode();

            }

            System.out.println("AStar expanded: " + nodesExpanded + " nodes");
            System.out.println("AStar number of nodes stored in the tree at most: " + spaceUsed + " nodes");
            System.out.println("the solution was reached in: " + moves.size() + " steps");
            System.out.println("the moves taken were" + moves);


        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }
    }


    public void testBFS(){
        try {
            SearchAlgorithms BFS = new SearchAlgorithms(3);
            StateNode finalNode = BFS.startBFS();


            int nodesExpanded = finalNode.getTotalNodesExpanded();
            int spaceUsed = finalNode.getNoElementsInTree();

            ArrayList<Character> moves = new ArrayList<>();

            moves.add(0, finalNode.getDirection());
            StateNode parentNode = finalNode.getParentNode();

            while (parentNode.getParentNode() != null) {

                moves.add(0, parentNode.getDirection());

                parentNode = parentNode.getParentNode();

            }

            System.out.println("BFS expanded: " + nodesExpanded + " nodes");
            System.out.println("BFS number of nodes stored in the tree at most: " + spaceUsed + " nodes");
            System.out.println("the solution was reached in: " + moves.size() + " steps");
            System.out.println("the moves taken were" + moves);
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }
    }

    public void start(){











        //TODO: AS AN EXTENSION: CREATE AN ALGORITHM THAT FINDS THE MOST EFFICIENT PLACE TO ADD THE X TILES






    }

}
