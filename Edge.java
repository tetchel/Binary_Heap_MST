//data type class representing an edge on the graph
public class Edge {

    //each side of the edge is a vertex
    private Vertex v, w;
    //weight of this edge
    private int weight;

    public Edge(Vertex v, Vertex w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    //////////////////////GETTERS//////////////////////

    public Vertex getV() {
        return v;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getW() {
        return w;
    }

    /**
     * Takes a vertex on this edge as a parameter and returns the other end of the edge
     * @param in the starting vertex
     * @return the ending vertex
     */
    public Vertex getOther(Vertex in) {
        if(in == v)
            return w;
        else if(in == w)
            return v;
        //does not occur, required for compilation
        else {
            System.out.println("Something went wrong in getOther");
            return null;
        }
    }
}
