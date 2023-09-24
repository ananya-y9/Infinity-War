package avengers;
import java.util.ArrayList;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }

        String UTSInputFile = args[0];
        String UTSOutputFile = args[1];
        StdIn.setFile(UTSInputFile);
   
        int EUT = StdIn.readInt(); //EU threshold
        int n = StdIn.readInt(); //number of evemts

        int [] EU = new int [n];
        int [] EUC = new int[n];

        for (int i = 0; i < n; i++) //make adjacency list
        {
            int event = StdIn.readInt();
            int EUV = StdIn.readInt();

            EU[event] = EUV; 
            EUC[event] = EUV;
        }


        int [] [] adjm = new int [n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++) //adjacency matrix 
            {
            int val = StdIn.readInt();
            adjm[i][j] = val;
            }
        }

        
        int [] pcount = new int[2];
        
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(0);
        dfs(0, path, adjm, EU, EUC, EUT, pcount);
        
        StdOut.setFile(UTSOutputFile);

        StdOut.println(pcount[0]);
        StdOut.println(pcount[1]);

        
    }
    public static void dfs(int s, ArrayList <Integer> path, int [][] adjm, int [] EU, int [] EUC, int EUT, int [] pcount)
    {
        pcount[0]++; //counts how many times pathways are created
        
        if (EU[s] >= EUT)
        {
            pcount[1]++; //keeping track of the other
        }

        for (int j = 0; j < adjm.length; j++)
        {
            if (adjm[s][j]!=0) //adjacent
            {
                path.add(j);
                EU[j] = EU[s] + EU[j];
                dfs(j, path, adjm, EU, EUC, EUT, pcount); //visit this node
            }
        }

        path.remove(path.size()-1); //remove most recently added item
        EU[s] = EUC[s]; //reassign value that you changed
        return;
    }
}
