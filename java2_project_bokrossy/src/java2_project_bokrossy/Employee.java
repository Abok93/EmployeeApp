package java2_project_bokrossy;

class Employee {
    private int id;
    private String name;
    private String job;
    private boolean fulltime;
    private String gender;

    // Empty constructor
    public Employee() {
    }
    // Constructor with initialized variables
    public Employee(int id, String name, String job, boolean fulltime, String gender) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.fulltime = fulltime;
        this.gender = gender;
    }

    //Getter methods to retrieve the private variables
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public boolean getFulltime() {
        return fulltime;
    }

    public String getGender() {
        return gender;
    }

    //Setter methods to set the private variables
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setFulltime(boolean fulltime) {
        this.fulltime = fulltime;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    //custom toString method
    public String toString() {
        return "ID: " + getId() + "  Name: " + getName() + "  Job Title: " + getJob() + "  Full Time: " + getFulltime()
                + "  Gender: " + getGender();
    }

}
