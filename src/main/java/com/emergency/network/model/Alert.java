package com.emergency.network.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="alerts")
public class Alert {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length=2000) private String message;
    private String severity = "HIGH";
    private String area;
    private boolean active = true;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Alert(){}
    public Alert(String title,String message,String severity,String area){
        this.title=title;this.message=message;this.severity=severity;this.area=area;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getMessage(){return message;} public void setMessage(String v){message=v;}
    public String getSeverity(){return severity;} public void setSeverity(String v){severity=v;}
    public String getArea(){return area;} public void setArea(String v){area=v;}
    public boolean isActive(){return active;} public void setActive(boolean v){active=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
