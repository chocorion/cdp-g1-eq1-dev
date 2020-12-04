package domain;

import java.util.Comparator;

public class Release {
    public final Integer project;
    public final Integer id;
    public final Version version;
    public final String title;
    public final String description;
    public final String link;
    public final String creationDate;

    public static final Comparator<Release> COMPARATOR;

    static {
        COMPARATOR = Comparator
        .comparing(
                (Release release) -> release.project,
                        Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.version,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.creationDate,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.id,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.title,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.description,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Release release) -> release.link,
                Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    // Required by Jackson
    public Release() {
        this(null, null, null, null, null, null);
    }

    public Release(Integer project,
                String title,
                String description,
                Version version,
                String link,
                String creationDate,
                Integer id) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.version = version;
        this.link = link;
        this.creationDate = creationDate;
        this.id = id;
    }
    public Release(Integer project,
                String title,
                String description,
                Version version,
                String link,
                String creationDate) {
        this(project, title, description, version, link, creationDate, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Release))
            return false;

        return COMPARATOR.compare(this, (Release) obj) == 0;
    }
}