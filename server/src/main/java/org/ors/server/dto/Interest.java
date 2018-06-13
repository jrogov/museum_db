package org.ors.server.dto;

import org.ors.server.entity.InterestNeo;

public class Interest implements IDTO {
    private Integer counter;
    private Category category;

    public Interest() {
    }

    public Interest(InterestNeo n) {
        setCounter(n.getCounter());
        setCategory(new Category(n.getCategory()));
    }

    public Interest(Integer counter, Category category) {
        this.counter = counter;
        this.category = category;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
