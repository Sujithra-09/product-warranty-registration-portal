package com.warranty.portal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WarrantyService {

    @Autowired
    private WarrantyRegistrationRepository repository;

    public WarrantyRegistration createWarranty(WarrantyRegistration warranty) {
        return repository.save(warranty);
    }

    public List<WarrantyRegistration> getAllWarranties() {
        return repository.findAll();
    }

    public WarrantyRegistration getWarrantyById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public WarrantyRegistration updateWarranty(
            Long id,
            String productName,
            String productId,
            String customerName,
            String purchaseDate) {

        WarrantyRegistration warranty = repository.findById(id).orElse(null);

        if (warranty != null) {
            warranty.setProductName(productName);
            warranty.setProductId(productId);
            warranty.setCustomerName(customerName);
            warranty.setPurchaseDate(purchaseDate);

            return repository.save(warranty);
        }

        return null;
    }

    public void deleteWarranty(Long id) {
        repository.deleteById(id);
    }
}