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
//@RequestMapping()
public class HomeController {
    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ItemRepo itemRepo;

    @GetMapping(value = {"/", "/home"})
    public  String showHome() {return "index";}

    @GetMapping("/index")
    public  String index() {return "index";}

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
    public String displayItems(@Valid @ModelAttribute("item") Item item, Model model, BindingResult result, Authentication auth) {
        if (result.hasErrors()) {
            return "itemForm2";
        }


        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        itemRepo.save(item);
        appUser.addItemtoAppUser(item);
        appUserRepository.save(appUser);

        //End of show. This method existed before to display ALL items but modified to save items
        // to currently logged in user.

        //Allows EVERYTHING stored in ItemRepo to display to Itemform.
        model.addAttribute("item", itemRepo.findAll());

        return "displayItems2";

    }

    //incomplete method NEEDS TESTING
    @GetMapping("/personalitems")
    public String showpersonalitems(Model model, Authentication auth) {

       /* Item item = itemRepo.findOne(id);*/
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        model.addAttribute("personalitems", appUser.getItems());



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

    //Insert @GetMapping("/additemtolost/{id}")

    @GetMapping("/additemtolost/{id}")
    public String additemtolostlist(@PathVariable("id") long id, Model model, Authentication auth){

        Item item = itemRepo.findOne(id);
//        Must use database user not spring security user
        AppUser appUser = appUserRepository.findAppUserByUsername(auth.getName());
        appUser.addItemtoAppUser(item);
        item.setItemStatus("Lost");
        model.addAttribute("lostitemslist", itemRepo.findOne(id));
        itemRepo.save(item);
        appUserRepository.save(appUser);
        model.addAttribute("userlist",appUserRepository.findAll());
        model.addAttribute("itemslist",appUserRepository.findAll());
        return "redirect:/displayItems";
    }


}

