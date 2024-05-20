import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //Initializes the reader
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        //Read input
        String[] input = in.readLine().split(" ");

        int thieves = Integer.parseInt(input[0]);
        int goldBars = Integer.parseInt(input[1]);
        int locations = Integer.parseInt(input[2]);
        int roads = Integer.parseInt(input[3]);

        Graph graph = new Graph(2 * locations + 2);
        for(int i = 0; i < roads; i++){
            String[] route = in.readLine().split(" ");
            int l1 = Integer.parseInt(route[0]) ;
            int l2 = Integer.parseInt(route[1]);
            graph.addEdge(l1 * 2 + 1, l2 * 2, 1);
            graph.addEdge(l2 * 2 + 1, l1 * 2, 1);
        }

        for (int i = 1 ; i <= locations; i++) {
            graph.addEdge(i * 2, i * 2 + 1, 1);
        }

        String[] vaultDestination = in.readLine().split(" ");
        int vaultLoc = Integer.parseInt(vaultDestination[0]);
        int destinationLoc = Integer.parseInt(vaultDestination[1]);

        int numPaths = graph.DinicMaxflow(vaultLoc * 2 + 1, destinationLoc * 2);

        System.out.println(Math.min(thieves,numPaths)*goldBars);
    }
}