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
                .comparing((Task task) -> task.id)
                .thenComparing((Task task) -> task.projectId)
                .thenComparing((Task task) -> task.usId)
                .thenComparing((Task task) -> task.title)
                .thenComparing((Task task) -> task.duration)
                .thenComparing((Task task) -> task.status);
    }

    public Task() {
        this(null, null, null, null, null, null);
    }

    public Task(Integer id, Integer projectId, Integer usId, String title, String duration, String status) {
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
