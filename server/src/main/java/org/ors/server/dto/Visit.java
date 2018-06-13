package org.ors.server.dto;

import org.ors.server.entity.VisitCas;

public class Visit implements IDTO {
    private String visitorid;
    private String date;
    private String tickettype;
    private Double price;
    private String excursionid;

    public Visit() {
    }

    public Visit(VisitCas c) {
        setVisitorid(c.getVisitorid());
        setDate(c.getDate());
        setTickettype(c.getTickettype());
        setPrice(c.getPrice());
        setExcursionid(c.getExcursionid());
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

    public String getExcursionid() {
        return excursionid;
    }

    public void setExcursionid(String excursionid) {
        this.excursionid = excursionid;
    }
}
