public class Heap {

    //a is one-indexed since we have to multiply the index by 2 and get a different result.
    //therefore a[0] is always empty!
    private Vertex a[];
    //length of the array, easy to keep track of how many elements are in the heap this way
    private int length;

    public Heap(int size) {
        //can hold up to size elements since a[0] is always empty, we have to add 1
        a = new Vertex[++size];
        //length is 0 until we start adding stuff
        this.length = 0;
    }

    //////////////////////ASSIGNED METHODS//////////////////////

    //initializes a heap from an array of keys and a size
    public static Heap heap_ini(int[] keys, int n) {
        //create a new heap of the given size
        Heap h = new Heap(n);
        //insert all values assigning the given respective keys (IDs are assigned automatically by insert)
        for(int i = 0; i < n; i++) {
            h.insert(keys[i]);
        }
        //return the new heap
        return h;
    }

    //in the spirit of OOP, instead of taking the heap as a parameter, it will be "this" object.

    //returns whether or not element id is in heap hp
    public boolean in_heap(int id) {
        //search each node until we find the one with the correct id, then return true if we find it
        if(getVertex(id) != null)
            return true;
        else
            return false;
    }

    //returns the smallest element in heap hp
    public int min_key() {
        return getMinVertex().getKey();
    }

    //returns the id of the element with the smallest key (ie the id of element returned by min_key)
    public int min_id() {
        return getMinVertex().getId();
    }

    //returns key of the element whose id is id
    public int key(int id) throws HeapException {
        if(getVertex(id) != null)
            return getVertex(id).getKey();
        else
            //it was not found, throw an exception
            throw new HeapException("There is no node with ID " + id + " in the specified heap.");
    }

    //deletes smallest value from hp
    public Vertex delete_min() {
        if(isEmpty()) {
            System.out.println("Cannot delete from empty heap");
            return null;
        }
        //overwrite a[1], the min value
        Vertex ret = a[1];
        a[1] = a[length];

        //decrease length because we're removing something
        length--;

        //index
        int current = 1;
        //this next bit moves nodes up the tree to maintain the heap property
        while(true) {
            int child = current*2;
            //if we exit the heap-tree, we're finished
            if(child > length)
                break;
            if(child + 1 <= length) {
                //take the smallest of the children
                child = a[child].getKey() <= a[child+1].getKey() ? child : child+1;
            }
            //if the child is less than the parent, we have to move it up
            if(Vertex.compare(a[current], a[child]) <= 0)
                break;
            //move it up the tree
            swap(current, child);
            //move to the next row of the tree
            current = child;
        }
        return ret;
    }

    //sets element id to new_key if [id] > new_key
    public void decrease_key(int id, int new_key) {
        if(getVertex(id) != null && getVertex(id).getKey() > new_key) {
            getVertex(id).setKey(new_key);
        }
    }

    //////////////////////CUSTOM METHODS//////////////////////

    //returns the node with the smallest key (helper for min_id and min_key methods)
    protected Vertex getMinVertex() {
        return a[1];
    }

    //returns the node with id i (helper method), returns null if not in the heap
    protected Vertex getVertex(int i) {
        //scan nodes until we find the right one
        for(int j = 1; j <= length; j++) {
            if(a[j].getId() == i)
                return a[j];
        }
        //if we didn't find it return null
        return null;
    }

    //insert a new node with key i into the heap
    protected void insert(int i) {
        //increase length because we're adding something
        length++;
        //turn the i passed into a node with id corresponding to the current length so that nodes are numbered:
        //1, 2, 3 ... length-1, length
        a[length] = new Vertex(length, i);

        //now we have to swap this new value with successive parents until it is in the correct spot
        int current = length;
        //loop until the parent value is greater than or equal to current
        while(current > 1) {
            int parent = current/2;
            //if the parent is bigger, swap to maintain heap property
            if(Vertex.compare(a[current], a[parent]) >= 0)
                break;
            swap(current, parent);
            //current node is parent now
            current = parent;
        }
    }

    //swap the nodes located at i1 and i2 in the heap
    protected void swap(int i1, int i2) {
        Vertex tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
    }

    //returns true if the heap is empty, else false
    public boolean isEmpty() {
        return length == 0;
    }

    //for testing
    public void printAll() {
        for(int i = 1; i <= length; i++) {
            System.out.printf("ID: %d Key: %d%n", a[i].getId(), a[i].getKey());
        }
    }

    //////////////////////EXCEPTION CLASS//////////////////////

    public class HeapException extends Exception {
        public HeapException(String msg) { super(msg); }
    }
}
