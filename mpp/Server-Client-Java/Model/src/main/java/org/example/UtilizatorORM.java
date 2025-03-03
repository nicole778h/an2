package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "utilizatori")
public class UtilizatorORM extends EntityORM<Long> implements Serializable {

    @Column(name = "username")
    private String username;

    @Column(name = "parola")
    private String parola;

    // Constructor fără parametri necesar pentru JPA
    public UtilizatorORM() {
    }

    public UtilizatorORM(Long id, String username, String parola) {
        this.id = id; // folosim 'this.id' din EntityORM
        this.username = username;
        this.parola = parola;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UtilizatorORM)) return false;
        UtilizatorORM utilizatororm = (UtilizatorORM) o;
        return Objects.equals(getId(), utilizatororm.getId()) &&
                Objects.equals(username, utilizatororm.username) &&
                Objects.equals(parola, utilizatororm.parola);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), username, parola);
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "id=" + getId() +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
