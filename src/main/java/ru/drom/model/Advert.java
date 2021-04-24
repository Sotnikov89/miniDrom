package ru.drom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @OneToOne
    private Model model;
    @OneToOne
    private TypeBody typeBody;
    private int mileage;
    private int price;
    private int photoId;
    private boolean status;
    @ManyToOne
    private User user;
    private LocalDateTime created;
}
