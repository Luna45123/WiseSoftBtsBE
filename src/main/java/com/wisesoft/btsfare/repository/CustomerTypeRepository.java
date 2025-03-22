package com.wisesoft.btsfare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wisesoft.btsfare.entity.CustomerType;

public interface CustomerTypeRepository extends JpaRepository<CustomerType,Long>{

    Optional<CustomerType> findByTypeId(String typeId);

}
