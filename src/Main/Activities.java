package Main;
public class Activities {
    private  String firstName;
    private  String lastName;
    private String activity;


    public Activities(String firstName, String lastName,String activity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.activity= activity;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    @Override
    public String toString() {
        return "Activities{" +
                ", Activity='" + activity+ '\'' +
                    '}';
        }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}


