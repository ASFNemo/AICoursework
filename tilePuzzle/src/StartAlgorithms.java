import java.util.ArrayList;

/**
 * Created by asherfischbaum on 23/11/2016.
 */
public class StartAlgorithms {
    public static void main(String[] args) {
        StartAlgorithms SA = new StartAlgorithms();
        SA.testDFS();
        //SA.testAStar();

    }

    public void testDFS() {

        int storedNodes = 0;
        int nodesExpanded = 0;
        int routeLength = 0;

        for (int i = 0; i < 15; i++) {
            try {
                SearchAlgorithms DFS = new SearchAlgorithms(5);
                StateNode finalNode = DFS.startDFS();



                int maxTree = finalNode.getNoElementsInTree();

                ArrayList<Character> moves = new ArrayList<>();

                moves.add(0, finalNode.getDirection());

                StateNode parentNode = finalNode.getParentNode();

                while (parentNode.getParentNode() != null) {

                    moves.add(0, parentNode.getDirection());

                    parentNode = parentNode.getParentNode();

                }

                storedNodes = storedNodes + finalNode.getNoElementsInTree();
                nodesExpanded = nodesExpanded + finalNode.getTotalNodesExpanded();
                routeLength = routeLength + moves.size();

                System.out.println("DFS expanded: " + finalNode.getTotalNodesExpanded() + " nodes");
                System.out.println("IDDFS number of nodes stored in the tree at most: " + maxTree + " nodes");
                System.out.println("the solution was reached in: " + moves.size() + " steps");
                System.out.println("the moves taken were" + moves);


            } catch (OutOfMemoryError e) {
                System.out.println("ran out of memory");
            }
        }
        System.out.println("the tree stored at most: " + (storedNodes/15) + " nodes");
        System.out.println("total nodes expanded: " + (nodesExpanded/15));
        System.out.println("route length: " + (routeLength/15));
    }





    public void testIDDFS(){
        try {
            SearchAlgorithms IDDFS = new SearchAlgorithms(6);
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
            SearchAlgorithms AS = new SearchAlgorithms(5);
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

            System.out.println("BFS expanded: " + spaceUsed + " nodes");
            System.out.println("BFS number of nodes stored in the tree at most: " + nodesExpanded + " nodes");
            System.out.println("the solution was reached in: " + moves.size() + " steps");
            System.out.println("the moves taken were" + moves);
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }
    }


}
