package Main;

import java.io.*;
import java.util.*;

public class Main {
    static ArrayList<People> records = new ArrayList<>();
    static ArrayList<Activities> Activitylist = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        /* / read csv from file and store in an array list of people objects and activities*/
        loadData();
        //Store loaded data into new graph
        //Declare a new graph
        Graph peopleNet = new Graph(records.size());
        try {
            for (People i : records) {
                Node node = new Node(i);
                peopleNet.addNode(node);
            }
            //Loop through records and compare first object with second object
            for (People l : records) {
                for (People j : records.subList(records.indexOf(l) + 1, records.size())) {
                    if (hasMatchingCriteria(l, j)) {
                        Node pl = peopleNet.getNode(l);
                        Node pj = peopleNet.getNode(j);
                        peopleNet.addEdge(pl, pj);
                        break;
                    }
                }
            }
        } catch (OutOfMemoryError e) {
            increaseMemory();
        }
        peopleNet.printGraph();

        //getnode count
        System.out.println("Main.Node count: " + peopleNet.getRecords().size());

        try {

            System.out.println("WELCOME TO RECOMMENDATION SYSTEM");
            long endTime = System.nanoTime();
            Scanner read = new Scanner(System.in);


            System.out.println("Enter First name:");
            int source = getPersonid(read.nextLine(), peopleNet);
            System.out.println(source);

            System.out.println("Enter Second name:");
            int target = getPersonid(read.nextLine(), peopleNet);
            System.out.println(target);


            System.out.println("Elapsed tme: " + (endTime - startTime));
        } catch (OutOfMemoryError e) {
            increaseMemory();
        }

    }

    public static void loadData() {
        String line = "";
        try (BufferedReader file = new BufferedReader(new FileReader("res/SamplefilePersons2022Oct31text.csv"))) {
            int id = 0;
            while ((line = file.readLine()) != null) {
                String[] values = line.split(",");
                String firstName = values[0];
                String lastName = values[1];
                String phoneNum = values[2];
                String email = values[3];
                String community = values[4];
                String school = values[5];
                String employer = values[6];
                String privacy = values[7];
                char choice = privacy.charAt(0);
                long phone = Long.parseLong(phoneNum);
                id++;
                records.add(new People(id, firstName, lastName, phone, email, community, school, employer, choice));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Load activities list
        try (BufferedReader file = new BufferedReader(new FileReader("res/SamplefileActivities2022Oct31text.csv"))) {
            while ((line = file.readLine()) != null) {
                String[] values = line.split(",");
                String firstName = values[0];
                String lastName = values[1];
                String activity = values[2];
                String name = firstName.concat(lastName);
                Activitylist.add(new Activities(name, activity));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(records);
        //System.out.println(Activitylist);
        System.out.println("Data Loaded");
    }

    //method to match criteria for object pairs
    public static boolean hasMatchingCriteria(People first, People second) {
        return first.getCommunity().equals(second.getCommunity()) || first.getEmployer().equals(second.getEmployer())
                || first.getSchool().equals(second.getSchool());

    }

    public static void findDistance(String source, String Target, Graph net) {
        int childCount = 0;
        if (Objects.equals(source, Target)) {
            System.exit(0);
        }
        ArrayList<Node> checked = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        List<Connection> nodelinks= new LinkedList<>();
        checked.add(getMatchingname(source, net));
        queue.add(getMatchingname(source, net));

        for (Map.Entry<People, Node> entry : net.getRecords().entrySet()) {
            String dest = entry.getValue().getPerson().getFirstName();

            Node currentnode= queue.poll();
            assert currentnode != null;
            System.out.println("Queue Main.Node:"+currentnode.getPerson().getFirstName());
            nodelinks=currentnode.getConnectionsPerNode(entry.getValue());



        }




    }

    // free up memory method using runtime maxmemory
    public static void increaseMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long mb = 1024 * 1024;
        System.out.println("free memory: " + freeMemory / mb);
        System.out.println("allocated memory: " + allocatedMemory / mb);
        System.out.println("max memory: " + maxMemory / mb);
        System.out.println("total free memory: " + (freeMemory + (maxMemory - allocatedMemory)) / mb);
        System.out.println("Increase memory");
        runtime.gc();
        maxMemory = runtime.maxMemory();
        allocatedMemory = runtime.totalMemory();
        freeMemory = runtime.freeMemory();
        System.out.println("free memory: " + freeMemory / mb);
        System.out.println("allocated memory: " + allocatedMemory / mb);
        System.out.println("max memory: " + maxMemory / mb);
        System.out.println("total free memory: " + (freeMemory + (maxMemory - allocatedMemory)) / mb);
    }
    public static int getPersonid (String namee, Graph net){
        //check if first letter of name is in upper case
        String name = "";
        if (!Character.isUpperCase(namee.charAt(0))) {
            name = namee.substring(0, 1).toUpperCase().concat(namee.substring(1));
        } else {
            name = namee;
        }

        ArrayList<People> Occurences = new ArrayList<>();
        //search for name in graph and return id
        for (Map.Entry<People, Node> entry : net.getRecords().entrySet()) {
            People key = entry.getKey();
            Node value = entry.getValue();
            if (key.getFirstName().equals(name)) {
                Occurences.add(value.getPerson());
            }
        }
        if (Occurences.size() == 1) {
            return Occurences.get(0).getId();
        } else if (Occurences.size() > 1) {
            for (People i : Occurences) {
                System.out.println("which Person?");
                System.out.println(i.getId() + " " + i.getFirstName() + " " + i.getLastName());
            }
            int tries = 3;
            try {
                for (int i = 0; i < tries; i++) {
                    Scanner read = new Scanner(System.in);
                    System.out.println("Enter ID number of the intended person");
                    int choice = read.nextInt();
                    for (People j : Occurences) {
                        if (j.getId() == choice) {
                            return j.getId();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.exit(0);
            }
        }
        return 0;
    }

    public static Node getMatchingname (String name, Graph net){
        for (Map.Entry<People, Node> entry : net.getRecords().entrySet()) {
            if (entry.getKey().getFirstName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static List<Node> getCloseContacts(List<Connection> nodelinks, String Target, Graph net){
        List <Node> closeContacts = new LinkedList<>();
        //get and store all close contacts
        for (Connection trav: nodelinks){
            closeContacts.add(trav.getEnd());
            if (closeContacts.contains(getNodeBasedonname(Target,net))){
                break;
            }
        }
        return closeContacts;
    }
    public static Node getNodeBasedonname(String name, Graph net){
        for (Map.Entry<People,Node> entry: net.getRecords().entrySet()){
            if (entry.getKey().getFirstName().equals(name)){
                return entry.getValue();
            }
        }
        return null;
    }
}