package com.warranty.portal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WarrantyServiceTest {

    @Mock
    private WarrantyRegistrationRepository repository;

    @InjectMocks
    private WarrantyService warrantyService;

    private WarrantyRegistration warranty;

    @BeforeEach
    void setUp() {
        warranty = new WarrantyRegistration(
                "Laptop",
                "P001",
                "Sujithra",
                "2026-09-20"
        );
    }

    @Test
    void testCreateWarranty() {
        when(repository.save(warranty)).thenReturn(warranty);

        WarrantyRegistration result =
                warrantyService.createWarranty(warranty);

        assertEquals(warranty, result);
        verify(repository).save(warranty);
    }

    @Test
    void testGetAllWarranties() {
        when(repository.findAll()).thenReturn(List.of(warranty));

        List<WarrantyRegistration> result =
                warrantyService.getAllWarranties();

        assertEquals(1, result.size());
        assertEquals(warranty, result.get(0));
        verify(repository).findAll();
    }

    @Test
    void testGetWarrantyById() {
        when(repository.findById(1L)).thenReturn(Optional.of(warranty));

        WarrantyRegistration result =
                warrantyService.getWarrantyById(1L);

        assertEquals(warranty, result);
        verify(repository).findById(1L);
    }

    @Test
    void testGetWarrantyByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        WarrantyRegistration result =
                warrantyService.getWarrantyById(1L);

        assertNull(result);
    }

    @Test
    void testUpdateWarranty() {
        when(repository.findById(1L)).thenReturn(Optional.of(warranty));
        when(repository.save(warranty)).thenReturn(warranty);

        WarrantyRegistration result =
                warrantyService.updateWarranty(
                        1L,
                        "Mobile",
                        "P002",
                        "Sujithra Updated",
                        "2026-10-01"
                );

        assertEquals("Mobile", result.getProductName());
        assertEquals("P002", result.getProductId());
        assertEquals("Sujithra Updated", result.getCustomerName());
        assertEquals("2026-10-01", result.getPurchaseDate());

        verify(repository).save(warranty);
    }

    @Test
    void testUpdateWarrantyNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        WarrantyRegistration result =
                warrantyService.updateWarranty(
                        1L,
                        "Mobile",
                        "P002",
                        "Sujithra",
                        "2026-10-01"
                );

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void testDeleteWarranty() {
        warrantyService.deleteWarranty(1L);

        verify(repository).deleteById(1L);
    }
}