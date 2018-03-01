package lostandfound.demo.Controller;

import lostandfound.demo.Model.AppUser;
import lostandfound.demo.Model.Item;
import lostandfound.demo.Repositories.AppRoleRepository;
import lostandfound.demo.Repositories.AppUserRepository;
import lostandfound.demo.Repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
//@RequestMapping()
public class HomeController {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ItemRepo itemRepo;

    @GetMapping("/home")
    public  String showHome() {return "home";}

    @PostMapping("/index")
    public  String index() {return "home";}
    @GetMapping("/register")
    public String registerUser(Model model)
    {
        model.addAttribute("newuser",new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@Valid @ModelAttribute("newuser") AppUser appUser, BindingResult result, HttpServletRequest request)
    {
        if(result.hasErrors())
        {
            return "register";
        }

        appUser.addRole(appRoleRepository.findAppRoleByRoleName("USER"));

        appUserRepository.save(appUser);
        return "redirect:/user/home";
    }

    @GetMapping("/addItem")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());

        return "itemForm";

    }

    @PostMapping("/processItem")
    public String displayItems(@Valid @ModelAttribute("item") Item item, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "itemForm";
        }

        itemRepo.save(item);

        //Allows EVERYTHING stored in foodItemRepo to display to Itemform.
        model.addAttribute("item", itemRepo.findAll());

        return "displayItems";

    }


}

