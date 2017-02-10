package net.siekiera.garbageNotifier.model;

import javax.persistence.*;

/**
 * Created by Eric on 29.01.2017.
 */
@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;
    private String phone_number;
    //token provided by system
    private String token;
    //token provided by user
    @Transient
    private String tokenCheck;
    private boolean active;
    //uzyjemy tego pola do precyzyjnego ustawienia kiedy sms ma zostac wyslany.
    private Integer czas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getCzas() {
        return czas;
    }

    public void setCzas(Integer czas) {
        this.czas = czas;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTokenCheck() {
        return tokenCheck;
    }

    public void setTokenCheck(String tokenCheck) {
        this.tokenCheck = tokenCheck;
    }
}