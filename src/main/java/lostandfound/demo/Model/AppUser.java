package lostandfound.demo.Model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    // private String lastcommit;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    //This needs to be instantiated in the construtor so you can use it to add and remove individual roles
    private Set<AppRole> roles;



    public AppUser() {
        this.roles = new HashSet<>();
        this.items = new HashSet();
    }

    public AppUser(String email, String password, String firstName, String lastName, String username) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.roles = new HashSet<>();
        this.items = new HashSet();
    }

    //Creating an ADD method for addRole
    public void addRole(AppRole role) {
        this.roles.add(role);
    }


    //Start of Getters and setters for "Set<AppRole>"
    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }

    //End of Getters and setters for "Set<AppRole>"



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "item_id"))
    private Set<Item> items;

    //Start Getters and setters for "Set<FoodItem>"
    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    //End Getters and setters for "Set<FoodItem>"



    //Creating an ADD method for Item. Should be void since no return type required
    public void addItemtoAppUser(Item item) {this.items.add(item);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
