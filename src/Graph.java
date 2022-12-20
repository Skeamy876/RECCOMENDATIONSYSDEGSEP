import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Graph {
  //  @Serial
    //private static final long serialVersionUID = 1L;

    private Map <People, Node> recordsmap;

    public Graph(){
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
            System.out.println(entry.getKey() + " " + entry.getValue().getConnections());
        }
    }
}
