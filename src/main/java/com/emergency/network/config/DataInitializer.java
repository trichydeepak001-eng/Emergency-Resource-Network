package com.emergency.network.config;

import com.emergency.network.model.*;
import com.emergency.network.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(UserRepository users, ResourceRepository resources, AlertRepository alerts){
        return args -> {
            if(users.count()==0){
                User admin=new User("System Admin","admin@emergency.local","9999999999","admin123","ADMIN");
                users.save(admin);
                User v1=new User("Arun Volunteer","volunteer1@emergency.local","9000000001","1234","VOLUNTEER");
                v1.setSkills("First Aid,Driving,Medical Support"); v1.setLatitude(10.7905); v1.setLongitude(78.7047);
                users.save(v1);
                User v2=new User("Priya Volunteer","volunteer2@emergency.local","9000000002","1234","VOLUNTEER");
                v2.setSkills("Food Distribution,First Aid"); v2.setLatitude(10.8050); v2.setLongitude(78.6850);
                users.save(v2);
            }
            if(resources.count()==0){
                Resource r1=new Resource(); r1.setName("O+ Blood Units"); r1.setType(ResourceType.BLOOD);
                r1.setQuantity(18); r1.setUnit("units"); r1.setProviderName("City Blood Bank"); r1.setPhone("9000011111");
                r1.setAddress("Central Medical Center"); r1.setLatitude(10.7905); r1.setLongitude(78.7047); r1.setVerified(true);
                resources.save(r1);
                Resource r2=new Resource(); r2.setName("Drinking Water"); r2.setType(ResourceType.WATER);
                r2.setQuantity(500); r2.setUnit("litres"); r2.setProviderName("Relief Hub"); r2.setPhone("9000022222");
                r2.setAddress("North Relief Camp"); r2.setLatitude(10.8050); r2.setLongitude(78.6850); r2.setVerified(true);
                resources.save(r2);
                Resource r3=new Resource(); r3.setName("Emergency Food Packs"); r3.setType(ResourceType.FOOD);
                r3.setQuantity(100); r3.setUnit("packs"); r3.setProviderName("Community Kitchen"); r3.setPhone("9000033333");
                r3.setAddress("West Community Hall"); r3.setLatitude(10.7750); r3.setLongitude(78.7200); r3.setVerified(true);
                resources.save(r3);
            }
            if(alerts.count()==0){
                alerts.save(new Alert("Emergency Network Online","Verified resources and volunteers are available. Create a request if immediate assistance is required.","HIGH","Trichy"));
            }
        };
    }
}
