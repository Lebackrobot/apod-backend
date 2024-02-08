package development.apodbackend.models;

import java.util.Date;

import development.apodbackend.schemas.UserSchema;
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

@Table(schema="public", name="user")
@Entity
@EqualsAndHashCode(of="id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String nickname;
    private Date created_at;
    private Date updated_at;

    
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }

    public UserModel(UserSchema userSchema) {
        this.nickname = userSchema.nickname();
        this.email = userSchema.email();
    }
}
