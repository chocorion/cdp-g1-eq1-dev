package domain;

import java.util.Optional;

public class Project {
    private int id;

    private String name;
    private String description;

    public Project() {
        id = -1;
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = -1;
    }


    public Project(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
