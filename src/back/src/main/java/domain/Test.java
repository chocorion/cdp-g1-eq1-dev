package domain;

import java.util.Date;
import java.util.Optional;

public class Test {
    private Optional<Integer> id;

    private String name;
    private String description;
    private Date lastExecution;
    private String state;
    private int projectId;

    public Test(String name, String description, Date lastExecution, String state, int projectId) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.projectId = projectId;
        this.id = Optional.empty();
    }

    public Test(String name, String description, Date lastExecution, String state, int id, int projectId) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.id = Optional.of(id);
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getProjectId() {
        return projectId;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public String getState() {
        return state;
    }
}
