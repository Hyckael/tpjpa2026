package user.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Admin extends User {
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    public Admin() {}

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Admin{" +
                super.toString() +
                "createdAt=" + createdAt +
                '}';
    }
}
