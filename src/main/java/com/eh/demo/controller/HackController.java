package com.eh.demo.controller;

import com.eh.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HackController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/hack")
    public String hack(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hack";
    }

    public List<User> getUsers() {
        List<User> users = jdbcTemplate.query("SELECT * FROM user",
                (resultSet, rowNum) -> new User(resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")));

        return users;
    }

}
