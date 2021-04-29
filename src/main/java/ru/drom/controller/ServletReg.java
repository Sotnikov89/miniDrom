package ru.drom.controller;

import org.hibernate.exception.ConstraintViolationException;
import ru.drom.model.City;
import ru.drom.model.User;
import ru.drom.service.basicImpl.DefaultServiceUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletReg", urlPatterns = "/reg")
public class ServletReg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("cityId"));
        User user = User.builder()
                .name(req.getParameter("name"))
                .email(req.getParameter("email"))
                .city(City.builder().id(id).build())
                .password(req.getParameter("password"))
                .build();
        try {
            DefaultServiceUser.instOf().save(user);
        } catch (ConstraintViolationException e) {
            resp.sendError(400);
        }
    }
}
