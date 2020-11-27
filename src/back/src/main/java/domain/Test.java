package domain;


import java.util.Comparator;

public class Test {
    public final Integer id;

    public final String name;
    public final String description;
    public final String lastExecution;
    public final String state;
    public final Integer projectId;

    public static final Comparator<Test> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (Test test) -> test.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Test test) -> test.name,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing((Test test) -> test.description,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Test test) -> test.lastExecution,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Test test) -> test.state,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Test test) -> test.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    // Required by jackson
    public Test() {
        this(null, null, null, null, null);
    }

    public Test(String name, String description, String lastExecution, String state, Integer projectId) {
        this(name, description, lastExecution, state, projectId, null);
    }

    public Test(String name, String description, String lastExecution, String state, Integer projectId, Integer id) {
        this.name = name;
        this.description = description;
        this.lastExecution = lastExecution;
        this.state = state;
        this.id = id;
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Test))
            return false;

        return COMPARATOR.compare(this, (Test) obj) == 0;
    }

    @Override
    public String toString() {
        return "Test(id=" + id
                + ", name=" + name
                + ", description=" + description
                + ", lastExecution=" + lastExecution
                + ", state=" + state
                + ", projectId=" + projectId
                + ")";
    }
}
