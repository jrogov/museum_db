package org.ors.server.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Author
{
    @Id
    private String id;
    private String name, country;
    private Date birthdate, deathdate;
    
    public Author(String name, String country, Date birthdate, Date deathdate)
    {
        setName(name);
        setCountry(country);
        setBirthDate(birthdate);
        setDeathDate(deathdate);
    }
    
    public String getName() { return this.name; }
    public String getCountry() { return this.country; }
    public Date getBirthDate() { return this.birthdate; }
    public Date getDeathDate() { return this.deathdate; }
    
    void setName(String name) { this.name = name; }
    void setCountry(String country) { this.country = country; }
    void setBirthDate(Date birthdate) { this.birthdate = birthdate; }
    void setDeathDate(Date deathdate) { this.deathdate = deathdate; }
}
