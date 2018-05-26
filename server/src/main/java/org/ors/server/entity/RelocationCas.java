package org.ors.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Table("relocation")
public class RelocationCas implements IEntity {

    @Id
    @PrimaryKey
    private String id;

    private String exhibitid;

    private String fromroomid;
    private String toroomid;

    public RelocationCas() {
    }

    public RelocationCas(String exhibitid, String fromroomid, String toroomid) {
        this.id = new Date().getTime() + "_" + exhibitid + "_" + fromroomid + "_"+toroomid;
        this.exhibitid = exhibitid;
        this.fromroomid = fromroomid;
        this.toroomid = toroomid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExhibitid() {
        return exhibitid;
    }

    public void setExhibitid(String exhibitid) {
        this.exhibitid = exhibitid;
    }

    public String getFromroomid() {
        return fromroomid;
    }

    public void setFromroomid(String fromroomid) {
        this.fromroomid = fromroomid;
    }

    public String getToroomid() {
        return toroomid;
    }

    public void setToroomid(String toroomid) {
        this.toroomid = toroomid;
    }
}
