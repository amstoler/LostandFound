package lostandfound.demo.Setup;


import lostandfound.demo.Model.AppRole;
import lostandfound.demo.Model.AppUser;
import lostandfound.demo.Model.Item;
import lostandfound.demo.Repositories.AppRoleRepository;
import lostandfound.demo.Repositories.AppUserRepository;
import lostandfound.demo.Repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppUserRepository userRepo;

    @Autowired
    AppRoleRepository roleRepo;

    @Autowired
    ItemRepo itemRepo;

    @Override
    public void run (String...strings) throws Exception {

        AppRole myrole= new AppRole("USER");
        roleRepo.save(myrole);

        myrole= new AppRole("ADMIN");
        roleRepo.save(myrole);

        AppUser user = new AppUser("ariel@email.com","password","Ariel","Stoler", "user");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        Item item = new Item("Shoe", "black", "other", "");
        itemRepo.save(item);
        user.addItemtoAppUser(item);
        userRepo.save(user);

        user = new AppUser("Bob@email.com", "password", "Bob", "Bobberson", "admin");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        item = new Item("shirt", "red", "clothes", "");
        itemRepo.save(item);
        user.addItemtoAppUser(item);
        userRepo.save(user);

        //Adjust below data to match above examples for "items"

      /*  user = new AppUser("Tim@email.com", "password", "Tim", "Timmerson", "admin");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        foodItem = new FoodItem("Icecream", "20", "dessert" );
        foodItemRepo.save(foodItem);
        user.addFoodItem(foodItem);
        userRepo.save(user);

        user = new AppUser("Jim@email.com", "password", "Jim", "Jimmerson", "user");
        user.addRole(roleRepo.findAppRoleByRoleName("USER"));
        foodItem = new FoodItem("Salad", "15", "food");
        foodItemRepo.save(foodItem);
        user.addFoodItem(foodItem);
        userRepo.save(user);

        user = new AppUser("user@email.com", "password", "First Name", "Last Name", "admin");
        user.addRole(roleRepo.findAppRoleByRoleName("ADMIN"));
        foodItem = new FoodItem("Sprite", "30", "drink");
        foodItemRepo.save(foodItem);
        user.addFoodItem(foodItem);
        userRepo.save(user);*/

    }

}
