package com.wisesoft.btsfare.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.wisesoft.btsfare.dto.BtsFareDTO;
import com.wisesoft.btsfare.dto.DiscountDTO;
import com.wisesoft.btsfare.entity.BtsFare;
import com.wisesoft.btsfare.entity.CustomerType;
import com.wisesoft.btsfare.entity.Discount;
import com.wisesoft.btsfare.mapper.BtsFareMapper;
import com.wisesoft.btsfare.repository.CustomerTypeRepository;
import com.wisesoft.btsfare.repository.DiscountRepository;
import com.wisesoft.btsfare.repository.FareRepository;

import jakarta.transaction.Transactional;

@Service
public class FareService {
    private final BtsFareMapper btsFareMapper;

    private final FareRepository fareRepository;

    private final DiscountRepository discountRepository;

    private final CustomerTypeRepository customerTypeRepository;

    public FareService(BtsFareMapper btsFareMapper, FareRepository fareRepository,
            DiscountRepository discountRepository,CustomerTypeRepository customerTypeRepository) {
        this.btsFareMapper = btsFareMapper;
        this.fareRepository = fareRepository;
        this.discountRepository = discountRepository;
        this.customerTypeRepository =customerTypeRepository;
    }

    public List<BtsFareDTO> getAllBtsFare() {
        List<BtsFare> btsFares = fareRepository.findAllOrderByNumberStation().get();
        List<BtsFareDTO> dtos = new ArrayList<BtsFareDTO>();
        btsFareMapper.updateStationFromEntity(btsFares, dtos);
        return dtos;
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
    public String updateDiscount(List<DiscountDTO> discountDTOList) {
        discountDTOList.stream().map(discountDto -> {
                        Optional<Discount> existingDiscountOpt = discountRepository.findById(discountDto.getId());

            Discount discount;

            if (existingDiscountOpt.isPresent()) {
                discount = existingDiscountOpt.get();
            }else{
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
            }else{
                discount.setDiscount(discountDto.getDiscount());
            }
            return discountRepository.save(discount);
        }).collect(Collectors.toList());

        return "";
    }
}
