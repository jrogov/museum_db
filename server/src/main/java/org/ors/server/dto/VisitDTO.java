package org.ors.server.dto;

public class VisitDTO implements IDTO {
    private String visitorid;
    private String tickettype;
    private Double price;
    private String excursionid;

    public VisitDTO() {
    }

    public VisitDTO(String visitorid, String tickettype, Double price, String excursionid) {
        this.visitorid = visitorid;
        this.tickettype = tickettype;
        this.price = price;
        this.excursionid = excursionid;
    }

    public String getVisitorid() {
        return visitorid;
    }

    public void setVisitorid(String visitorid) {
        this.visitorid = visitorid;
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

    @Override
    public String toString() {
        return "VisitDTO{" +
            "visitorid='" + visitorid + '\'' +
            ", tickettype='" + tickettype + '\'' +
            ", price=" + price +
            ", excursionid='" + excursionid + '\'' +
            '}';
    }
}
