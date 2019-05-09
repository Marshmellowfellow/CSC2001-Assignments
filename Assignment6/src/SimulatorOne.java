/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Map;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

// Used to signal violations of preconditions for
// various shortest path algorithms.

// Graph class: evaluate shortest paths.
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS**********************
// void addEdge( String v, String w, double cvw )
//                              --> Add additional edge
// void printPath( String w )   --> Print path after alg is run
// void unweighted( String s )  --> Single-source unweighted
// void dijkstra( String s )    --> Single-source weighted
// void negative( String s )    --> Single-source negative weighted
// void acyclic( String s )     --> Single-source acyclic
// ******************ERRORS*********************************
// Some error checking is performed to make sure graph is ok,
// and to make sure graph satisfies properties needed by each
// algorithm.  Exceptions are thrown if errors are detected.

public class SimulatorOne
{
    public static final double INFINITY = Double.MAX_VALUE;
    private Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

    /**
     * Add a new edge to the graph.
     */
    public void addEdge( String sourceName, String destName, double cost )
    {
        Vertex v = getVertex( sourceName );
        Vertex w = getVertex( destName );
        v.adj.add( new Edge( w, cost ) );
    }

    /**
     * Driver routine to handle unreachables and print total cost.
     * It calls recursive routine to print shortest path to
     * destNode after a shortest path algorithm has run.
     */
    public void printPath( String destName )
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY )
            System.out.println( destName + " is unreachable" );
        else
        {
            System.out.print( "(Cost is: " + w.dist + ") " );
            printPath( w );
            System.out.println( );
        }
    }

    /**
     * If vertexName is not present, add it to vertexMap.
     * In either case, return the Vertex.
     */
    private Vertex getVertex( String vertexName )
    {
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex( vertexName );
            vertexMap.put( vertexName, v );
        }
        return v;
    }

    /**
     * Recursive routine to print shortest path to dest
     * after running shortest path algorithm. The path
     * is known to exist.
     */
    private void printPath( Vertex dest )
    {
        if( dest.prev != null )
        {
            printPath( dest.prev );
            System.out.print( " to " );
        }
        System.out.print( dest.name );
    }
    
    /**
     * Initializes the vertex output info prior to running
     * any shortest path algorithm.
     */
    private void clearAll( )
    {
        for( Vertex v : vertexMap.values( ) )
            v.reset( );
    }

    /**
     * Single-source unweighted shortest-path algorithm.
     */
    public void unweighted( String startName )
    {
        clearAll( ); 

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        Queue<Vertex> q = new LinkedList<Vertex>( );
        q.add( start ); start.dist = 0;

        while( !q.isEmpty( ) )
        {
            Vertex v = q.remove( );

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                if( w.dist == INFINITY )
                {
                    w.dist = v.dist + 1;
                    w.prev = v;
                    q.add( w );
                }
            }
        }
    }

    /**
     * Single-source weighted shortest-path algorithm. (Dijkstra) 
     * using priority queues based on the binary heap
     */
    public void dijkstra( String startName )
    {
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {
            Path vrec = pq.remove( );
            Vertex v = vrec.dest;
            if( v.scratch != 0 )  // already processed v
                continue;
                
            v.scratch = 1;
            nodesSeen++;

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                    
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    pq.add( new Path( w, w.dist ) );
                }
            }
        }
    }

    /**
     * Single-source negative-weighted shortest-path algorithm.
     * Bellman-Ford Algorithm
     */
    public void negative( String startName )
    {
        clearAll( ); 

        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        Queue<Vertex> q = new LinkedList<Vertex>( );
        q.add( start ); start.dist = 0; start.scratch++;

        while( !q.isEmpty( ) )
        {
            Vertex v = q.remove( );
            if( v.scratch++ > 2 * vertexMap.size( ) )
                throw new GraphException( "Negative cycle detected" );

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist + cvw;
                    w.prev = v;
                      // Enqueue only if not already on the queue
                    if( w.scratch++ % 2 == 0 )
                        q.add( w );
                    else
                        w.scratch--;  // undo the enqueue increment    
                }
            }
        }
    }

    /**
     * Single-source negative-weighted acyclic-graph shortest-path algorithm.
     */
    public void acyclic( String startName )
    {
        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( ); 
        Queue<Vertex> q = new LinkedList<Vertex>( );
        start.dist = 0;
        
          // Compute the indegrees
		Collection<Vertex> vertexSet = vertexMap.values( );
        for( Vertex v : vertexSet )
            for( Edge e : v.adj )
                e.dest.scratch++;
            
          // Enqueue vertices of indegree zero
        for( Vertex v : vertexSet )
            if( v.scratch == 0 )
                q.add( v );
       
        int iterations;
        for( iterations = 0; !q.isEmpty( ); iterations++ )
        {
            Vertex v = q.remove( );

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( --w.scratch == 0 )
                    q.add( w );
                
                if( v.dist == INFINITY )
                    continue;    
                
                if( w.dist > v.dist + cvw )
                {
                    w.dist = v.dist + cvw;
                    w.prev = v;
                }
            }
        }
        
        if( iterations != vertexMap.size( ) )
            throw new GraphException( "Graph has a cycle!" );
    }

    public static void main( String [ ] args )
    {
        SimulatorOne g = new SimulatorOne( );
        try
        {   	
            //FileReader fin = new FileReader(args[0]);
        	FileReader fin = new FileReader("Graph1.txt");
            Scanner graphFile = new Scanner( fin );

            // Variables
            String line;
            int lineNo = 0;
            int NumNodes = 0;
            
            String source;
            String dest;
            int    cost;
            int[][] requests = new int[100][2];
            
            int NumDrivers = 0;
            int[] driverLocation = new int[100]; 
            int NumRequests = 0;
            
            while( graphFile.hasNextLine( ) )
            {
                line = graphFile.nextLine( );
                StringTokenizer st = new StringTokenizer( line , " ");
                int length1 = st.countTokens();
                int count1 = 0;
            	
                try{ 
                	//Number of nodes in graph
                	if(lineNo == 0) {
                		NumNodes = Integer.parseInt(st.nextToken()); 
                	}
                	
                	//Adding all the vertices for each node
                	if(lineNo>0 && lineNo<NumNodes) {
                		System.out.println("Vertices");
                		source  = st.nextToken( );
	                	while(count1 < length1) {
	                		dest = st.nextToken( );
	                		cost  = Integer.parseInt( st.nextToken( ) ); 
	                		System.out.println(source + " " + dest + "  " + cost);
	                		count1 = count1 + 3;
	                		g.addEdge( source, dest, cost );
	                	}
	                	System.out.println("");
                	}
                	
                	if(lineNo > NumNodes) {
                		
                		//Number of drivers
                		if(lineNo == NumNodes + 1) {
                			NumDrivers = Integer.parseInt( st.nextToken( )); 
                    		System.out.println("NumDrivers");
                    		System.out.println(NumDrivers);
                    		System.out.println("");
                		}
                		
                		//Driver address nodes
                		if(lineNo == NumNodes + 2) {
                   			int element = 0;
                   			int location = 0;
                			System.out.println("Driver locations");
    	                	while(count1 < length1) {
    	                		location = Integer.parseInt( st.nextToken( ) ); 
    	                		System.out.println(location);
    	                		count1 = count1 + 1;
    	                		driverLocation[element] = location;
    	                		element++;
    	                	}
    	                	System.out.println("");
                		}
                		
                		//Number of requests 
                		if(lineNo == NumNodes + 3) {
                			NumRequests = Integer.parseInt( st.nextToken( )); 
                    		System.out.println("NumRequests");
                    		System.out.println(NumRequests);
                    		System.out.println("");                			
                		}
                		
                		//Request pick up and drop off pairs
                		if(lineNo == NumNodes + 4) {
                			int pickUp;
                			int dropOff;
                			int element = 0;
                			System.out.println("pickUp " + " dropOff");
    	                	while(count1 < length1) {
    	                		pickUp = Integer.parseInt( st.nextToken( ) ); 
    	                		dropOff  = Integer.parseInt( st.nextToken( ) ); 
    	                		System.out.println(pickUp + " " + dropOff);
    	                		count1 = count1 + 2;
    	                		requests[element][0] = pickUp;
    	                		requests[element][1] = dropOff;
    	                		element++;
    	                	}
    	                	System.out.println("");
    	                	
    	                	System.out.println("Requests");
    	                	int count2 = 0;
    	                	while(count2 < element) {
    	                		System.out.print(requests[count2][0]);
    	                		System.out.println(requests[count2][1]);
    	                		count2++;
    	                	}
    	                	
    	                	
                		}
                		
                	}
                	
                    //Sets the variables based on the line and calls method to add edge.
//                    String source  = st.nextToken( );
//                    String dest    = st.nextToken( );
//                    int    cost    = Integer.parseInt( st.nextToken( ) );                 
//                    g.addEdge( source, dest, cost );
                	
                	lineNo ++;

                }
                catch( NumberFormatException e )
                  { System.err.println( "Skipping ill-formatted line " + line ); }
             }
         }
        catch( IOException e )
        { System.err.println( e ); }

	  System.out.println("");
      System.out.println( "File read..." );
      System.out.println( g.vertexMap.size( ) + " vertices" );

      Scanner in = new Scanner( System.in );
 }
}