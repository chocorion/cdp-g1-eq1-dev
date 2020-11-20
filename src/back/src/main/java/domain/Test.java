package domain;


import java.util.Comparator;

public class Test {
    final public Integer id;

    final public String name;
    final public String description;
    final public String lastExecution;
    final public String state;
    final public Integer projectId;

    final public static Comparator<Test> comparator;

    static {
        comparator = Comparator
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
    public Test () {
        this(null, null, null,null, null);
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

        return comparator.compare(this, (Test) obj) == 0;
    }
}
