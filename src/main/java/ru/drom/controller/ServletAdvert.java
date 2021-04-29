package ru.drom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.drom.dto.DTOAdvert;
import ru.drom.model.Advert;
import ru.drom.model.User;
import ru.drom.service.basicImpl.DefaultServiceAdvert;
import ru.drom.transport.ImplAdvertMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletAdvert", urlPatterns = "/advert")
public class ServletAdvert extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
        if (ajax) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            if (req.getParameter("getAll") != null) {
                List<Advert> adverts = DefaultServiceAdvert.instOf().findAll();
                List<DTOAdvert> dtoAdverts = new ImplAdvertMapper().advertsToDtoAdverts(adverts);
                resp.getWriter().write(objectMapper.writeValueAsString(dtoAdverts));
            }
            if (req.getParameter("getByUser") != null) {
                User user = (User) req.getSession().getAttribute("user");
                List<Advert> adverts = DefaultServiceAdvert.instOf().findAllByUserId(user.getId());
                List<DTOAdvert> dtoAdverts = new ImplAdvertMapper().advertsToDtoAdverts(adverts);
                resp.getWriter().write(objectMapper.writeValueAsString(dtoAdverts));
            }
            if (req.getParameter("getByToday") != null) {
                List<Advert> adverts = DefaultServiceAdvert.instOf().findAllThisDay();
                List<DTOAdvert> dtoAdverts = new ImplAdvertMapper().advertsToDtoAdverts(adverts);
                resp.getWriter().write(objectMapper.writeValueAsString(dtoAdverts));
            } else {
                List<Advert> adverts = DefaultServiceAdvert.instOf().findByFilter(req);
                List<DTOAdvert> dtoAdverts = new ImplAdvertMapper().advertsToDtoAdverts(adverts);
                resp.getWriter().write(objectMapper.writeValueAsString(dtoAdverts));
            }
        } else {
            req.getRequestDispatcher("advert.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DefaultServiceAdvert.instOf().saveAdvertByReq(req);
        resp.sendRedirect(req.getContextPath() + "/advert");
    }
}
