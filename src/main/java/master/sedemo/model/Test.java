package master.sedemo.model;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    private String uid;

    private boolean hasFeatureEnabled;

    public Test() {

    }

    public Test(String name, String description, String uid, Boolean hasFeatureEnabled) {
        this.name = name;
        this.description = description;
        this.uid = uid;
        this.hasFeatureEnabled = hasFeatureEnabled;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription(){
        return description;
    }
    public String getUid() {
        return uid;
    }

    public Boolean getHasFeatureEnabled() {
        return hasFeatureEnabled;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
//    public String setUid() {
//        this.uid = uid;
//    }
    public void setHasFeatureEnabled(Boolean hasFeatureEnabled) { this.hasFeatureEnabled = hasFeatureEnabled; }


}

