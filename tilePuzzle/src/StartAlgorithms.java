/**
 * Created by asherfischbaum on 23/11/2016.
 */
public class StartAlgorithms {
    public static void main(String[] args) {
        StartAlgorithms SA = new StartAlgorithms();
        SA.start();

    }

    public void start(){

        // TODO: ADD A PARAMETER WHERER THEY CAN PASS IN AN ARRAY OF COORDINATES WHERE TO PUT X TILES

//        try {
//            SearchAlgorithms BFS = new SearchAlgorithms(4);
//            BFS.startBFS();
//        } catch (OutOfMemoryError e){
//            System.out.println("ran out of memory");
//        }

        try {
            SearchAlgorithms DFS = new SearchAlgorithms(4);
            DFS.startDFS();
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }

        try {
            SearchAlgorithms IDDFS = new SearchAlgorithms(4);
            IDDFS.startIDDFS();
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }

        try {
            SearchAlgorithms AS = new SearchAlgorithms(4);
            AS.startAStar();
        } catch (OutOfMemoryError e){
            System.out.println("ran out of memory");
        }

        //TODO: AS AN EXTENSION: CREATE AN ALGORITHM THAT FINDS THE MOST EFFICIENT PLACE TO ADD THE X TILES






    }

}
