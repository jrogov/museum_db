package org.ors.server.dto;

import org.ors.server.entity.ExcursionBase;
import org.ors.server.entity.ExcursionNeo;
import org.ors.server.entity.ExcursionNodeNeo;

import java.util.List;
import java.util.Vector;

public class Excursion extends ExcursionBase implements IDTO {

    private String id;

    private List<Room> path;
    private Category category;

    public Excursion() {
    }

    public Excursion(ExcursionNeo n){
        super(n);
        setId(n.getId().toString());
        if( n.getCategory() != null)
            setCategory(new Category(n.getCategory()));
        path = new Vector<>(n.getPathlen());
        ((Vector<Room>) path).setSize(n.getPathlen());
        for(ExcursionNodeNeo s : n.getPath() ){
            path.set(s.getIndex(), new Room(s.getRoom()));
        }
        System.out.println("New Excursion:" + n.getCategory() + " : " + getCategory());
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Room> getPath() {
        return path;
    }

    public void setPath(List<Room> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Excursion{" +
            "id='" + id + '\'' +
            ", path=" + path +
            ", category=" + category.getName() +
            '}';
    }
}

