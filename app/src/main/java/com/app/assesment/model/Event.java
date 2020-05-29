
package com.app.assesment.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("programs")
    @Expose
    private List<Program> programs = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Event{" +
                "day='" + day + '\'' +
                ", programs=" + programs +
                '}';
    }
}
