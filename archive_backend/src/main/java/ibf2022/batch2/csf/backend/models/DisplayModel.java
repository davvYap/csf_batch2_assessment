package ibf2022.batch2.csf.backend.models;

import org.bson.Document;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class DisplayModel {
    private String bundleId;
    private String title;
    private long date;

    public DisplayModel(String bundleId, String title, long date) {
        this.bundleId = bundleId;
        this.title = title;
        this.date = date;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static DisplayModel convertFromDocument(Document d) {
        String id = d.getString("bundleId");
        String title = d.getString("title");
        long date = d.getLong("date");
        return new DisplayModel(id, title, date);
    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add("bundleId", bundleId)
                .add("title", title)
                .add("date", date)
                .build();
    }

}
