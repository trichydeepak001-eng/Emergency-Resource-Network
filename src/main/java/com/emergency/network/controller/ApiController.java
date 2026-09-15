package com.emergency.network.controller;

import com.emergency.network.model.*;
import com.emergency.network.repository.*;
import com.emergency.network.service.NetworkService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class ApiController {
    private final UserRepository users;
    private final ResourceRepository resources;
    private final EmergencyRequestRepository requests;
    private final AlertRepository alerts;
    private final NetworkService service;

    public ApiController(UserRepository users, ResourceRepository resources, EmergencyRequestRepository requests,
                         AlertRepository alerts, NetworkService service){
        this.users=users;this.resources=resources;this.requests=requests;this.alerts=alerts;this.service=service;
    }

    @GetMapping("/health") public Map<String,String> health(){return Map.of("status","UP","service","emergency-resource-network");}
    @GetMapping("/dashboard") public Map<String,Object> dashboard(){return service.dashboard();}
    @GetMapping("/resources") public List<Resource> resources(
            @RequestParam(required=false) ResourceType type,
            @RequestParam(required=false) ResourceStatus status){
        if(type!=null) return resources.findByType(type);
        if(status!=null) return resources.findByStatus(status);
        return resources.findAll();
    }
    @PostMapping("/resources") public ResponseEntity<Resource> addResource(@Valid @RequestBody Resource r){
        r.setId(null); if(r.getStatus()==null) r.setStatus(ResourceStatus.AVAILABLE); return ResponseEntity.ok(resources.save(r));
    }
    @PutMapping("/resources/{id}") public ResponseEntity<Resource> updateResource(@PathVariable Long id,@RequestBody Resource incoming){
        return resources.findById(id).map(r -> {
            r.setName(incoming.getName()); r.setType(incoming.getType()); r.setQuantity(incoming.getQuantity());
            r.setUnit(incoming.getUnit()); r.setProviderName(incoming.getProviderName()); r.setPhone(incoming.getPhone());
            r.setAddress(incoming.getAddress()); r.setLatitude(incoming.getLatitude()); r.setLongitude(incoming.getLongitude());
            r.setStatus(incoming.getStatus()); r.setVerified(incoming.isVerified()); return ResponseEntity.ok(resources.save(r));
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
    @DeleteMapping("/resources/{id}") public ResponseEntity<Void> deleteResource(@PathVariable Long id){
        if(!resources.existsById(id)) return ResponseEntity.notFound().build(); resources.deleteById(id); return ResponseEntity.noContent().build();
    }

    @GetMapping("/requests") public List<EmergencyRequest> allRequests(
            @RequestParam(required=false) RequestStatus status,
            @RequestParam(required=false) String phone){
        if(phone!=null && !phone.isBlank()) return requests.findByRequesterPhoneOrderByCreatedAtDesc(phone);
        if(status!=null) return requests.findByStatus(status);
        return requests.findAll();
    }
    @PostMapping("/requests") public ResponseEntity<EmergencyRequest> createRequest(@RequestBody EmergencyRequest q){
        q.setId(null); q.setStatus(RequestStatus.OPEN); return ResponseEntity.ok(requests.save(q));
    }
    @PatchMapping("/requests/{id}/status") public ResponseEntity<EmergencyRequest> requestStatus(@PathVariable Long id,@RequestParam RequestStatus status){
        return requests.findById(id).map(q->{q.setStatus(status); return ResponseEntity.ok(requests.save(q));})
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PostMapping("/requests/{requestId}/reserve/{resourceId}")
    public ResponseEntity<?> reserve(@PathVariable Long requestId,@PathVariable Long resourceId){
        return service.reserveResource(resourceId,requestId)
                .<ResponseEntity<?>>map(r->ResponseEntity.ok(Map.of("message","Resource matched successfully","resource",r)))
                .orElseGet(()->ResponseEntity.badRequest().body(Map.of("message","Resource unavailable or insufficient quantity")));
    }
    @PostMapping("/requests/{requestId}/assign/{volunteerId}")
    public ResponseEntity<?> assign(@PathVariable Long requestId,@PathVariable Long volunteerId){
        return service.assignVolunteer(requestId,volunteerId)
                .<ResponseEntity<?>>map(q->ResponseEntity.ok(q))
                .orElseGet(()->ResponseEntity.badRequest().body(Map.of("message","Volunteer unavailable or invalid")));
    }

    @GetMapping("/volunteers") public List<User> volunteers(){
        return users.findAll().stream().filter(u->"VOLUNTEER".equalsIgnoreCase(u.getRole())).toList();
    }
    @PostMapping("/volunteers") public ResponseEntity<User> registerVolunteer(@RequestBody User u){
        if(users.findByEmail(u.getEmail()).isPresent()) return ResponseEntity.status(HttpStatus.CONFLICT).build();
        u.setId(null);u.setRole("VOLUNTEER");u.setAvailable(true);return ResponseEntity.ok(users.save(u));
    }
    @PatchMapping("/volunteers/{id}/availability") public ResponseEntity<User> availability(@PathVariable Long id,@RequestParam boolean available){
        return users.findById(id).map(u->{u.setAvailable(available);return ResponseEntity.ok(users.save(u));})
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/alerts") public List<Alert> alerts(){return alerts.findByActiveTrueOrderByCreatedAtDesc();}
    @PostMapping("/alerts") public ResponseEntity<Alert> addAlert(@RequestBody Alert a){a.setId(null);a.setActive(true);return ResponseEntity.ok(alerts.save(a));}
    @PatchMapping("/alerts/{id}/close") public ResponseEntity<Alert> closeAlert(@PathVariable Long id){
        return alerts.findById(id).map(a->{a.setActive(false);return ResponseEntity.ok(alerts.save(a));})
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("/login") public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String email=body.get("email"), password=body.get("password");
        return users.findByEmail(email).filter(u->Objects.equals(u.getPassword(),password))
                .<ResponseEntity<?>>map(u->ResponseEntity.ok(Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole())))
                .orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","Invalid email or password")));
    }
}
