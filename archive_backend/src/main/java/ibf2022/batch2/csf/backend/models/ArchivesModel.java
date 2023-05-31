package ibf2022.batch2.csf.backend.models;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class ArchivesModel {
    private String bundleId;
    private String title;
    private String name;
    private String comments;
    private Long date;
    private List<String> urls;

    public ArchivesModel() {
    }

    public ArchivesModel(String bundleId, String title, String name, String comments, Long date,
            List<String> urls) {
        this.bundleId = bundleId;
        this.title = title;
        this.name = name;
        this.comments = comments;
        this.date = date;
        this.urls = urls;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public JsonObject toJsonObject() {

        JsonArrayBuilder jsArr = Json.createArrayBuilder();

        for (String url : urls) {
            jsArr.add(url);

        }
        return Json.createObjectBuilder()
                .add("bundleId", bundleId)
                .add("date", new Date().toInstant().toEpochMilli())
                .add("title", title)
                .add("name", name)
                .add("comments", comments == null ? "" : comments)
                .add("urls", jsArr)
                .build();
    }

    public static ArchivesModel convertFromDocument(Document d) {
        ArchivesModel m = new ArchivesModel();
        m.setBundleId(d.getString("bundleId"));
        m.setName(d.getString("name"));
        m.setTitle(d.getString("title"));
        m.setComments(d.getString("comments"));
        m.setUrls(d.getList("urls", String.class));
        m.setDate(d.getLong("date"));
        return m;
    }

}
