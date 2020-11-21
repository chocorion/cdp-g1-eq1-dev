package domain;

import java.util.Comparator;

public class Task {
    final public Integer id;
    final public Integer projectId;
    final public Integer usId;
    final public String title;
    final public String duration;
    final public String status;

    final public static Comparator<Task> comparator;

    static {
        comparator = Comparator
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

    public Task(Integer projectId, Integer usId, String title, String duration, String status) {
        this(projectId, usId, title, duration, status, null);
    }

    public Task(Integer projectId, Integer usId, String title, String duration, String status, Integer id) {
        this.id = id;
        this.projectId = projectId;
        this.usId = usId;
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

        return comparator.compare(this, (Task) obj) == 0;
    }
}
