package ru.drom.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
    @OneToOne(fetch = FetchType.LAZY)
    private Model model;
    @OneToOne(fetch = FetchType.LAZY)
    private TypeBody typeBody;
    private int mileage;
    private int price;
    private int photoId;
    private int yearOfIssue;
    private boolean sold;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private LocalDateTime created;
}
