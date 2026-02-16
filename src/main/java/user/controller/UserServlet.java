package user.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.entity.User;
import user.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name="userServlet",
        urlPatterns={"/users","/user/add","/user/delete","/user/update"})
public class UserServlet extends HttpServlet {
    UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String type = req.getParameter("type");

        User user = userService.createUser(type, name, email, password,address,phone);

        resp.setContentType("application/json");
        resp.getWriter().write(user.toString());
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();

        if ("/users".equals(path)) {
            List<User> users = userService.getAllUsers();
            resp.setContentType("application/json");
            resp.getWriter().write(users.toString());
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Endpoint GET inconnu");
        }

    }
}
