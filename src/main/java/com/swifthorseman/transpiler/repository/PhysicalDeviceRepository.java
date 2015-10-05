package com.swifthorseman.transpiler.repository;

import com.swifthorseman.transpiler.domain.PhysicalDevice;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PhysicalDevice entity.
 */
public interface PhysicalDeviceRepository extends JpaRepository<PhysicalDevice,Long> {

}
