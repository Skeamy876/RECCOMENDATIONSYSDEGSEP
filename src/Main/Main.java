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
            read.close();

            double result = averageDegreeOfSeperation(source, target, peopleNet);
            System.out.println("Degree of Seperation is:" + Math.round(result));
            System.out.println("Elapsed tme: " + (endTime - startTime));
        } catch (OutOfMemoryError e) {
            increaseMemory();
        }

    }

    public static void loadData() {
        String line;
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
        System.out.println("Data Loaded");
    }

    //method to match criteria for object pairs
    public static boolean hasMatchingCriteria(People first, People second) {
        return first.getCommunity().equals(second.getCommunity()) || first.getEmployer().equals(second.getEmployer())
                || first.getSchool().equals(second.getSchool());

    }
    //calculate the average degree of seperation for the source and target
    public static double averageDegreeOfSeperation(int source, int Target, Graph net) {
        List<Node> path = findDistance(source, Target, net);
        int numberConnections = Objects.requireNonNull(path).size() - 1;
        int numberNodes = path.size();
        return (double) numberConnections / numberNodes;
    }
    //breadth first search to find the shortest distance between the source and target nodes
    public static List<Node> findDistance(int source, int Target, Graph net) {
        Map<Node, Node> prev = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        List<Node> closeContacts = new LinkedList<>();
        prev.put(getMatchingname(source, net), null);
        queue.add(getMatchingname(source, net));
        visited.add(getMatchingname(source, net));

        while (!queue.isEmpty()) {
            Node currentnode = queue.poll();
            assert currentnode != null;
            if (Objects.equals(currentnode, getMatchingname(Target, net))) {
                return constuctPath(prev, currentnode);
            }
            //iterate through connects for the currentnode
            for (Connection connection : currentnode.getConnectionsPerNode(currentnode)) {
                closeContacts.add(connection.getEnd());
            }
            for (Node trav : closeContacts) {
                if (!queue.contains(trav) && !visited.contains(trav)) {
                    queue.add(trav);
                    visited.add(trav);
                    prev.put(trav, currentnode);
                }
            }
        }
        return null;
    }
    //constructs path between the source and target
    private static List<Node> constuctPath(Map<Node, Node> prev, Node currentnode) {
        List<Node> path = new LinkedList<>();
        path.add(currentnode);
        while (prev.get(currentnode) != null) {
            currentnode = prev.get(currentnode);
            path.add(currentnode);
        }
        Collections.reverse(path);
        try {
            return path;
        } catch (NullPointerException e) {
            System.out.println("Path is empty");

        }
        return null;

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
    //return the id of the name entered
    public static int getPersonid(String namee, Graph net) {
        //get first name and last name after space
        String [] nameparts= namee.split(" ");
        System.out.println(nameparts[1]);
        System.out.println(nameparts[0]);
        String firstName=nameparts[0];
        String lastName=nameparts[1];
        //store multiple occurences of the same name
        ArrayList<People> Occurences = new ArrayList<>();

        //search for name in graph and return id and add to occurencess
        for (Map.Entry<People, Node> entry : net.getRecords().entrySet()) {
            People key = entry.getKey();
            Node value = entry.getValue();
            if (key.getFirstName().equalsIgnoreCase(firstName) && key.getLastName().equalsIgnoreCase(lastName)) {
                Occurences.add(value.getPerson());
            }
        }
        //return emptylist if zero occurences
        if (Occurences.size() == 1) {
            return Occurences.get(0).getId();
        } else if (Occurences.size() > 1) {
            System.out.println("which Person?");
            for (People i : Occurences) {
                System.out.println(i.getId() + " " + i.getFirstName() + " " + i.getLastName());
            }
            int tries = 3;
            try {
                int i = 0;
                //3 changes to select id number of intended person
                while (i<tries) {
                    Scanner read = new Scanner(System.in);
                    System.out.println("Enter ID number of the intended person");
                    int choice = read.nextInt();
                    for (People j : Occurences) {
                        if (j.getId() == choice) {
                            return j.getId();
                        }
                    }
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Invalid input");
                System.exit(0);
            }
        }
        return 0;
    }
    //returns the matching node object for the name entered
    public static Node getMatchingname(int name, Graph net) {
        for (Map.Entry<People, Node> entry : net.getRecords().entrySet()) {
            if (entry.getKey().getId() == name) {
                return entry.getValue();
            }
        }
        return null;
    }

}
