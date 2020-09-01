package com.ecsite.demo.controller;

import com.ecsite.demo.domain.User;
import com.ecsite.demo.domain.UserDetailsImpl;
import com.ecsite.demo.form.UserForm;
import com.ecsite.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @ModelAttribute
    public UserForm userFormSetUp(){
        return new UserForm();
    }

    @RequestMapping("/login_form")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/register_form")
    public String toRegister(){
        return "register_user";
    }

    @RequestMapping("/register")
    public String register(
            @Valid UserForm userForm,
            BindingResult result,
            Model model
    ){

        if(!userForm.getPassword().equals(userForm.getConfirmationPassword())){
            result.rejectValue("password", "", "パスワードが一致していません");
            result.rejectValue("confirmationPassword", "", "");

        }

        UserDetailsImpl existUser = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(userForm.getEmail());
        if(nonNull(existUser)){
            result.rejectValue("email", "", "そのメールアドレスは既に登録されています");
        }

        if(result.hasErrors()){
            return toRegister();
        }

        String zipCode = userForm.getZipcode().replace("-","");

        User user = new User();
        user.setName(userForm.getName());
        user.setPassword(userForm.getPassword());
        user.setEmail(userForm.getEmail());
        user.setAddress(userForm.getAddress());
        user.setZipcode(zipCode);
        user.setTelephone(userForm.getTelephone());

        userDetailsServiceImpl.userInsert(user);


        return "redirect:/user/login_form";
    }
}
