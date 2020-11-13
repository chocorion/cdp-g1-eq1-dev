package domain;


public class Test {
    private int id;

    private String name;
    private String description;
    private String lastExecution;
    private String state;
    private int projectId;

    public Test() {
        id = -1;
    }

    public Test(String name, String description, String lastExecution, String state, int projectId) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.projectId = projectId;
        this.id = -1;
    }

    public Test(String name, String description, String lastExecution, String state, int id, int projectId) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.id = id;
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLastExecution() {
        return lastExecution;
    }

    public String getState() {
        return state;
    }
}
