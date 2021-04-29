package ru.drom.service.basicImpl;

import com.google.common.io.Files;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.drom.dao.DaoAdvert;
import ru.drom.model.*;
import ru.drom.service.ServiceAdvert;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultServiceAdvert implements ServiceAdvert {

    private final DaoAdvert daoAdvert;

    private DefaultServiceAdvert() { this.daoAdvert = DaoAdvert.instOf(); }

    public static DefaultServiceAdvert instOf() { return new DefaultServiceAdvert(); }

    @Override
    public Advert findById(int id) {
        return daoAdvert.findById(id);
    }

    @Override
    public List<Advert> findAll() {
        return daoAdvert.findAll();
    }

    @Override
    public List<Advert> findAllByUserId(int userId) {
        return daoAdvert.findAllByUserId(userId);
    }

    @Override
    public List<Advert> findAllThisDay() { return daoAdvert.findAllThisDay(); }

    @Override
    public List<Advert> findByFilter(HttpServletRequest req) {
        List<Advert> adverts = findAll();
        String make = req.getParameter("make");
        if (make != null) { adverts.removeIf(advert -> advert.getModel().getMake().getId() != Integer.parseInt(make)); }
        String model = req.getParameter("model");
        if (model != null) { adverts.removeIf(advert -> advert.getModel().getId() != Integer.parseInt(model)); }
        String type = req.getParameter("type");
        if (type != null) { adverts.removeIf(advert -> advert.getTypeBody().getId() != Integer.parseInt(type)); }
        String mileage = req.getParameter("mileage");
        if (mileage != null) { adverts.removeIf(advert -> advert.getMileage() > Integer.parseInt(mileage)); }
        String price = req.getParameter("price");
        if (price != null) { adverts.removeIf(advert -> advert.getPrice() > Integer.parseInt(price)); }
        String photo = req.getParameter("photo");
        if (photo.equals("true")) { adverts.removeIf(advert -> advert.getPhotoId() == 0); }
        return adverts;
    }

    @Override
    public Advert save(Advert advert) {
        return daoAdvert.save(advert);
    }

    @Override
    public void update(Advert advert) {
        daoAdvert.update(advert);
    }

    @Override
    public void delete(int id) {
        daoAdvert.delete(id);
    }

    @Override
    public void saveAdvertByReq(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = req.getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images\\");
            if (!folder.exists()) {
                folder.mkdir();
            }
            Advert advert = save(Advert.builder()
                    .user((User) req.getSession().getAttribute("user"))
                    .created(LocalDateTime.now())
                    .sold(false)
                    .build());
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString("UTF-8");
                    if (fieldName.equals("desc")) {
                        advert.setDescription(fieldValue);
                        continue;
                    }
                    if (fieldName.equals("model")) {
                        advert.setModel(Model.builder().id(Integer.parseInt(fieldValue)).build());
                        continue;
                    }
                    if (fieldName.equals("type")) {
                        advert.setTypeBody(TypeBody.builder().id(Integer.parseInt(fieldValue)).build());
                        continue;
                    }
                    if (fieldName.equals("mileage")) {
                        advert.setMileage(Integer.parseInt(fieldValue));
                        continue;
                    }
                    if (fieldName.equals("price")) {
                        advert.setPrice(Integer.parseInt(fieldValue));
                    }
                    if (fieldName.equals("yearOfIssue")) {
                        advert.setYearOfIssue(Integer.parseInt(fieldValue));
                    }
                } else {
                    File file = new File(folder + File.separator
                            + advert.getId() + "."
                            + Files.getFileExtension(item.getName()));
                    try (FileOutputStream out = new FileOutputStream(file)) {
                        out.write(item.getInputStream().readAllBytes());
                    }
                    advert.setPhotoId(advert.getId());
                }
                update(advert);
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
    }
}
