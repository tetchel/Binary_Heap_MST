import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.StringTokenizer;

public class MST_Solver {

    public MST_Solver(String[] a) {
        //ensure command line parameters were passed, else exit
        if(a.length != 1) {
            System.out.println("Please specify the input file as a command line parameter.");
            System.exit(-1);
        }

        //size of heap to be made
        int n = 0;

        //file object representing input
        //tokenizer to read the file
        StringTokenizer st = null;
        try {
            //obtain the file resource and read the first line to get heap size, later we will process the rest
            File f = new File(a[0]);
            //read the contents of the file into one giant string for easy use with tokenizer
            byte[] contents = Files.readAllBytes(f.toPath());
            String file = new String(contents, Charset.defaultCharset());
            //tokenizer used to parse the integer values
            st = new StringTokenizer(file);
            //get the size of this graph from the first token
            n = Integer.parseInt(st.nextToken());
        }
        catch(IOException e) {
            System.out.println("The file specified does not exist in this directory or is otherwise invalid.");
            System.exit(-1);
        }

        //initialize keys to infinity value
        int[] keys = new int[n+1];

        for(int i = 0; i < n; i++) {
            keys[i] = Integer.MAX_VALUE;
        }

        //create the heap we will use
        Heap heap = Heap.heap_ini(keys, n);

        try {
            //now we loop through the rest of the file and initialize the graph from the vertices and edges
            while(st.hasMoreTokens()) {
                //get the id of the first vertex
                int id = Integer.parseInt(st.nextToken());
                //id of second vertex
                int id_2 = Integer.parseInt(st.nextToken());
                //weight of the edge connecting the two
                int weight = Integer.parseInt(st.nextToken());
                //get the current vertices from the heap
                Vertex v = heap.getVertex(id);
                Vertex w = heap.getVertex(id_2);
                //add the edges to the edgelists in both vertices
                //have to add them both ways!
                v.addEdge(new Edge(v, w, weight));
                w.addEdge(new Edge(w, v, weight));
            }
        }
        catch(NumberFormatException e) {
            System.out.println("Something went wrong parsing numbers from the file");
            e.printStackTrace();
        }

        //print the adjacency list representing the graph
        System.out.println("The graph as an adjacency list is as follows:");
        System.out.println("\n======================================================\n");

        //loop through vertices
        for(int i = 1; i <= n; i++) {
            Vertex v = heap.getVertex(i);
            String adjacent = i + " is adjacent to:\n";
            //loop through the edges incident on the current vertex and output the adjacencies
            for(Edge e : v.getIncident()) {
                adjacent = adjacent + e.getOther(v).getId() + ", weight = " + e.getWeight() + "\n";
            }
            System.out.print(adjacent);
        }

        //this is equivalent to the pi-array in the slides
        Edge[] mst = new Edge[n+1];

        //prim's algorithm!
        //current vertex
        Vertex u;
        //while the queue still has values, find the best way to add them to the MST
        while(!heap.isEmpty()) {
            //extract the minimum value and mark it as visited
            u = heap.delete_min();
            u.setVisited(true);
            //loop through incident edges
            for(Edge e : u.getIncident()) {
                //get the other end of the current edge
                Vertex v = e.getOther(u);
                if(!v.isVisited() && e.getWeight() < v.getKey()) {
                    mst[v.getId()] = e;
                    heap.decrease_key(v.getId(), e.getWeight());
                }
            }
        }

        System.out.println("\n======================================================\n");
        System.out.println("MST contains the following edges:");
        //print all the edges contained in the MST
        for(int i = 2; i < n; i++) {
            //loop through vertices
            //get the next edge in the MST
            Edge e = mst[i];
            if(e != null)
                //print the edge's info
                System.out.printf("%d To %d, Weight = %d%n", e.getV().getId(), e.getW().getId(), e.getWeight());
            else
                //will not appear in final program
                System.out.println("Error with edge: " + i);
        }
    }

    //filename is given as a command line parameter
    public static void main(String[] a) {
        new MST_Solver(a);
    }
}
