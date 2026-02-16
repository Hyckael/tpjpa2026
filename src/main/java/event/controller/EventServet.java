package event.controller;

import event.service.EventService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EventServet extends HttpServlet {
    EventService eventService;
    @Override
    public void init(){
        eventService=new EventService();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int place  = Integer.parseInt(req.getParameter("place"));
        String description = req.getParameter("description");
        String date = req.getParameter("date");
        String address = req.getParameter("address");

    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {}
}
