package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Model.User;
import org.example.service.UserService;

import java.io.IOException;


@WebServlet("/users")
public class UserServlet  extends HttpServlet {

    private final UserService userService = new UserService();



    @Override
    public  void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException {
        Integer id = (Integer)request.getSession().getAttribute("id");
        String username = (String)request.getSession().getAttribute("username");
        String password = (String)request.getSession().getAttribute("password");

        if(id==null || username==null || password==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Missing required user details\"}"
            );
            return;
        }

        User newUser = new User(id , username , password);
        userService.setUser(id , newUser);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"id\":" + newUser.getId() +
                        ",\"username\":\"" + newUser.getUsername() + "\"}"
        );
    }

    @Override
    public  void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException {
        Integer id = (Integer)request.getSession().getAttribute("id");


        if(id==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Missing required user details\"}"
            );
            return;
        }

        if( !userService.findUser(id)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Missing required user details\"}"
            );
            return;
        }

        User user = userService.getUser(id);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write(
                "{\"id\":" + user.getId() +
                        ",\"username\":\"" + user.getUsername() + "\"}"
        );
    }
}
