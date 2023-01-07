package Main;

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

    public void printAdjacenyList(){
        StringBuilder message= new StringBuilder();
        if (this.connections.size()==0){
            System.out.println(this.person +"--_>");
        }
       //loop through connections
        for (Map.Entry<Node, List<Connection>> entry : this.connections.entrySet()) {
            for (Connection connection : entry.getValue()) {
                if (connection.getStart().equals(this)){
                    message.append(connection.getStart().getPerson()).append("--->").append(connection.getEnd().getPerson()).append(" ");
                }
            }
            System.out.println(message);
        }
    }


}
