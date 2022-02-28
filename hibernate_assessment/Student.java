package hibernate_assessment;

public class Student {
    private int id;
    private int rollno;

    private String name;
    private String university;
    public Student(){

    }

    public Student( String name,int rollno, String university) {

        this.rollno = rollno;
        this.name = name;
        this.university = university;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", rollno=" + rollno +
                ", name='" + name + '\'' +
                ", university='" + university + '\'' +
                '}';
    }
}
