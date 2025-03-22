package com.wisesoft.btsfare.dto;

public class DiscountDTO {
    private Long id;
    private String typeId;
    private int discount;
    private String cutomerTypeName;
    
    public DiscountDTO(Long id, String typeId, int discount, String cutomerTypeName) {
        this.id = id;
        this.typeId = typeId;
        this.discount = discount;
        this.cutomerTypeName = cutomerTypeName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeId() {
        return typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public String getCutomerTypeName() {
        return cutomerTypeName;
    }
    public void setCutomerTypeName(String cutomerTypeName) {
        this.cutomerTypeName = cutomerTypeName;
    }

    

}
