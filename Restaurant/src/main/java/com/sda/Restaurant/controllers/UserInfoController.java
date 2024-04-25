package com.sda.Restaurant.controllers;

import com.sda.Restaurant.models.UserInfo;
import com.sda.Restaurant.repos.UserInfoRepo;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserInfoController {



    @Qualifier("userInfoRepo")
    @Autowired
    private UserInfoRepo repo;


    @GetMapping("/signup")
    public String showSignUpForm(Model model)
    {
        model.addAttribute("userinfo", new UserInfo());
        return "signup";
    }


    @PostMapping("/signup")
    public String saveUser(@ModelAttribute("userinfo")UserInfo userInfo, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "redirect:/signup";
        }
        //Checking Username
        if(repo.findByFullname(userInfo.getFullname()).isPresent())
        {
            result.rejectValue("fullname",null,"Already Exists");
            return "redirect:/signup";
        }
        //Checking Email redundancy
        if(repo.findByEmail(userInfo.getEmail()).isPresent())
        {
            result.rejectValue("email",null,"Already Exists");
            return "redirect:/signup";
        }

        ///Because Admin is only one, so it's already injected into database
        userInfo.setAdmin(false);
        repo.save(userInfo);
        return "redirect:/signup";
    }





    @GetMapping("/login")
    public String showLoginForm(Model model)
    {
        model.addAttribute("userinfo",new UserInfo());
        return "login";
    }


    @GetMapping("/error")
    public String handleError() {
        // Return the path to your custom error view
        return "error";
    }


  @GetMapping("/customerHome")
    public String showCustomerInterface(Model model)
    {
        model.addAttribute("userinfo",new UserInfo());
        return "customerHome";
    }


    @PostMapping("/login")
    public String checkCreds(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        if (email.isEmpty() || password.isEmpty()) {
            model.addAttribute("error", "Please enter both email and password.");
            return "redirect:/error";
        }

        Optional<UserInfo> userInfoOpt = repo.findByEmail(email);

        if (userInfoOpt.isPresent()) {
            UserInfo userInfo = userInfoOpt.get();
            if (userInfo.getPassword().equals(password)) {
                session.setAttribute("userinfo", userInfo);
                return "customerHome";
            }
            else {

                model.addAttribute("error", "Invalid email or password. Please try again.");
                return "error";
            }
        }


        // Redirect to signup page if email is not found
        return "redirect:/signup";
    }







}
