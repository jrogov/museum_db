package org.ors.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@Table("visit")
public class VisitCas implements IEntity {
    @Id
    @PrimaryKey
    String id;

    private String visitorid;
    private String date;
    private String tickettype;
    private Double price;

    public VisitCas() {
    }

    public VisitCas(Visitor v, String tickettype, Double price){
        this(v.getId(), tickettype, price);
//        setId(date+"_"+visitorid);
//        setVisitorid(v.getId());
//        setTickettype(tickettype);
//        setPrice(price);
    }

    public VisitCas(String visitorid, String tickettype, Double price) {
        setDate(Long.toString(new Date().getTime()));
        this.id = getDate()+"_"+visitorid;
        this.visitorid = visitorid;
        this.tickettype = tickettype;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitorid() {
        return visitorid;
    }

    public void setVisitorid(String visitorid) {
        this.visitorid = visitorid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
