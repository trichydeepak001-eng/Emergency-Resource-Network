package com.emergency.network.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="resources")
public class Resource {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;
    @Enumerated(EnumType.STRING) private ResourceType type;
    private int quantity;
    private String unit;
    private String providerName;
    private String phone;
    private String address;
    private double latitude;
    private double longitude;
    @Enumerated(EnumType.STRING) private ResourceStatus status = ResourceStatus.AVAILABLE;
    private boolean verified;

    public Resource(){}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public ResourceType getType(){return type;} public void setType(ResourceType v){type=v;}
    public int getQuantity(){return quantity;} public void setQuantity(int v){quantity=v;}
    public String getUnit(){return unit;} public void setUnit(String v){unit=v;}
    public String getProviderName(){return providerName;} public void setProviderName(String v){providerName=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public String getAddress(){return address;} public void setAddress(String v){address=v;}
    public double getLatitude(){return latitude;} public void setLatitude(double v){latitude=v;}
    public double getLongitude(){return longitude;} public void setLongitude(double v){longitude=v;}
    public ResourceStatus getStatus(){return status;} public void setStatus(ResourceStatus v){status=v;}
    public boolean isVerified(){return verified;} public void setVerified(boolean v){verified=v;}
}
