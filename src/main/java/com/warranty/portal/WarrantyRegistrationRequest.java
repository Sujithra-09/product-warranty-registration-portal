package com.warranty.portal;

import jakarta.validation.constraints.NotBlank;

public class WarrantyRegistrationRequest {

    @NotBlank(message = "Product name is required")
    private String productName;

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Purchase date is required")
    private String purchaseDate;

    public String getProductName() {
        return productName;
    }

    public String getProductId() {
        return productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
    public void setProductName(String productName) {
    this.productName = productName;
}

public void setProductId(String productId) {
    this.productId = productId;
}

public void setCustomerName(String customerName) {
    this.customerName = customerName;
}

public void setPurchaseDate(String purchaseDate) {
    this.purchaseDate = purchaseDate;
}
}