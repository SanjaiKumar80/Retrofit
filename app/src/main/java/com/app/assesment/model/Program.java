
package com.app.assesment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Program {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("event")
    @Expose
    private String event;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Program{" +
                "time='" + time + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
