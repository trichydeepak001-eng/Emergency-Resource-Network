package com.emergency.network.repository;
import com.emergency.network.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest,Long>{
    List<EmergencyRequest> findByStatus(RequestStatus status);
    List<EmergencyRequest> findByRequesterPhoneOrderByCreatedAtDesc(String phone);
}
