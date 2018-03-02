package lostandfound.demo.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

   // private String image;

    private String description;

    private String type;
//Admin only should be able to change status of item. Lost or found.
   // private String itemStatus;

    //Describe the relationship between Item and AppUser. Create getters and setters for hash set.

    @ManyToMany(mappedBy = "items", fetch = FetchType.LAZY)
    private Set<AppUser> users;
    //Instantiated in constructor to allow for additon and removal of users
    public Item() {this.users = new HashSet<>();}


    public Item(String title, String description, String type) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.users = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
