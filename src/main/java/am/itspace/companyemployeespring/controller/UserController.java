package am.itspace.companyemployeespring.controller;
import am.itspace.companyemployeespring.entity.User;
import am.itspace.companyemployeespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public String user(){
        return "/user";
    }
    @GetMapping("/user/add")
    public String useradd(){
        return "/adduser";
    }
    @GetMapping("/admin")
    public String admin(){
        return "/admin";
    }
    @PostMapping("/user/add")
    public String useradd(@ModelAttribute User user, ModelMap modelMap){
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if(byEmail.isPresent()){
            modelMap.addAttribute("error message", "email allready in use");
            return "/adduser";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return "redirect:/";
    }
}
