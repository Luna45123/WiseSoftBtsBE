package com.wisesoft.btsfare.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.wisesoft.btsfare.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByType_CustomerType(String customerType);
}
