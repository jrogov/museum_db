package org.ors.server.entity;

import org.neo4j.ogm.annotation.Id;
import org.ors.server.dto.Exhibit;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exhibit")
public class ExhibitMongo extends ExhibitBase implements IEntity {

    @Id
    private String id;

//    public ExhibitMongo(){}

    public ExhibitMongo(Exhibit e){
        super(e);
        setId(e.getId());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
