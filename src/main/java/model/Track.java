package model;

public class Track {

    private String title;
    private String description;
    private String releaseDate;
    private String url;

    public Track(String title, String description, String releaseDate, String url) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getUrlGenius() {
        return url;
    }

    public void setUrlGenius(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        if (this.description.equals("?")) {
            return this.title + "\n\n" + "Дата релиза: " + this.releaseDate + "\n\n" + "Текст:\n" + this.url;
        }
        return this.title + "\n\n" + "Дата релиза: " + this.releaseDate + "\n\n" + this.description + "\n\n" + "Текст:\n" + this.url;
    }
}
