package avengers;
import java.util.ArrayList;
/**
 * Given an adjacency matrix, use a random() function to remove half of the nodes. 
 * Then, write into the output file a boolean (true or false) indicating if 
 * the graph is still connected.
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * PredictThanosSnapInputFile name is passed through the command line as args[0]
 * Read from PredictThanosSnapInputFile with the format:
 *    1. seed (long): a seed for the random number generator
 *    2. p (int): number of people (vertices in the graph)
 *    2. p lines, each with p edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note: the last p lines of the PredictThanosSnapInputFile is an ajacency matrix for
 * an undirected graph. 
 * 
 * The matrix below has two edges 0-1, 0-2 (each edge appear twice in the matrix, 0-1, 1-0, 0-2, 2-0).
 * 
 * 0 1 1 0
 * 1 0 0 0
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * Delete random vertices from the graph. You can use the following pseudocode.
 * StdRandom.setSeed(seed);
 * for (all vertices, go from vertex 0 to the final vertex) { 
 *     if (StdRandom.uniform() <= 0.5) { 
 *          delete vertex;
 *     }
 * }
 * Answer the following question: is the graph (after deleting random vertices) connected?
 * Output true (connected graph), false (unconnected graph) to the output file.
 * 
 * Note 1: a connected graph is a graph where there is a path between EVERY vertex on the graph.
 * 
 * Note 2: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, isConnected is true if the graph is connected,
 *   false otherwise):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(isConnected);
 * 
 * @author Yashas Ravi
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/PredictThanosSnap predictthanossnap.in predictthanossnap.out
 * 
 * java -cp bin avengers/PredictThanosSnap predictthanossnap3.in predictthanossnap3.out
 * 
*/

public class PredictThanosSnap 
{
    public static void main (String[] args) 
    {
 
        if ( args.length < 2 ) 
        {
            StdOut.println("Execute: java PredictThanosSnap <INput file> <OUTput file>");
            return;
        }

        String predictThanosSnapInputFile = args[0];
        String predictThanosSnapOutputFile = args[1];
        boolean isConnected = true;

	    // Set the input file.
        StdIn.setFile(predictThanosSnapInputFile);

        //this is an UNDIRECTED graph
        long seed = StdIn.readLong();
        int v = StdIn.readInt(); //number of people/vertices

        int [] [] adjm = new int [v][v]; //v^2 space

        for (int i = 0; i < v; i++)
        {
            for (int j = 0; j < v; j++)
            {
                adjm[i][j] = StdIn.readInt();
            }
        }

        ArrayList <Integer> vLeft = new ArrayList<Integer>(); //to keep track of the vertices left
        
        StdRandom.setSeed(seed);
        for (int i = 0; i < v; i++)
        {
            if (StdRandom.uniform() <= 0.5) 
            { 
            //delete vertex, so you delete that row?
            //set all 1's to 0 because then it's not connected to anything
                for (int j = 0; j < v; j++)
                {
                    adjm[i][j] = 0;
                    adjm[j][i] = 0; 
                }
            }
            else //add the index of the vertex that was not deleted to 
            {
                vLeft.add(i);
            }
        }

        //dfs on every vertex that does not have all zero rows!!!
        for (int i = 0; i < vLeft.size(); i++)
        {
            boolean marked [] = new boolean [v];
            dfs(vLeft.get(i), marked, adjm); //only runs for vertex that are left
            //marks up the array to see which verticies are connected to the ones that are left
            for (int m = 0; m < vLeft.size(); m++)
            {
                if (marked[vLeft.get(m)] == false) //if any of the vertices is not marked, it means it's not connected
                {
                    isConnected = false;
                }
            }
        }

        StdOut.setFile(predictThanosSnapOutputFile);
        StdOut.println(isConnected);
    	// WRITE YOUR CODE HERE

    }

    public static void dfs(int v, boolean [] marked, int [][] adjm)
    {
        marked[v] = true;
        for (int j = 0; j < adjm.length; j++)
        {
            if (adjm[v][j]!=0) //adjacent
            {
                if (marked[j] == false)//has not been visited yet
                {
                    dfs(j, marked, adjm); //visit this node
                }
            }
        }
    }
}