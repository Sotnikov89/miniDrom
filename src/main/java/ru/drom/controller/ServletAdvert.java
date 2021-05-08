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
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet(name = "ServletAdvert", urlPatterns = "/advert")
public class ServletAdvert extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean ajax = "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
        if (ajax) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            User user = (User) req.getSession().getAttribute("user");
            Map<String, Integer> param = Stream.of(
                    new AbstractMap.SimpleEntry<>("getAll", req.getParameter("getAll")==null ? 0 : 1),
                    new AbstractMap.SimpleEntry<>("getByUser", req.getParameter("getByUser")==null ? 0 : user.getId()),
                    new AbstractMap.SimpleEntry<>("getByToday", req.getParameter("getByToday")==null ? 0 : 1),
                    new AbstractMap.SimpleEntry<>("make", parseIntOr0(req.getParameter("make"))),
                    new AbstractMap.SimpleEntry<>("model", parseIntOr0(req.getParameter("model"))),
                    new AbstractMap.SimpleEntry<>("type", parseIntOr0(req.getParameter("type"))),
                    new AbstractMap.SimpleEntry<>("mileage", parseIntOr0(req.getParameter("mileage"))),
                    new AbstractMap.SimpleEntry<>("price", parseIntOr0(req.getParameter("price"))),
                    new AbstractMap.SimpleEntry<>("photo", req.getParameter("photo")!=null && req.getParameter("photo").equals("true") ? 1 : 0)
            )
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            List<DTOAdvert> adverts = new DispatchDiapasonServ().init().filter(param);
            resp.getWriter().write(objectMapper.writeValueAsString(adverts));
        } else {
            req.getRequestDispatcher("advert.jsp").forward(req, resp);
        }
    }

    private int parseIntOr0(String value) {
        return value!=null && !value.equals("") ? Integer.parseInt(value) : 0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String advertId = req.getParameter("id");
        if (advertId != null) {
            Advert advert = DefaultServiceAdvert.instOf().findById(Integer.parseInt(advertId));
            boolean sold = req.getParameter("sold").equals("true");
            advert.setSold(sold);
            DefaultServiceAdvert.instOf().update(advert);
        } else {
            DefaultServiceAdvert.instOf().saveAdvertByReq(req);
            resp.sendRedirect(req.getContextPath() + "/advert");
        }
    }
}
