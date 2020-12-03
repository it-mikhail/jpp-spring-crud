package crud.controller;

import crud.model.User;

import crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String printRootMenu(ModelMap model) {
        List<String> messages = new ArrayList<>();
        Map<String, String> menuMap = new LinkedHashMap<>();

        menuMap.put("User List", "/user/list");
        messages.add("Hello!");

        model.addAttribute("messages", messages);
        model.addAttribute("menuMap", menuMap);

        return "index";
    }

    @GetMapping("/user/list")
    public String printUserList(ModelMap model) {
        model.addAttribute("userList", userService.getUserList());
        model.addAttribute("userEditFormUrl", "/user/edit");
        model.addAttribute("userCreateFormUrl", "/user/new");
        model.addAttribute("userDeleteActionUrl", "/user/delete");

        return "userlist";
    }

    @GetMapping(value = "/user/new")
    public String printNewUserForm(ModelMap model) {
        model.addAttribute("pageTitle", "Create new user");
        model.addAttribute("formTitle", "New user data:");
        model.addAttribute("formAction", "/user/save");
        model.addAttribute("submitButtonText", "create");
        model.addAttribute("user", new User());

        return "useredit";
    }

    @GetMapping(value = "/user/edit" + "/{id}")
    public String printEditUserForm(@PathVariable String id, ModelMap model) {
        model.addAttribute("pageTitle", "Edit user");
        model.addAttribute("formTitle", "User data:");
        model.addAttribute("formAction", "/user/update");
        model.addAttribute("submitButtonText", "update");
        model.addAttribute("user", userService.getUserById(Integer.parseInt(id)));
        model.addAttribute("showUserId", "true");

        return "useredit";
    }

    @GetMapping(value = "/user/delete" + "/{id}")
    public String deleteUser(@PathVariable String id, ModelMap model) {
        userService.delete(userService.getUserById(Integer.parseInt(id)));
        return "redirect:" + "/user/list";
    }

    @RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") User newUser, ModelMap model) {
        userService.add(newUser);
        return "redirect:" + "/user/list";
    }

    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.update(user);
        return "redirect:" + "/user/list";
    }

}
