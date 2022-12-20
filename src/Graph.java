import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private Map <People, Node> recordsmap;

    public Graph(int size){
        this.recordsmap= new HashMap<>();
    }

    public void addNode(Node data){
        recordsmap.put(data.getPerson(),data);
    }
    public Node getNode(People data) {
        return recordsmap.get(data);
    }

    public void addEdge(Node source, Node dest){
        source.addConnection(dest);
        dest.addConnection(source);
    }
    public Map<People, Node> getRecords() {
        return recordsmap;
    }

    public void setRecordsmap(Map<People, Node> recordsmap) {
        this.recordsmap = recordsmap;
    }

    public void printGraph() {
        for (Map.Entry<People, Node> entry : recordsmap.entrySet()) {
            //get the nodes
            Node node = entry.getValue();
            node.printAdjacenyList();
        }
    }

}
