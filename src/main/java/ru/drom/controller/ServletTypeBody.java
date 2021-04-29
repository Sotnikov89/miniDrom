package ru.drom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.drom.model.TypeBody;
import ru.drom.service.basicImpl.DefaultServiceTypeBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletTypeBody", urlPatterns = "/type")
public class ServletTypeBody extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<TypeBody> typeBodies = DefaultServiceTypeBody.instOf().findAll();
        resp.getWriter().write(new ObjectMapper().writeValueAsString(typeBodies));
    }
}
