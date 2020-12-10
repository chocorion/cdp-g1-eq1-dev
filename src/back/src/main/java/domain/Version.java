package domain;

import java.util.Comparator;

public class Version implements Comparable<Version> {
    public final Integer versionMajor;
    public final Integer versionMinor;
    public final Integer versionPatch;

    public static final Comparator<Version> COMPARATOR;

    static {
        COMPARATOR = Comparator
        .comparing(
                (Version version) -> version.versionMajor,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Version version) -> version.versionMinor,
                Comparator.nullsFirst(Comparator.naturalOrder()))
        .thenComparing(
                (Version version) -> version.versionPatch,
                Comparator.nullsFirst(Comparator.naturalOrder()));
    }

    // Required by Jackson
    public Version() {
        this(null, null, null);
    }

    public Version(Integer versionMajor,
                Integer versionMinor,
                Integer versionPatch) {
        this.versionMajor = versionMajor;
        this.versionMinor = versionMinor;
        this.versionPatch = versionPatch;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Version))
            return false;

        return COMPARATOR.compare(this, (Version) obj) == 0;
    }

    @Override
    public int compareTo(Version arg0) {
        return COMPARATOR.compare(this, arg0);
    }

    @Override
    public String toString() {
        return versionMajor + "." + versionMinor + "." + versionPatch;
    }
}
