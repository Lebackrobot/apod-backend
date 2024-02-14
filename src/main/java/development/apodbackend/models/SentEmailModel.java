package development.apodbackend.models;


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

@Table(schema="public", name="sent_emails")
@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentEmailModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int subscription_id;
    private Date created_at;
    private Date updated_at;

    @PrePersist
    protected void onCreated() {
        created_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }

    public SentEmailModel(int subscription_id) {
        this.subscription_id = subscription_id;
    }
}
