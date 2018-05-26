package org.ors.server.dto;

import org.ors.server.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* Object to return on Requests */
public class Author extends AuthorBase implements IDTO {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // mongo ID
    private String id;

    public Author() {
    }

    public Author(String name, String country, String birthdate, String deathdate) {
        super(name, country, birthdate, deathdate);
    }

    public Author(AuthorMongo m){
        super(m);
        setId(m.getId());
    }

    public Author(AuthorNeo n){
        super(n);
        setId(n.getMongoid());

        Set<AuthorNeo> authorNeos = n.getContemporaries();
        contemporaries = new ArrayList<AuthorShort>(authorNeos.size());
        for( AuthorNeo authorNeo : authorNeos){
            contemporaries.add(new AuthorShort(authorNeo));
        }

        Set<ExhibitNeo> exhibitNeos = n.getExhibits();
        exhibits = new ArrayList<ExhibitShort>(exhibitNeos.size());
        for(ExhibitNeo exhibitNeo : exhibitNeos)
            exhibits.add(new ExhibitShort(exhibitNeo));

        Set<CategoryNeo> categoryNeos = n.getCategories();
        categories = new ArrayList<>(categoryNeos.size());
        for(CategoryNeo categoryNeo : categoryNeos)
            categories.add(new CategoryShort(categoryNeo));

    }
    // Neo4j IDs;
    private List<AuthorShort> contemporaries = new ArrayList<>();
    private List<CategoryShort> categories = new ArrayList<>();
    private List<ExhibitShort> exhibits = new ArrayList<>();

    public List<AuthorShort> getContemporaries() {
        return contemporaries;
    }

    public List<CategoryShort> getCategories() {
        return categories;
    }

    public List<ExhibitShort> getExhibits() {
        return exhibits;
    }

    @Override
    public String toString() {
        return "Author{" +
            "id='" + id + '\'' +
            ", contemporaries=" + contemporaries.toString() +
            ", name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", birthdate='" + birthdate + '\'' +
            ", deathdate='" + deathdate + '\'' +
            '}';
    }
}
