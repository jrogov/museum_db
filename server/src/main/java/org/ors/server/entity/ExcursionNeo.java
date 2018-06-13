package org.ors.server.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.ors.server.dto.Excursion;

import java.util.ArrayList;
import java.util.List;

@NodeEntity(label = "Excursion")
public class ExcursionNeo extends ExcursionBase implements IEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Relationship(type="NODE", direction = Relationship.OUTGOING)
    private List<ExcursionNodeNeo> path = new ArrayList<>();

    @Relationship(type = "CATEGORY", direction = Relationship.OUTGOING)
    private CategoryNeo category;

    public ExcursionNeo() {
    }

    public ExcursionNeo(Excursion e) {
        super(e);
        if( e.getId() != null ) setId(Long.valueOf(e.getId()));
        Integer pathlen = e.getPath().size();
        pathlen = Math.min(pathlen, e.getPathlen());
        path = new ArrayList<>(pathlen);
//        Do it manually in service
//        setCategory(new CategoryNeo(e.getCategory()));
//        for( int i=0; i<pathlen; i++){
//            path.add(new ExcursionNodeNeo(e.getPath().get(i)));
//    }
    }
    public ExcursionNeo(String date, String language, String guidename, Integer maxpeople, Integer stopsnum) {
        super(date, language, guidename, maxpeople, stopsnum);
    }

    public CategoryNeo getCategory() {
        return category;
    }

    public void setCategory(CategoryNeo category) {
        this.category = category;
    }

    public void addPathNode(ExcursionNodeNeo nodeNeo){
        path.add(nodeNeo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ExcursionNodeNeo> getPath() {
        return path;
    }

    public void setPath(List<ExcursionNodeNeo> path) {
        this.path = path;
    }
}
