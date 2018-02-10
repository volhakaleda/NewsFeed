package nyc.c4q.newsfeed.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * Created by mohammadnaz on 2/5/18.
 */
@Entity(tableName = "TopNews")
public class TopNews {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name="author")
    private String author;
    @ColumnInfo(name="description")
    private String description;
    @ColumnInfo(name="url")
    private String url;
    @ColumnInfo(name="urlToImage")
    private String urlToImage;
    @ColumnInfo(name="publishedAt")
    private String publishedAt;

    public TopNews(String author, String description, String url, String urlToImage, String publishedAt) {
        this.author = author;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}