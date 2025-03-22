package com.wisesoft.btsfare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisesoft.btsfare.entity.ExtensionFare;

public interface ExtensionFareRepository extends JpaRepository<ExtensionFare, Long>{
    Optional<ExtensionFare> findByType_CustomerType(String customerType);

    Optional<ExtensionFare> findByType_TypeId(String typeId);
}
