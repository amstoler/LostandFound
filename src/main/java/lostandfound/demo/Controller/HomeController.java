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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/")
    public  String showHome() {return "index";}

    @GetMapping("/index")
    public  String index() {return "index";}

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



    @GetMapping("/displayItems")
    public String showdisplayItems(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("item", itemRepo.findAll());

        return "displayItems2";

    }

      @GetMapping("/addItem")
    public String showItemForm(Model model) {
        model.addAttribute("item", new Item());

        return "itemForm2";

    }

    @PostMapping("/processItem")
    public String displayItems(@Valid @ModelAttribute("item") Item item, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "itemForm2";
        }

        itemRepo.save(item);

        //Allows EVERYTHING stored in ItemRepo to display to Itemform.
        model.addAttribute("item", itemRepo.findAll());

        return "displayItems2";

    }

    @GetMapping("/personalitems")
    public String showpersonalitems(Model model) {
      /*  model.addAttribute("personalitem", new Item());
        model.addAttribute()*/

        return "personalitems";

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
        return "redirect:/displayItems";
    }


}

