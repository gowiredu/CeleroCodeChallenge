package com.example.retrofittutorial;

import com.google.gson.annotations.SerializedName;

public class PostOld {
    @SerializedName("identifier")
    private int userId;

    @SerializedName("name") // so JSON parse knows text is "body" from JSON file
    private String name;

    @SerializedName("serviceReason") // so JSON parse knows text is "body" from JSON file
    private String serviceReason;

    @SerializedName("visitOrder") // so JSON parse knows text is "body" from JSON file
    private int visitOrder;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getServiceReason() {
        return serviceReason;
    }

    public int getVisitOrder() {
        return visitOrder;
    }
}
