package lk.sasax.GreenShadow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recipient;
    private String subject;
    private String body;
}
