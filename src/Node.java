import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Node {
    //@Serial
   // private static final long serialVersionUID = 1L;

    private  People person;
    private  Map<Node, List<Connection>> connections;

    public Node() {
    }

    public People getPerson() {
        return person;
    }

    public void setPerson(People person) {
        this.person = person;
    }

    public Node(People person) {
        this.person = person;
        this.connections= new HashMap<>();
    }

    public void addConnection(Node end){
        Connection edge= new Connection(this, end);
        if (!connections.containsKey(edge.getStart())) {
            connections.put(edge.getStart(), new ArrayList<>());
        }
        connections.get(edge.getStart()).add(edge);
        //edge.writeToFile();
    }
    public List<Connection> getConnectionsPerNode(Node id){
        return connections.getOrDefault(id, Collections.emptyList());
    }
    public Map<Node, List<Connection>> getConnections() {
        return connections;
    }

//create equals and hashcode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(person, node.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }


}
