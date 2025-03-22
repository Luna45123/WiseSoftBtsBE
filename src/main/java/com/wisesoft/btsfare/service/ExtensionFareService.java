package com.wisesoft.btsfare.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.wisesoft.btsfare.dto.ExtensionFareDTO;
import com.wisesoft.btsfare.entity.CustomerType;
import com.wisesoft.btsfare.entity.ExtensionFare;
import com.wisesoft.btsfare.repository.CustomerTypeRepository;
import com.wisesoft.btsfare.repository.ExtensionFareRepository;

import jakarta.transaction.Transactional;

@Service
public class ExtensionFareService {

    private final ExtensionFareRepository extensionFareRepository;

    private final CustomerTypeRepository customerTypeRepository;

  

    public ExtensionFareService(ExtensionFareRepository extensionFareRepository,
            CustomerTypeRepository customerTypeRepository) {
        this.extensionFareRepository = extensionFareRepository;
        this.customerTypeRepository = customerTypeRepository;
    }

    public List<ExtensionFareDTO> getAllExtensionFares() {
        return extensionFareRepository.findAll()
                .stream()
                .map(fare -> new ExtensionFareDTO(
                        fare.getId(),
                        fare.getType().getTypeId(),
                        fare.getPrice(),
                        fare.getType().getCustomerType()))
                .collect(Collectors.toList());

    }

    @Transactional
    public String updateExtensionFare(List<ExtensionFareDTO> fareDTOList) {
        fareDTOList.stream().map(fareDto -> {
            Optional<ExtensionFare> existingFareOpt = extensionFareRepository.findById(fareDto.getId());

            ExtensionFare fare;
            if (existingFareOpt.isPresent()) {
                fare = existingFareOpt.get();
            } else {
                fare = new ExtensionFare();
            }

         
            Optional<CustomerType> customerTypeOpt = customerTypeRepository.findByTypeId(fareDto.getTypeId());

            if (customerTypeOpt.isEmpty()) {
                CustomerType newCustomerType = new CustomerType();
                newCustomerType.setTypeId(fareDto.getTypeId());
                newCustomerType.setCustomerType(fareDto.getCustomerTypeName());
                customerTypeRepository.save(newCustomerType);
                fare.setType(newCustomerType);
            } else {
                fare.setType(customerTypeOpt.get());
            }

            fare.setPrice(fareDto.getPrice());

            return extensionFareRepository.save(fare);
        }).collect(Collectors.toList());

        return "";
    }

}
