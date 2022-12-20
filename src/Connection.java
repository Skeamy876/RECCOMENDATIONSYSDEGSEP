import java.io.*;

public class Connection {
   // @Serial
    //private static final long serialVersionUID = 1L;

    private Node start;
    private Node end;

    public Connection(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }

    public void writeToFile() {
        try (FileOutputStream fos = new FileOutputStream("edge_data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            this.start= null;
            this.end = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}