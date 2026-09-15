package com.emergency.network.repository;
import com.emergency.network.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ResourceRepository extends JpaRepository<Resource,Long>{
    List<Resource> findByStatus(ResourceStatus status);
    List<Resource> findByType(ResourceType type);
}
