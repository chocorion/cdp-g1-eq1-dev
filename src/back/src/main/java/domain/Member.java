package domain;

import java.util.Comparator;

public class Member {
    public final Integer user;
    public final Integer project;
    public final String name;
    public final String role;
    public final String level;

    public static final Comparator<Member> COMPARATOR;

    static {
        COMPARATOR = Comparator
                .comparing(
                        (Member member) -> member.user,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Member member) -> member.project,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                    (Member member) -> member.name,
                    Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Member member) -> member.role,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Member member) -> member.level,
                        Comparator.nullsFirst(Comparator.naturalOrder()));

    }

    //Requiered by Jackson
    public Member() {
        this(null, null, null, null);
    }

    public Member(Integer project, String name, String role, String level) {
        this(project, name, role, level, null);
    }

    public Member(Integer project, String name, String role, String level, Integer user) {
        this.project = project;
        this.name = name;
        this.role = role;
        this.level = level;
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Member))
            return false;
        return COMPARATOR.compare(this, (Member) obj) == 0;
    }
}