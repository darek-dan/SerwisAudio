package serwisAudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import serwisAudio.model.User;
import serwisAudio.service.UserService;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private UserService userService;
    @Autowired
    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAdminPanel(Model model) {
        List<User> users = userService.getAllUsersOrderByRegistrationDateTimeDesc();
        model.addAttribute("users", users);
        return "tables";
    }
    @GetMapping("/users/addAdmin&{userId}")
    public String addAdmin(@PathVariable("userId") int userId){
        userService.addTrader(userId);
        return "redirect:/admin/";
    }
    @GetMapping("/users/changeStatus&{userId}")
    public String changeUserStatus(@PathVariable("userId") int userId){
        userService.changeUserStatus(userId);
        return "redirect:/admin/";
    }


}
