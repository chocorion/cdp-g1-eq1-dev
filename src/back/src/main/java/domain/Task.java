package domain;

import java.util.Comparator;

public class Task {
    public final Integer id;
    public final Integer projectId;
    public final Integer usId;
    public final Integer memberId;
    public final String title;
    public final String duration;
    public final String status;

    public static final Comparator<Task> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (Task task) -> task.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.usId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.memberId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.title,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.duration,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Task task) -> task.status,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    public Task() {
        this(null, null, null, null, null, null);
    }

    public Task(Integer projectId, Integer usId, Integer memberId, String title, String duration, String status) {
        this(projectId, usId, memberId, title, duration, status, null);
    }

    public Task(Integer projectId, Integer usId, Integer memberId, String title, String duration, String status, Integer id) {
        this.id = id;
        this.projectId = projectId;
        this.usId = usId;
        this.memberId = memberId;
        this.title = title;
        this.duration = duration;
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Task))
            return false;

        return COMPARATOR.compare(this, (Task) obj) == 0;
    }
}
