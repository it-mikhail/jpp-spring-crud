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

    final private String ROOT_MENU_URL = "/";

    final private String USER_LIST_URL = "/user/list";

    final private String USER_CREATE_FORM_URL = "/user/new";
    final private String USER_UPDATE_FORM_URL = "/user/edit";

    final private String USER_CREATE_ACTION_URL = "/user/save";
    final private String USER_UPDATE_ACTION_URL = "/user/update";
    final private String USER_DELETE_ACTION_URL = "/user/delete";

    final private String ROOT_MENU_TEMPLATE = "index";

    final private String USER_LIST_TEMPLATE = "userlist";
    final private String USER_UPDATE_TEMPLATE = "useredit";
    final private String USER_CREATE_TEMPLATE = USER_UPDATE_TEMPLATE;

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = ROOT_MENU_URL)
    public String printRootMenu(ModelMap model) {
        List<String> messages = new ArrayList<>();
        Map<String, String> menuMap = new LinkedHashMap<>();

        menuMap.put("User List", USER_LIST_URL);
        messages.add("Hello!");

        model.addAttribute("messages", messages);
        model.addAttribute("menuMap", menuMap);

        return ROOT_MENU_TEMPLATE;
    }

    @GetMapping(value = USER_LIST_URL)
    public String printUserList(ModelMap model) {
        model.addAttribute("userList", userService.getUserList());
        model.addAttribute("userEditFormUrl", USER_UPDATE_FORM_URL);
        model.addAttribute("userCreateFormUrl", USER_CREATE_FORM_URL);
        model.addAttribute("userDeleteActionUrl", USER_DELETE_ACTION_URL);

        return USER_LIST_TEMPLATE;
    }

    @GetMapping(value = USER_CREATE_FORM_URL)
    public String printNewUserForm(ModelMap model) {
        model.addAttribute("pageTitle", "Create new user");
        model.addAttribute("formTitle", "New user data:");
        model.addAttribute("formAction", USER_CREATE_ACTION_URL);
        model.addAttribute("submitButtonText", "create");
        model.addAttribute("user", new User());

        return USER_UPDATE_TEMPLATE;
    }

    @GetMapping(value = USER_UPDATE_FORM_URL + "/{id}")
    public String printEditUserForm(@PathVariable String id, ModelMap model) {
        model.addAttribute("pageTitle", "Edit user");
        model.addAttribute("formTitle", "User data:");
        model.addAttribute("formAction", USER_UPDATE_ACTION_URL);
        model.addAttribute("submitButtonText", "update");
        model.addAttribute("user", userService.getUserById(Integer.parseInt(id)));
        model.addAttribute("showUserId", "true");

        return USER_UPDATE_TEMPLATE;
    }

    @GetMapping(value = USER_DELETE_ACTION_URL + "/{id}")
    public String deleteUser(@PathVariable String id, ModelMap model) {
        userService.delete(Integer.parseInt(id));
        return "redirect:" + USER_LIST_URL;
    }

    @RequestMapping(value = USER_CREATE_ACTION_URL, method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") User newUser, ModelMap model) {
        userService.add(newUser);
        return "redirect:" + USER_LIST_URL;
    }

    @RequestMapping(value = USER_UPDATE_ACTION_URL, method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, ModelMap model) {
        userService.update(user);
        return "redirect:" + USER_LIST_URL;
    }

}
