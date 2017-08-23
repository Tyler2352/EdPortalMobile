package com.example.tyler.test2;

/**
 * Created by Tyler on 1/16/2017.
 */

public class Doc {

    private String id;
    private String projlead;
    private String projtitle;

    public Doc(String id, String projlead, String projtitle) {
        this.id = id;
        this.projlead = projlead;
        this.projtitle = projtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjlead() {
        return projlead;
    }

    public void setProjlead(String projlead) {
        this.projlead = projlead;
    }

    public String getProjtitle() {
        return projtitle;
    }

    public void setProjtitle(String projtitle) {
        this.projtitle = projtitle;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + ", projlead: " + this.projlead + ", projtitle: " + this.projtitle + "\n";
    }
}
