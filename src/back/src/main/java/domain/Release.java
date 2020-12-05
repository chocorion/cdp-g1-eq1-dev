package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Release {
    public final Integer project;
    public final Integer id;
    public final Version version;
    public final String title;
    public final String description;
    public final String link;
    public final String creationDate;
    public final List<UserStory> userStories;

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
        this(null, null, null, new Version(null, null, null), null, null, new ArrayList<UserStory>());
    }

    @SuppressWarnings("parameternumber")
    public Release(Integer project,
                String title,
                String description,
                Version version,
                String link,
                String creationDate,
                List<UserStory> userStories,
                Integer id) {
        this.project = project;
        this.title = title;
        this.description = description;
        this.version = version;
        this.link = link;
        this.creationDate = creationDate;
        this.userStories = userStories;
        this.id = id;
    }

    public Release(Integer project,
                String title,
                String description,
                Version version,
                String link,
                String creationDate,
                List<UserStory> userStories) {
        this(project, title, description, version, link, creationDate, userStories, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Release))
            return false;

        return COMPARATOR.compare(this, (Release) obj) == 0;
    }

    @Override
    public String toString() {
        return "Release(id=" + id
                + ", project=" + project
                + ", title=" + title
                + ", description=" + description
                + ", versionMaj=" + version.versionMajor
                + ", versionMin=" + version.versionMinor
                + ", versionPatch=" + version.versionPatch
                + ", link=" + link
                + ", creationDate=" + creationDate
                + ")";
    }
}