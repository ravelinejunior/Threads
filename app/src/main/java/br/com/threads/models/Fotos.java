package br.com.threads.models;

public class Fotos {

    public Fotos() {

    }

    private Integer id;
    private Integer albumId;
    private String title;
    private String url;
    private String thumbailUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbailUrl() {
        return thumbailUrl;
    }

    public void setThumbailUrl(String thumbailUrl) {
        this.thumbailUrl = thumbailUrl;
    }
}
