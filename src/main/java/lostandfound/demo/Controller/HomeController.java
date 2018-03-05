package lostandfound.demo.Controller;

import lostandfound.demo.Model.AppUser;
import lostandfound.demo.Model.Item;
import lostandfound.demo.Repositories.AppRoleRepository;
import lostandfound.demo.Repositories.AppUserRepository;
import lostandfound.demo.Repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ItemRepo itemRepo;

    @GetMapping(value = {"/", "/home"})
    public  String showHome() {return "index";}

    @PostMapping("/login")
    public String login()
    {
        return "/login2";
    }

    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register2";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser") AppUser appUser, BindingResult result, HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            return "register2";
        }

        appUser.addRole(appRoleRepository.findAppRoleByRoleName("USER"));

        appUserRepository.save(appUser);
        return "redirect:/";
    }

      @GetMapping("/addItem")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());

        return "itemForm2";

    }

    @PostMapping("/processItem")
    public String lostitems(@Valid @ModelAttribute("item") Item item, Model model, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return "itemForm2";
        }

        if(item.getImage().isEmpty()){
            item.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQv4jSEk4XcXfmO7GQcd-Di4E15P082E3P88qM1_rwRqsZUB9DZ");
        }


        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        itemRepo.save(item);
        appUser.addItemtoAppUser(item);
        appUserRepository.save(appUser);

        //End of show. This method existed before to display ALL items but modified to save items
        // to currently logged in user.

        model.addAttribute("item", itemRepo.findAll());
//testing needed if want to display to return lostitems html page
        return "home";

    }

    @GetMapping("/founditems")
    public String showfounditems(Model model, Authentication auth) {
        model.addAttribute("item", new Item());
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        model.addAttribute("item", itemRepo.findAllByitemStatusContainingIgnoreCase("found"));

        return "founditems";

    }
    // Testing below method to look same as above but for lost
    @GetMapping("/lostitems")
    public String showlostitems(Model model, Authentication auth) {
        model.addAttribute("item", new Item());
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        model.addAttribute("item", itemRepo.findAllByitemStatusContainingIgnoreCase("lost"));

        return "lostitems";

    }



    @GetMapping("/additemtofound/{id}")
    public String additemtofound(@PathVariable("id") long id, Model model, Authentication auth){

        Item item = itemRepo.findOne(id);
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        appUser.addItemtoAppUser(item);
        item.setItemStatus("Found");
        model.addAttribute("itemsfoundlist", itemRepo.findOne(id));
        itemRepo.save(item);
        appUserRepository.save(appUser);
        model.addAttribute("listusers", appUserRepository.findAll());
        model.addAttribute("itemlist", appUserRepository.findAll());
        return "redirect:/lostitems";
    }

    //Insert @GetMapping("/additemtolost/{id}")

    @GetMapping("/additemtolost/{id}")
    public String additemtolostlist(@PathVariable("id") long id, Model model, Authentication auth){

        Item item = itemRepo.findOne(id);

        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        appUser.addItemtoAppUser(item);
        item.setItemStatus("Lost");
        model.addAttribute("lostitemslist", itemRepo.findOne(id));
        itemRepo.save(item);
        appUserRepository.save(appUser);
        model.addAttribute("item",appUserRepository.findAll());
        return "redirect:/founditems";
    }


}

