package development.apodbackend.models;


import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Table(schema="public", name="sent_emails")
@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class SentEmailModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int subscriptionId;
    private Date createdAt;
    private Date updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public SentEmailModel(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public boolean isToday() {
        return createdAt.toString()
                        .split(" ")[0]
                        .equals(LocalDate.now().toString());
    }
}
