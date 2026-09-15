package com.emergency.network.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="emergency_requests")
public class EmergencyRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String requesterName;
    private String requesterPhone;
    private String title;
    @Column(length=2000) private String description;
    @Enumerated(EnumType.STRING) private ResourceType resourceType;
    private int quantity;
    private String unit;
    private String urgency = "HIGH";
    private double latitude;
    private double longitude;
    private String address;
    @Enumerated(EnumType.STRING) private RequestStatus status = RequestStatus.OPEN;
    private Long assignedResourceId;
    private Long assignedVolunteerId;
    private LocalDateTime createdAt = LocalDateTime.now();

    public EmergencyRequest(){}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getRequesterName(){return requesterName;} public void setRequesterName(String v){requesterName=v;}
    public String getRequesterPhone(){return requesterPhone;} public void setRequesterPhone(String v){requesterPhone=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public ResourceType getResourceType(){return resourceType;} public void setResourceType(ResourceType v){resourceType=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
    public String getUrgency(){return urgency;} public void setUrgency(String v){urgency=v;}
    public double getLatitude(){return latitude;} public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;} public void setLongitude(double v){longitude=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public RequestStatus getStatus(){return status;} public void setStatus(RequestStatus v){status=v;}
    public Long getAssignedResourceId(){return assignedResourceId;} public void setAssignedResourceId(Long v){assignedResourceId=v;}
    public Long getAssignedVolunteerId(){return assignedVolunteerId;} public void setAssignedVolunteerId(Long v){assignedVolunteerId=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
