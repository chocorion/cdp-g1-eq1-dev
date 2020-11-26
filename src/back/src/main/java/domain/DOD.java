package domain;

import java.util.Comparator;

public class DOD {
    public final Integer id;

    public final Integer projectId;
    public final String description;
    public final Integer taskId;
    public final Boolean state;

    public static final Comparator<DOD> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (DOD dod) -> dod.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (DOD dod) -> dod.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (DOD dod) -> dod.taskId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (DOD dod) -> dod.description,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (DOD dod) -> dod.state,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    public DOD() {
        this(null, null, null, null, null);
    }


    public DOD(Integer projectId, Integer taskId, String description, Boolean state) {
        this(projectId, taskId, description, state, null);
    }

    public DOD(Integer projectId, Integer taskId, String description, Boolean state, Integer id) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
        this.state = state;
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof DOD))
            return false;

        return COMPARATOR.compare(this, (DOD) obj) == 0;
    }
}
