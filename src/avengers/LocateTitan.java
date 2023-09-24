package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * java -cp bin avengers/LocateTitan locatetitan3.in locatetitan3.out
 * 
 * 
 * @author Yashas Ravi
 * 
 */

public class LocateTitan 
{
    public static void main (String [] args) 
    {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }
        String locateTitanInputFile = args[0];
        String locateTitanOutputFile = args[1];

	    // Set the input file.
        StdIn.setFile(locateTitanInputFile);

        int n = StdIn.readInt(); //n = number of vertices 

        double [] fun = new double[n]; 
        for (int i = 0; i < n; i++) //made an array to hold the functionality of each generator
        {
            int g = StdIn.readInt();
            double f = StdIn.readDouble();
            fun[g] = f; 
        }

        int [][] adjm = new int[n][n];
        for (int k = 0; k < n; k++)
        {
            for (int j = 0; j < n; j++)
            {
            adjm[k][j] = StdIn.readInt();
            }
        }

        //Step 2
        //for every edge (every entry on the adjacency matrix), 
        //recalculate the length based on functionality at that index
        for (int i = 0; i < n; i++)
        {
            double fun1 = fun[i]; //from first generator and for every column in this row

            for(int k = 0; k < n; k++) //every column, or every other generator
            {
                double fun2 = fun[k];
                adjm[i][k] = (int)(adjm[i][k]/(fun1*fun2));
            }
        }

        //Step 3
        int [] minCost = new int[n];
        boolean [] pA = new boolean[n];

        for (int i = 0; i < n; i++)
        {
            if (i ==0) //vertex0
            {
                minCost[i] = 0;
            }
            else
            {
                minCost[i] = Integer.MAX_VALUE;
            }
        }

        for(int i = 0; i < n-1; i++) //n-1 iterations, from source 0!!!
        {
            int currentSource = 0; 
            int min = Integer.MAX_VALUE; 

            for (int v = 0; v < n; v++)
            {
                if (minCost[v] < min & pA[v]!= true)
                {
                    currentSource = v; 
                    min = minCost[v];
                }
            }

            pA[currentSource] = true;

            for (int k = 0; k < n; k++)
            {
                if (adjm[currentSource][k] != 0)
                {
                    if ((pA[k] != true) && (minCost[currentSource] != Integer.MAX_VALUE) && (minCost[k] > (minCost[currentSource] + adjm[currentSource][k]))) 
                    {
                        minCost[k] = minCost[currentSource] + adjm[currentSource][k];
                    }
                }
            }
        }


        StdOut.setFile(locateTitanOutputFile);
        StdOut.println(minCost[n-1]);
    }

    	// WRITE YOUR CODE HERE
}
