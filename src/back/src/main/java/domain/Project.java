package domain;

import java.util.Optional;

public class Project {
    private Optional<Integer> id;

    private String name;
    private String description;

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = Optional.empty();
    }


    public Project(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = Optional.of(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
