package com.emergency.network.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;
    @NotBlank @Column(unique=true) private String email;
    @NotBlank private String phone;
    private String password;
    private String role = "USER";
    private String skills;
    private boolean available = true;
    private double latitude;
    private double longitude;

    public User() {}
    public User(String name, String email, String phone, String password, String role) {
        this.name=name; this.email=email; this.phone=phone; this.password=password; this.role=role;
    }

    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getRole(){return role;} public void setRole(String v){role=v;}
    public String getSkills(){return skills;} public void setSkills(String v){skills=v;}
    public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
    public double getLatitude(){return latitude;} public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;} public void setLongitude(double v){longitude=v;}
}
