package domain;

import java.util.Comparator;

public class Project {
    final public Integer id;
    final public String name;
    final public String description;

    final public static Comparator<Project> comparator;

    static {
        comparator = Comparator
                .comparing(
                        (Project project) -> project.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Project project) -> project.name,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Project project) -> project.description,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    // Required by Jackson
    public Project() {
        this(null, null);
    }

    public Project(String name, String description) {
        this(name, description, null);
    }

    public Project(String name, String description, Integer id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Project))
            return false;

        return comparator.compare(this, (Project) obj) == 0;
    }
}