import java.util.ArrayList;

//basic data type class for holding vertex information
public class Vertex {

    //id is the identifier, key is the data/value
    private int id, key;
    //edges incident on the vertex
    private ArrayList<Edge> incident;
    //whether or not prim's algorithm has visited this
    private boolean visited;

    public Vertex(int id, int key) {
        this.id = id;
        this.key = key;
        incident = new ArrayList<>();
        visited = false;
    }

    /**
     * returns if first contains a key greater than or equal to second's key
     */
     public static int compare(Vertex first, Vertex second) {
        if(first.getKey() == second.getKey())
            return 0;
        else if(first.getKey() >= second.getKey())
            return 1;
        else
            return -1;
    }

    //add an edge incident on this vertex
    public void addEdge(Edge e) {
        incident.add(e);
    }

    //////////////////////GETTERS//////////////////////

    public int getId() {
        return id;
    }

    public int getKey() {
        return key;
    }

    public boolean isVisited() {
        return visited;
    }

    public ArrayList<Edge> getIncident() {
        return incident;
    }

    //////////////////////SETTERS//////////////////////

    public void setKey(int key) {
        this.key = key;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
