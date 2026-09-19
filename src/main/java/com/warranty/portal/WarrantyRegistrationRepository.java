package com.warranty.portal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarrantyRegistrationRepository
        extends JpaRepository<WarrantyRegistration, Long> {
}