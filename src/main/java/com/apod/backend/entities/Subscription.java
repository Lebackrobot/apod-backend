package com.apod.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Timestamp;

@Entity(name = "Subscription")
@Table(name = "Subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotBlank
    private Boolean status;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    private void prePersist() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }


}
