
public class Activities {
    private final String Name;
    private final String Activity;

    public Activities(String name, String activity) {
        Name = name;
        Activity = activity;
    }

    @Override
    public String toString() {
        return "Activities{" +
                "Name='" + Name + '\'' +
                ", Activity='" + Activity + '\'' +
                '}';
    }
}
