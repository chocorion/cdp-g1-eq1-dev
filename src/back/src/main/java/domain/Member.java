package domain;

import java.util.Comparator;

public class Member {
    final public Integer user;
    final public Integer project;
    final public String role;
    final public String level;

    final public static Comparator<Member> comporator;

    static {
        comporator = Comparator
                .comparing(
                        (Member member) -> member.user,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
                .thenComparing(
                        (Member member) -> member.project,
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
        this(null,null,null);
    }

    public Member(Integer project, String role, String level){
        this(project,role,level,null);
    }

    public Member(Integer project, String role, String level, Integer user){
        this.project = project;
        this.role = role;
        this.level = level;
        this.user = user;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if (!(obj instanceof Member))
            return false;
        return comporator.compare(this, (Member) obj) == 0;
    }
}