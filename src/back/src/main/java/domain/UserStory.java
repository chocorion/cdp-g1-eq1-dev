package domain;

import java.util.Comparator;

public class UserStory {
    public final Integer id;

    public final Integer projectId;
    public final String description;
    public final String priority;
    public final Integer difficulty;
    public final Integer sprint;

    public static final Comparator<UserStory> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (UserStory us) -> us.id,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.projectId,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.description,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.priority,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.difficulty,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (UserStory us) -> us.sprint,
                        Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    public UserStory() {
        this(null, null, null, null, null, null);
    }


    public UserStory(Integer projectId, String description, String priority, Integer difficulty, Integer sprint) {
        this(projectId, description, priority, difficulty, sprint, null);
    }

    public UserStory(Integer projectId, String description, String priority, Integer difficulty, Integer sprint, Integer id) {
        this.id = id;
        this.projectId = projectId;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.sprint = sprint;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof UserStory))
            return false;

        return COMPARATOR.compare(this, (UserStory) obj) == 0;
    }

    @Override
    public String toString() {
        return "UserStory(id=" + id
                + ", projectId=" + projectId
                + ", description=" + description
                + ", priority=" + priority
                + ", difficulty=" + difficulty
                + ", sprint=" + sprint
                + ")";
    }
}
