package com.apod.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "Subscription")
@Table(name = "Subscriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Subscription implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private String email;
    private Boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Subscription(@NotBlank String email, @NotBlank String name) {
        this.name = name;
        this.email = email;
        this.status = true;
    }

    @PrePersist
    private void prePersist() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        createdAt = currentTimestamp;
        updatedAt = currentTimestamp;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }


}
