package com.warranty.portal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class WarrantyRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String productName;

    @NotBlank
    private String productId;

    @NotBlank
    private String customerName;

    @NotBlank
    private String purchaseDate;

    public WarrantyRegistration() {
    }

    public WarrantyRegistration(String productName, String productId,
                                String customerName, String purchaseDate) {
        this.productName = productName;
        this.productId = productId;
        this.customerName = customerName;
        this.purchaseDate = purchaseDate;
    }

    public Long getId() {
        return id;
    }

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

    // Warranty expires 1 year after purchase
    public String getExpiryDate() {
        LocalDate purchase = LocalDate.parse(
                purchaseDate,
                DateTimeFormatter.ISO_LOCAL_DATE
        );

        return purchase.plusYears(1).toString();
    }

    // Warranty status
    public String getWarrantyStatus() {
        LocalDate today = LocalDate.now();

        LocalDate expiry = LocalDate.parse(
                getExpiryDate(),
                DateTimeFormatter.ISO_LOCAL_DATE
        );

        if (today.isAfter(expiry)) {
            return "Expired";
        }

        long daysLeft = ChronoUnit.DAYS.between(today, expiry);

        if (daysLeft <= 30) {
            return "Expiring Soon";
        }

        return "Active";
    }

    // Setters for updating warranty details

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
