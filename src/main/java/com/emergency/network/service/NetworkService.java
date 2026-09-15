package com.emergency.network.service;

import com.emergency.network.model.*;
import com.emergency.network.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NetworkService {
    private final ResourceRepository resources;
    private final EmergencyRequestRepository requests;
    private final UserRepository users;

    public NetworkService(ResourceRepository resources, EmergencyRequestRepository requests, UserRepository users){
        this.resources=resources; this.requests=requests; this.users=users;
    }

    public Map<String,Object> dashboard(){
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("resources", resources.count());
        m.put("availableResources", resources.findByStatus(ResourceStatus.AVAILABLE).size());
        m.put("openRequests", requests.findByStatus(RequestStatus.OPEN).size());
        m.put("volunteers", users.countByAvailableTrue());
        return m;
    }

    public Optional<Resource> reserveResource(Long resourceId, Long requestId){
        Optional<Resource> ro=resources.findById(resourceId);
        Optional<EmergencyRequest> qo=requests.findById(requestId);
        if(ro.isEmpty() || qo.isEmpty()) return Optional.empty();
        Resource r=ro.get(); EmergencyRequest q=qo.get();
        if(r.getStatus()!=ResourceStatus.AVAILABLE || r.getQuantity()<q.getQuantity()) return Optional.empty();
        r.setQuantity(r.getQuantity()-q.getQuantity());
        if(r.getQuantity()==0) r.setStatus(ResourceStatus.EXHAUSTED);
        q.setAssignedResourceId(r.getId());
        q.setStatus(RequestStatus.MATCHED);
        resources.save(r); requests.save(q);
        return Optional.of(r);
    }

    public Optional<EmergencyRequest> assignVolunteer(Long requestId, Long volunteerId){
        Optional<EmergencyRequest> qo=requests.findById(requestId);
        Optional<User> uo=users.findById(volunteerId);
        if(qo.isEmpty() || uo.isEmpty()) return Optional.empty();
        EmergencyRequest q=qo.get(); User u=uo.get();
        if(!"VOLUNTEER".equalsIgnoreCase(u.getRole()) || !u.isAvailable()) return Optional.empty();
        q.setAssignedVolunteerId(u.getId());
        q.setStatus(RequestStatus.IN_PROGRESS);
        u.setAvailable(false);
        users.save(u); requests.save(q);
        return Optional.of(q);
    }
}
