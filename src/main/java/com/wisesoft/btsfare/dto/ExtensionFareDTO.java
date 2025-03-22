package com.wisesoft.btsfare.dto;

public class ExtensionFareDTO {
    private Long id;
    private String typeId;  // ใช้ typeId แทน Object CustomerType
    private double price;
    private String customerTypeName;
    
    public ExtensionFareDTO(Long id, String typeId, double price,String customerTypeName) {
        this.id = id;
        this.price = price;
        this.typeId = typeId;
        this.customerTypeName = customerTypeName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public String getCustomerTypeName() {
        return customerTypeName;
    }
    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }
 

    
}
