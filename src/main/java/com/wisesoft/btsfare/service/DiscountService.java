package com.wisesoft.btsfare.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.dto.DiscountDTO;
import com.wisesoft.btsfare.entity.CustomerType;
import com.wisesoft.btsfare.entity.Discount;
import com.wisesoft.btsfare.repository.CustomerTypeRepository;
import com.wisesoft.btsfare.repository.DiscountRepository;

import jakarta.transaction.Transactional;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;

    private final CustomerTypeRepository customerTypeRepository;

    

    public DiscountService(DiscountRepository discountRepository, CustomerTypeRepository customerTypeRepository) {
        this.discountRepository = discountRepository;
        this.customerTypeRepository = customerTypeRepository;
    }

    public List<DiscountDTO> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(discount -> new DiscountDTO(
                        discount.getId(),
                        discount.getType().getTypeId(),
                        discount.getDiscount(),
                        discount.getType().getCustomerType())
                ).collect(Collectors.toList());
               

    }

    @Transactional
    public void updateDiscount(List<DiscountDTO> discountDTOList) {
        for (DiscountDTO discountDto : discountDTOList) {
            Optional<Discount> existingDiscountOpt = discountRepository.findById(discountDto.getId());
    
            Discount discount;
            if (existingDiscountOpt.isPresent()) {
                discount = existingDiscountOpt.get();
            } else {
                discount = new Discount();
            }
    
            Optional<CustomerType> customerTypeOpt = customerTypeRepository.findByTypeId(discountDto.getTypeId());
            if (customerTypeOpt.isEmpty()) {
                CustomerType newCustomerType = new CustomerType();
                newCustomerType.setTypeId(discountDto.getTypeId());
                newCustomerType.setCustomerType(discountDto.getCutomerTypeName());
                customerTypeRepository.save(newCustomerType);
                discount.setType(newCustomerType);
            } else {
                discount.setType(customerTypeOpt.get());
            }
    
            if (discountDto.getDiscount() > 100) {
                discount.setDiscount(100);
            } else {
                discount.setDiscount(discountDto.getDiscount());
            }
    
            discountRepository.save(discount);
        }
    }
    
}
