

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
import java.io.*; 
import java.util.*; 	
import java.util.StringTokenizer;

// Used to signal violations of preconditions for
// various shortest path algorithms.
class GraphException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphException( String name )
    {
        super( name );
    }
}

// Represents an edge in the graph.
class Edge
{
    public Vertex     dest;   // Second vertex in Edge
    public double     cost;   // Edge cost
    
    public Edge( Vertex d, double c )
    {
        dest = d;
        cost = c;
    }
}

// Represents an entry in the priority queue for Dijkstra's algorithm.
class Path implements Comparable<Path>
{
    public Vertex     dest;   // w
    public double     cost;   // d(w)
    
    public Path( Vertex d, double c )
    {
        dest = d;
        cost = c;
    }
    
    public int compareTo( Path rhs )
    {
        double otherCost = rhs.cost;
        return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
    }
}

//Class representing path 
class totalPath
{
	public ArrayList<String> driverPath;
	public ArrayList<String> nodeNum;
	public ArrayList<String> nodeVal;
	public int thisCost; 
	public totalPath() {
        nodeNum = new ArrayList<String>();
        nodeVal = new ArrayList<String>();
		driverPath = new ArrayList<String>(); 
		thisCost = 0;
	}
}

// Represents a vertex in the graph.
class Vertex
{
    public String     name;   // Vertex name
    public List<Edge> adj;    // Adjacent vertices
    public double     dist;   // Cost
    public Vertex     prev;   // Previous vertex on shortest path
    public int        scratch;// Extra variable used	 in algorithm

    public Vertex( String nm )
      { name = nm; adj = new LinkedList<Edge>( ); reset( ); }

    public void reset( )
    //  { dist = Graph.INFINITY; prev = null; pos = null; scratch = 0; }    
    { dist = Graph.INFINITY; prev = null; scratch = 0; }
      
