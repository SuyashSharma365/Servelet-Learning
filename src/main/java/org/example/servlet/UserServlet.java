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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");


        String idJson = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(idJson==null || username==null || password==null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Missing required user details\"}"
            );
            return;
        }

        Integer id = Integer.parseInt(idJson);

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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String idJson;
        idJson = request.getParameter("id");


        if(idJson == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing required user id\"}");
            return;
        }

        Integer id = Integer.parseInt(idJson);

        if( !userService.findUser(id)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"User not found\"}"
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
