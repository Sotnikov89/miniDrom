package ru.drom.dto;

import lombok.Builder;
import lombok.Data;
import ru.drom.model.Model;
import ru.drom.model.TypeBody;
import ru.drom.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class DTOAdvert {
    private int id;
    private String description;
    private Model model;
    private String make;
    private TypeBody typeBody;
    private int yearOfIssue;
    private int mileage;
    private int price;
    private int photoId;
    private boolean sold;
    private User user;
    private LocalDateTime created;
}
