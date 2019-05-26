package truonghuynhhoa.ptit.model;

import java.io.Serializable;

public class BusTurn implements Serializable {
    private Integer id;
    private String name;

    public BusTurn() {
    }

    public BusTurn(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}