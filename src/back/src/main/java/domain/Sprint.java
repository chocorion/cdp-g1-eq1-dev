package domain;

import java.util.Comparator;

public class Sprint {
    final public Integer projectId;
    final public Integer id;
    final public String name;

    final public static Comparator<Sprint> comparator;

    static {
        comparator = Comparator
                .comparing(
                        (Sprint sprint) -> sprint.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Sprint sprint) -> sprint.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Sprint sprint) -> sprint.name,
                        Comparator.nullsFirst(Comparator.naturalOrder()));      
    }

    // Required by jackson
    public Sprint () {
        this(null, null, null);
    }

    public Sprint(Integer projectId, String name){
        this(projectId, name, null);
    }

    public Sprint(Integer projectId, String name, Integer id) {
        this.projectId = projectId;
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Sprint))
            return false;

        return comparator.compare(this, (Sprint) obj) == 0;
    }

    @Override
    public String toString() {
        return "Sprint(projectId=" + projectId +
                ", id=" + id +
                ", name=" + name +
                ")";
    }
}
