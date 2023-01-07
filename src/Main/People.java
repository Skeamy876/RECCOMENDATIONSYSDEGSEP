package Main;
import java.util.ArrayList;
import java.util.Objects;
public class People {
    private int id;
    private String firstName;
    private String lastName;
    private long phoneNum;
    private String email;
    private String community;
    private String school;
    private String employer;
    private char privacy;
    private final ArrayList<Activities> activities;

    public People(int id, String firstName, String lastName, long phoneNum, String email, String community, String school, String employer, char privacy){//, Activities activities) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.community = community;
        this.school = school;
        this.employer = employer;
        this.privacy = privacy;
        this.activities= new ArrayList<Activities>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public char getPrivacy() {
        return privacy;
    }

    public void setPrivacy(char privacy) {
        this.privacy = privacy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People person = (People) o;
        return id == person.id &&
                firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FirstName: ").append(firstName).append(", lastName: ").append(lastName).append("\n");
        sb.append("Activities: ");
        for (Activities activity : activities) {
            sb.append(activity.getActivity()).append(",");
        }
        return sb.toString();
    }
    public boolean requestPrivacy(){
        return getPrivacy() == 'N';
    }
    //method to check if activities contains elements
    public boolean hasActivities(){
        return !activities.isEmpty();
    }

    public void setActivities(Activities activity) {
        activities.add(activity);
    }
    public ArrayList <Activities> getActivities() {
        return activities;
    }

    //public ArrayList <String> getActivitieslist() {
       // ArrayList <String> activitieslist= new ArrayList<>();



       // return activitieslist;
    //}

}
