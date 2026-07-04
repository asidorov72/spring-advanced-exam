package bg.softuni.booknest.model.enums;

public enum Genre {

    FANTASY("Fantasy"),
    SCIENCE_FICTION("Science Fiction"),
    DRAMA("Drama"),
    THRILLER("Thriller"),
    HISTORY("History"),
    ROMANCE("Romance"),
    CHILDREN("Children");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
