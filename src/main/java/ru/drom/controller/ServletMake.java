package ru.drom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.drom.model.Make;
import ru.drom.service.basicImpl.DefaultServiceMake;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletMake", urlPatterns = "/make")
public class ServletMake extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Make> models = DefaultServiceMake.instOf().findAll();
        resp.getWriter().write(new ObjectMapper().writeValueAsString(models));
    }
}
