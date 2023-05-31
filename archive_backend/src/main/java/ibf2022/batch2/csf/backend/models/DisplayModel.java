package ibf2022.batch2.csf.backend.models;

import org.bson.Document;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class DisplayModel {
    private String title;
    private long date;

    public DisplayModel(String title, long date) {
        this.title = title;
        this.date = date;
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
        String title = d.getString("title");
        long date = d.getLong("date");
        return new DisplayModel(title, date);
    }

    public JsonObject toJsonObject() {
        return Json.createObjectBuilder()
                .add("title", title)
                .add("date", date)
                .build();
    }

}
