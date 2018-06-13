package org.ors.server.entity;

import org.neo4j.ogm.annotation.Relationship;
import org.ors.server.dto.Category;

public class ExcursionBase {
    private String date;
    private String language;
    private String guidename;
    private Integer maxpeople;
    private Integer pathlen;

    public ExcursionBase() {
    }

    public ExcursionBase(ExcursionBase b){
        setDate(b.getDate());
        setLanguage(b.getLanguage());
        setGuidename(b.getGuidename());
        setMaxpeople(b.getMaxpeople());
        setPathlen(b.getPathlen());
    }

    public ExcursionBase(String date, String language, String guidename, Integer maxpeople, Integer pathlen) {
        this.date = date;
        this.language = language;
        this.guidename = guidename;
        this.maxpeople = maxpeople;
        this.pathlen = pathlen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGuidename() {
        return guidename;
    }

    public void setGuidename(String guidename) {
        this.guidename = guidename;
    }

    public Integer getMaxpeople() {
        return maxpeople;
    }

    public void setMaxpeople(Integer maxpeople) {
        this.maxpeople = maxpeople;
    }

    public Integer getPathlen() {
        return pathlen;
    }

    public void setPathlen(Integer pathlen) {
        this.pathlen = pathlen;
    }
}