   // public PairingHeap.Position<Path> pos;  // Used for dijkstra2 (Chapter 23)
}
//
// CONSTRUCTION: with no parameters.
//
// ******************PUBLIC OPERATIONS*****	*****************
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
    public int printPath(totalPath path, String destName)
    {
        Vertex w = vertexMap.get( destName );
        if( w == null )
            throw new NoSuchElementException( "Destination vertex not found" );
        else if( w.dist == INFINITY ) {
        	return -1;
        }
        else
        {
            //System.out.print( "(Cost is: " + w.dist + ") " );
        	//System.out.println((printPath( path, w )).driverPath);
        	printPath( path, w );
        }

        path.thisCost = (int) w.dist;
        return (int) w.dist;
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
    private totalPath printPath(totalPath path, Vertex dest )
    {
    	String string ="";
        if( dest.prev != null )
        {
            printPath(path,  dest.prev );
         //System.out.print( path.driverPath);
        }
        (path.driverPath).add(dest.name);	
        return path;
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
     * Single-source weighted shortest-path algorithm. (Dijkstra) 
     * using priority queues based on the binary heap
     */
    public totalPath dijkstra( String startName, totalPath thePath)
    {
        PriorityQueue<Path> pq = new PriorityQueue<Path>( );
        ArrayList<String> nodeNum = thePath.nodeNum;
        ArrayList<String> nodeVal = thePath.nodeVal;
        Vertex start = vertexMap.get( startName );
        if( start == null )
            throw new NoSuchElementException( "Start vertex not found" );

        clearAll( );
        pq.add( new Path( start, 0 ) ); 
        start.dist = 0;
        
        int nodesSeen = 0;
        while( !pq.isEmpty( ) && nodesSeen < vertexMap.size( ) )
        {
            Path vrec = pq.remove( );
            Vertex v = vrec.dest;
            // already processed v
            if( v.scratch != 0 ) {
            	continue;
            	
            }
            v.scratch = 1;
            nodesSeen++;

            for( Edge e : v.adj )
            {
                Vertex w = e.dest;
                double cvw = e.cost;
                
                if( cvw < 0 )
                    throw new GraphException( "Graph has negative edges" );
                    
                int total = (int) v.dist +(int) cvw;
                if( w.dist >= v.dist + cvw )
                {
                	
                	//System.out.println(v.name + "    " + w.name  + "    " +  total  + "   " + w.dist );
                	nodeNum.add(w.name);
                	nodeVal.add(Integer.toString(total));
               
                    w.dist = v.dist +cvw;
                    w.prev = v;
                    if( w.dist == v.dist + cvw ) {
                		//System.out.println(v.name + "    " + w.name  + "    " +  total  + "   " + w.dist );
                	}
                    pq.add( new Path( w, w.dist ) );
                }
//                
                
//                if(w.dist == (v.dist + cvw)) {
//                	System.out.println("it is done!!!");
//                }
                
                
            }
        }
        return thePath;
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
            int element = 0;
            
            int totalCost = 0;
            int costPickup = 0;
            int costDropoff = 0;
            int costReturn = 0;
                        
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
                		//System.out.println("");
                		//System.out.println("Number of nodes");	
                		//System.out.println(NumNodes);
                		//System.out.println("");
                	}
                	
                	//Adding all the vertices for each node
                	if(lineNo>0 && lineNo<NumNodes) {
                		//System.out.println("Vertices");
                		source  = st.nextToken( );
	                	while(count1 < length1) {
	                		dest = st.nextToken( );
	                		cost  = Integer.parseInt( st.nextToken( ) ); 
	                		//System.out.println(source + " " + dest + "  " + cost);
	                		count1 = count1 + 3;
	                		g.addEdge( source, dest, cost );
	                	}
	                	//System.out.println("");
                	}
                	
                	if(lineNo > NumNodes) {
                		
                		//Number of drivers
                		if(lineNo == NumNodes + 1) {
                			NumDrivers = Integer.parseInt( st.nextToken( )); 
                    		//System.out.println("NumDrivers");
                    		//System.out.println(NumDrivers);
                    		//System.out.println("");
                		}
                		
                		//Driver address nodes
                		if(lineNo == NumNodes + 2) {
                   			int element2 = 0;
                   			int location = 0;
                			//System.out.println("Driver locations");
    	                	while(count1 < NumDrivers) {
    	                		location = Integer.parseInt( st.nextToken( ) ); 
    	                		driverLocation[count1] = location;
    	                		//System.out.println(driverLocation[count1]);	
    	                		element2++;
    	                		count1++;
    	                		
    	                	}
    	                	//System.out.println("");
                		}
                		
                		
                		//Number of requests 
                		if(lineNo == NumNodes + 3) {
                			NumRequests = Integer.parseInt( st.nextToken( )); 
                    		//System.out.println("NumRequests");
                    		//System.out.println(NumRequests);
                    		//System.out.println("");                			
                		}
                		
                		//Request pick up and drop off pairs
                		if(lineNo == NumNodes + 4) {
                			int pickUp;
                			int dropOff;
                			//System.out.println("Requests");
    	                	while(count1 < length1) {
    	                		pickUp = Integer.parseInt( st.nextToken( ) ); 
    	                		dropOff  = Integer.parseInt( st.nextToken( ) ); 
    	                		//System.out.println(pickUp + " " + dropOff);
    	                		count1 = count1 + 2;
    	                		requests[element][0] = pickUp;
    	                		requests[element][1] = dropOff;
    	                		element++;
    	                	}
    	                	//System.out.println("");
    	                	
    	                	
                		}
                		
                	}
                	lineNo ++;

                }
                catch( NumberFormatException e )
                  { System.err.println( "Skipping ill-formatted line " + line ); }
             }
            
            
        	//System.out.println("Running requests...");
        	System.out.println(" ");
        	int count2 = 0;
        	
			while(count2 < NumRequests) {
				System.out.println("client " +  requests[count2][0] + " " + requests[count2][1]);
				
				ArrayList<String> ultimatePath = new ArrayList<String>();
				totalPath path = new totalPath();
	        	totalPath pickPath = new totalPath();
	        	totalPath dropPath = new totalPath();
	        	totalPath returnPath = new totalPath();
				int[][] closestDriver = new int[element][2];
				int count3 = 0;
				while(count3<NumDrivers) {
				 System.out.println("Driver Number" + count3);
			  	 g.dijkstra(Integer.toString(driverLocation[count3]),path);
				 closestDriver[count3][0] = driverLocation[count3];
				 closestDriver[count3][1] = g.printPath(path, Integer.toString(requests[count2][0]));
				 //System.out.println("Driver Node = " + closestDriver[count3][0] + " Pickup Node " + requests[count2][0] + ", Cost = " + closestDriver[count3][1] );
				 count3++;
				} 	 	
					
					
				 //Calculating the driver closest to the pick up point.
				 int minValue = 999;
				 int minDriver = 999;
				 int length2;
				 
				
				 for(int i=0;i < element;i++){
					 path = g.dijkstra(Integer.toString(requests[count2][1]), path);
					 length2 = g.printPath(path, Integer.toString(closestDriver[i][0]));
					   if(closestDriver[i][1] < minValue){
					   	if(length2 != -1) {
				  		  minValue = closestDriver[i][1];
				  		  minDriver = closestDriver[i][0];
					   }
					}
				  }
				 
				int newCount = 0;
				for(int i=0;i < NumDrivers;i++){
					 if(minValue == closestDriver[i][1]) {
					 newCount++;
				 }
				}
				  
				  
				if(minDriver != 999) {
				  pickPath = g.dijkstra(Integer.toString(minDriver), pickPath);
				  length2 = g.printPath(pickPath, Integer.toString(requests[count2][0])); 
				}
				  
				  
				costPickup = minValue;
				if((minValue != 999) && (minDriver != 999)) {  	  
				  	  
				//Calculate the shortest route from pick up to the drop off
					
				System.out.println("truck " + minDriver); 
				System.out.println(minDriver + " " + requests[count2][0]); 
				System.out.println("pickup " + requests[count2][0]);	
					
				int length =0;
				dropPath = g.dijkstra(Integer.toString(requests[count2][0]), dropPath);
				length = g.printPath(dropPath, Integer.toString(requests[count2][1])); 
				if(length != -1) {
					costDropoff = length;
					System.out.println("dropoff " + requests[count2][1]);
					//Calculate shortest route from the drop off back to the drivers home
					
					System.out.println("Return Path");
					returnPath = g.dijkstra(Integer.toString(requests[count2][1]), returnPath);
					length = g.printPath(returnPath, Integer.toString(minDriver));
					costReturn = length;
					//System.out.println("Drop off " + requests[count2][1] + ", Drivers home " + minDriver + ", Cost = " + length );
					totalCost = costPickup + costDropoff + costReturn;
					System.out.println("solotion cost " + totalCost);
					
					
					for(int i=0;i<(pickPath.driverPath).size() - 1;i++) {
						ultimatePath.add((pickPath.driverPath).get(i));
					}
					for(int i=0;i<(dropPath.driverPath).size() - 1;i++) {
						ultimatePath.add((dropPath.driverPath).get(i));		
					}
					for(int i=0;i<(returnPath.driverPath).size(); i++) {
						ultimatePath.add((returnPath.driverPath).get(i));
					}
					
					
//					System.out.println("");
//					System.out.println("Pick path cost = " + pickPath.thisCost);
//					System.out.println("Drop path cost = " + dropPath.thisCost);
//					System.out.println("Return path cost= " + returnPath.thisCost);
					//System.out.println("Driver path cost= " + pickPath.thisCost + dropPath.thisCost + returnPath.thisCost);
					
					System.out.println("Pickpath " +" Node num  " + " Node Value  " );
					for(int i=0;i<(pickPath.nodeNum).size();i++) {
						System.out.println((pickPath.nodeNum).get(i) + "  " + (pickPath.nodeVal).get(i));
					}
					System.out.println("");
					
					System.out.println("Droppath " + " Node num  " + " Node Value  " );
					for(int i=0;i<(dropPath.nodeNum).size();i++) {
						System.out.println((dropPath.nodeNum).get(i) + "  " + (dropPath.nodeVal).get(i));
					}
					System.out.println("");
					
					System.out.println("Returnpath	" + " Node num  " + " Node Value  " );
					for(int i=0;i<(returnPath.nodeNum).size();i++) {
						System.out.println((returnPath.nodeNum).get(i) + "  " + (returnPath.nodeVal).get(i));
					}
					System.out.println("");
					
					
					for(int i=0;i<ultimatePath.size();i++) {
						if(i == ultimatePath.size() - 1) {
							System.out.println(ultimatePath.get(i));
						}else {
							System.out.print(ultimatePath.get(i) + " ");
						}
					}
					System.out.println("");
					
					
				}else {
					System.out.println("cannot be helped");
					System.out.println("");
				  	}
				  }else {
					System.out.println("cannot be helped"); 
					System.out.println("");
				  }
				  
					
				  
				  count2 ++ ;
				  
				  
				  
        	}
        	

  		  
  		
        	/*
        	 *             //Running the reuests
        	System.out.println("Running requests...");
        	int[][] closestDriver = new int[element][2];
        	int count3 = 0;
        	
        	
        	//Finding closest driver to first node
        	int count2 = 0;
        	while(count2 < element) {
        		g.dijkstra(Integer.toString(requests[count2][0]));
        		closestDriver[count2][0] = driverLocation[count2][0];
        		closestDriver[count2][1] = g.printPath(Integer.toString(requests[count2][0]));	
                count2 ++ ;
        	}
        	
            //Calculating the closest driver.
    		  int minValue = closestDriver[0][1];
    		  int minDriver = closestDriver[0][0];
    		  for(int i=1;i < closestDriver.length;i++){
    		    if(closestDriver[i][1] < minValue){
    			  minValue = closestDriver[i][1];
    			  minDriver = closestDriver[i][0];
    			}
    		  }
        	
        	//Finding shortest delivery path
        	
        	
        	//Find shortest path home
        	
        	
        	//Total path route
        	
        	//

        	 */
        	
  		  
  		  
  		  
         }
        catch( IOException e )
        { System.err.println( e ); }

//	  System.out.println("");
//      System.out.println( "File read..." );
//      System.out.println( g.vertexMap.size( ) + " vertices" );

      Scanner in = new Scanner( System.in );
 }
}