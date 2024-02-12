package development.apodbackend.models;

import java.util.Date;
import java.util.List;

import development.apodbackend.schemas.SubscriptionSchema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(schema="public", name="subscriptions")
@Entity
@EqualsAndHashCode(of="id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String username;
    private Boolean status;
    private Date created_at;
    private Date updated_at;

    
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        status = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }

    public SubscriptionModel(SubscriptionSchema userSchema) {
        this.username = userSchema.username();
        this.email = userSchema.email();
    }
}