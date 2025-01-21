package com.eh.demo.controller;

import com.eh.demo.entity.Forum;
import com.eh.demo.entity.User;
import com.eh.demo.service.UserService;
import com.eh.demo.view.ForumView;
import com.eh.demo.view.RoleView;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    /*@Autowired
    UserService userService;*/

    @Autowired
    DataSource dataSource;

    @RequestMapping(value = { "/"}, method = RequestMethod.GET)
    public ModelAndView indexPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = { "/welcome"}, method = RequestMethod.GET)
    public ModelAndView welcomePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("welcome");
        return model;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("SESSION_ID", null);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        Cookie cookie_usr = new Cookie("usr", null);
        cookie_usr.setMaxAge(-1);
        cookie_usr.setPath("/");
        Cookie isAdmin = new Cookie("isAdmin", null);
        isAdmin.setMaxAge(-1);
        isAdmin.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie_usr);
        response.addCookie(isAdmin);
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String showWelcomePage(//@CookieValue(value = "usr", defaultValue = "NOT_LOGGED_IN") String usr,
                                  HttpServletResponse response, HttpServletRequest request, ModelMap model,
                                  @RequestParam String name, @RequestParam String password) throws SQLException {

        //System.out.println(name + ", " + password);

        //Safe
        //User user = userService.findByUsernameAndPassword(name, password); //service.validateUser(name, password);

        //Unsafe
        String sqlUser = "SELECT UID, USERNAME FROM USER WHERE USERNAME = '"
                + name + "' AND PASSWORD = '" + password + "';";

        Connection c = dataSource.getConnection();
        Statement stm = c.createStatement();
        ResultSet rsUser = stm.executeQuery(sqlUser);

        //List<String> roles = new ArrayList<>();
        String uid = "";
        List<String> usrName = new ArrayList<>();

        int i = 0;
        while(rsUser.next()) {
            uid = rsUser.getString("UID");//roles.add(rsUser.getString(3));
            usrName.add(rsUser.getString("USERNAME"));
            i++;
        }

        //ModelAndView mav = null;

        //if (user == null) {
        if (i == 0) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }

        String sqlRoles = "SELECT * FROM ROLE WHERE UID = '"
                + uid + "';";

        ResultSet rsRoles = stm.executeQuery(sqlRoles);

        List<String> roles = new ArrayList<>();

        int j = 0;
        while(rsRoles.next()) {
            roles.add(rsRoles.getString("ROLE"));//roles.add(rsUser.getString(3));
            j++;
        }

        model.put("name", name);
        model.put("password", password);
        model.addAttribute("roles", roles);
        model.addAttribute("usr", usrName);

        /*System.out.println(roles);

        mav = new ModelAndView("welcome");
        mav.addObject(roles);*/

        System.out.println(usrName.getFirst().replaceAll("[^A-Za-z0-9 ]", "").split(" ")[0]);

        Cookie cookie = new Cookie("SESSION_ID", sessionId());
        Cookie cookie_admin = new Cookie("isAdmin", roles.contains("ADMIN")?"ADMIN":"NOT_ADMIN");
        Cookie cookie_usr = new Cookie("usr", usrName.getFirst().replaceAll("[^A-Za-z0-9 ]", "").split(" ")[0]);
        response.addCookie(cookie);
        response.addCookie(cookie_admin);
        response.addCookie(cookie_usr);

        closeConnection(c, stm);

        return "welcome";
    }



    @RequestMapping(value = { "/users"}, method = RequestMethod.GET)
    public String getUsers(HttpServletResponse response, HttpServletRequest request, ModelMap model
                                 //@RequestParam String usr
                            ) throws SQLException {

        Map<String, String> cookiesMap = new HashMap<>();

        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }

        System.out.println(cookiesMap);

        if (!cookiesMap.containsValue("ADMIN")) {
            model.put("errorMessage", "No access to this menu item!");
            /*String site = new String("http://localhost:8080/login.jsp");
            response.setStatus(response.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", site);
            return "login";*/
            return "welcome";
        }

        String sqlUsers = "SELECT * FROM USER;";

        Connection c = dataSource.getConnection();
        Statement stm = c.createStatement();
        ResultSet rsUsers = stm.executeQuery(sqlUsers);

        List<String> users = new ArrayList<>();

        while(rsUsers.next()) {
            //roles.add(rs.getString(3));
            users.add(rsUsers.getString("USERNAME"));
        }

        model.addAttribute("users", users);

        closeConnection(c, stm);

        return "users";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser(HttpServletResponse response, HttpServletRequest request, ModelMap model,
                                  @RequestParam String usr) throws SQLException {

        //Safe
        //User user = userService.findByUsernameAndPassword(name, password); //service.validateUser(name, password);

        model.addAttribute("usr", usr);

        //Unsafe
        String sql = "SELECT USERNAME, '******', UID FROM USER WHERE USERNAME = '"
                + usr + "';";

        Connection c = dataSource.getConnection();
        Statement stm = c.createStatement();
        ResultSet rs = stm.executeQuery(sql);

        User user = null;
        String usrName = "";

        int i = 0;
        while(rs.next()) {
            //roles.add(rs.getString(3));
            user = new User(rs.getString("USERNAME"), rs.getString(2), rs.getString("UID"));
            i++;
        }

        //ModelAndView mav = null;

        //if (user == null) {
        if (i == 0) {
            model.put("errorMessage", "User not found!");
            return "user";
        }
        model.addAttribute("user", user);

        closeConnection(c, stm);

        return "user";
    }

    @RequestMapping(value = "/forum", method = RequestMethod.POST)
    public String postComment(//@CookieValue(value = "usr", defaultValue = "NOT_LOGGED_IN") String usr,
                                  HttpServletResponse response, HttpServletRequest request, ModelMap model,
                                  @RequestParam String comment) {

        if (comment == null || comment == "") {
            model.put("errorMessage", "Empty comment!");
            return "forum";
        }

        Map<String, String> cookiesMap = new HashMap<>();

        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }

        if(!cookiesMap.containsKey("usr")) {
            model.put("errorMessage", "User error!");
            return "forum";
        }

        String sqlUsersUID = "SELECT UID FROM USER WHERE USERNAME = '" + cookiesMap.get("usr") + "';";
        Connection c = null;
        Statement stm = null;
        List<ForumView> forums = new ArrayList<>();

        try {
            c = dataSource.getConnection();
            stm = c.createStatement();
            ResultSet rsUsersUID = stm.executeQuery(sqlUsersUID);

            String uid = "";

            int i = 0;
            while (rsUsersUID.next()) {
                uid = rsUsersUID.getString("UID");
                i++;
            }

            closeConnection(c, stm);

            if (uid == null || uid == "") {
                model.put("errorMessage", "User error!");
                return "forum";
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.now();
            String formattedDateTime = dateTime.format(formatter);

            System.out.println("INSERT INTO FORUM (COMMENT,UID,TIMESTAMP) VALUES ('" + comment + "', '" + uid + "', '" + formattedDateTime + "');");

            String sqlCommentsInsert = "INSERT INTO FORUM (COMMENT,UID,TIMESTAMP) VALUES ('" + comment + "', '" + uid + "', '" + formattedDateTime + "');";

            c = dataSource.getConnection();
            stm = c.createStatement();

            stm.executeUpdate(sqlCommentsInsert);

            String sqlComments = "SELECT * FROM FORUM;";

            //c = dataSource.getConnection();
            ResultSet rsComments = stm.executeQuery(sqlComments);

            Connection c2 = null;
            Statement stm2 = null;

            int j = 0;
            while (rsComments.next()) {
                String sqlUsername = "SELECT USERNAME FROM USER WHERE UID ='" + rsComments.getString("UID") + "';";
                c2 = dataSource.getConnection();
                stm2 = c2.createStatement();
                ResultSet rsUsernaame = stm2.executeQuery(sqlUsername);

                String username = rsUsernaame.getString("USERNAME");

                forums.add(new ForumView(Integer.parseInt(rsComments.getString("FID")),
                        rsComments.getString("COMMENT"),
                        username,
                        rsComments.getString("TIMESTAMP")));

                closeConnection(c2, stm2);

                j++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            Arrays.stream(e.getStackTrace()).forEach(System.out::println);
            System.err.println(e.getSQLState());
        } finally {
            try {
                if(c != null) {
                    c.close();
                }
                if(stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        model.addAttribute("forums", forums);

        closeConnection(c, stm);

        return "forum";
    }

    @RequestMapping(value = "/forum", method = RequestMethod.GET)
    public String getComment(//@CookieValue(value = "usr", defaultValue = "NOT_LOGGED_IN") String usr,
                              HttpServletResponse response, HttpServletRequest request, ModelMap model) throws SQLException {


        String sqlComments = "SELECT * FROM FORUM;";

        Connection c = dataSource.getConnection();
        Statement stm = c.createStatement();
        ResultSet rsComments = stm.executeQuery(sqlComments);

        List<ForumView> forums = new ArrayList<>();

        Connection c2;
        Statement stm2;

        int j = 0;
        while(rsComments.next()) {
            /*forums.add(new Forum(Integer.parseInt(rsComments.getString("FID")),
                    rsComments.getString("COMMENT"),
                    rsComments.getString("UID"),
                    rsComments.getString("TIMESTAMP")));*/
            String sqlUsername = "SELECT USERNAME FROM USER WHERE UID ='" + rsComments.getString("UID") + "';";
            c2 = dataSource.getConnection();
            stm2 = c2.createStatement();
            ResultSet rsUsernaame = stm2.executeQuery(sqlUsername);

            String username = rsUsernaame.getString("USERNAME");

            forums.add(new ForumView(Integer.parseInt(rsComments.getString("FID")),
                    rsComments.getString("COMMENT"),
                    username,
                    rsComments.getString("TIMESTAMP")));
            closeConnection(c2, stm2);
            j++;
        }

        model.addAttribute("forums", forums);

        closeConnection(c, stm);

        return "forum";
    }

    @RequestMapping(value = "/forum", method = RequestMethod.DELETE)
    public String deleteComment(//@CookieValue(value = "usr", defaultValue = "NOT_LOGGED_IN") String usr,
                             HttpServletResponse response, HttpServletRequest request, ModelMap model,
                                @RequestParam String fid) throws SQLException {


        String sqlRoles = "DELETE FROM FORUM WHERE FID = '" + fid + "';";

        Connection c = dataSource.getConnection();
        c.createStatement().executeUpdate(sqlRoles);

        c.commit();

        String sqlComments = "SELECT * FROM FORUM;";

        c = dataSource.getConnection();
        ResultSet rsComments = c.createStatement().executeQuery(sqlComments);

        List<ForumView> forums = new ArrayList<>();

        Connection c2;

        int j = 0;
        while(rsComments.next()) {
            String sqlUsername = "SELECT USERNAME FROM USER WHERE UID ='" + rsComments.getString("UID") + "';";
            c2 = dataSource.getConnection();
            ResultSet rsUsernaame = c2.createStatement().executeQuery(sqlUsername);

            String username = rsUsernaame.getString("USERNAME");

            forums.add(new ForumView(Integer.parseInt(rsComments.getString("FID")),
                    rsComments.getString("COMMENT"),
                    username,
                    rsComments.getString("TIMESTAMP")));
            j++;
        }

        model.addAttribute("forums", forums);

        return "forum";
    }

    private void closeConnection(Connection c, Statement stm) {
        try {
            if(c != null) {
                c.close();
            }
            if(stm != null) {
                stm.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String sessionId() {
        return UUID.randomUUID().toString().substring(21);
    }

}