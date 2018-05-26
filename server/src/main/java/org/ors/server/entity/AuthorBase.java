package org.ors.server.entity;

import org.ors.server.dto.IDTO;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

/* Common properties of Entities in multiple databases */
public class AuthorBase {

    @NotNull
    protected String name;

    @NotNull
    protected String country;

    @NotNull
    protected String birthdate;

    protected String deathdate;

    public String getName() { return this.name; }
    public String getCountry() { return this.country; }
    public String getBirthdate() { return this.birthdate; }
    public String getDeathdate() { return this.deathdate; }

    public void setName(String name) { this.name = name; }
    public void setCountry(String country) { this.country = country; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public void setDeathdate(String deathdate) { this.deathdate = deathdate; }

    public AuthorBase() {}

    /* Used for easy conversion between DB entities and DTO */
    public AuthorBase(AuthorBase b){
        setName(b.getName());
        setBirthdate(b.getBirthdate());
        setDeathdate(b.getDeathdate());
        setCountry(b.getCountry());
    }

    public AuthorBase(String name, String country, String birthdate, String deathdate)
    {
        setName(name);
        setCountry(country);
        setBirthdate(birthdate);
        setDeathdate(deathdate);
    }

    @Override
    public String toString() {
        return "AuthorBase{" +
            "name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", birthdate='" + birthdate + '\'' +
            ", deathdate='" + deathdate + '\'' +
            '}';
    }
}
