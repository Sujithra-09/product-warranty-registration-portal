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
                "LAP001",
                "Sujithra",
                "2026-01-01"
        );
    }

    @Test
    void createWarranty_shouldSaveWarranty() {
        when(repository.save(warranty)).thenReturn(warranty);

        WarrantyRegistration result = warrantyService.createWarranty(warranty);

        assertEquals(warranty, result);
        verify(repository).save(warranty);
    }

    @Test
    void getAllWarranties_shouldReturnAllWarranties() {
        when(repository.findAll()).thenReturn(List.of(warranty));

        List<WarrantyRegistration> result = warrantyService.getAllWarranties();

        assertEquals(1, result.size());
        assertEquals(warranty, result.get(0));
    }

    @Test
    void getWarrantyById_shouldReturnWarranty() {
        when(repository.findById(1L)).thenReturn(Optional.of(warranty));

        WarrantyRegistration result = warrantyService.getWarrantyById(1L);

        assertEquals(warranty, result);
    }

    @Test
    void getWarrantyById_shouldReturnNullWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        WarrantyRegistration result = warrantyService.getWarrantyById(1L);

        assertNull(result);
    }

    @Test
    void updateWarranty_shouldUpdateExistingWarranty() {
        when(repository.findById(1L)).thenReturn(Optional.of(warranty));
        when(repository.save(warranty)).thenReturn(warranty);

        WarrantyRegistration result = warrantyService.updateWarranty(
                1L,
                "Phone",
                "PH001",
                "Sujithra",
                "2026-02-01"
        );

        assertNotNull(result);
        assertEquals("Phone", warranty.getProductName());
        assertEquals("PH001", warranty.getProductId());
        assertEquals("2026-02-01", warranty.getPurchaseDate());

        verify(repository).save(warranty);
    }

    @Test
    void updateWarranty_shouldReturnNullWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        WarrantyRegistration result = warrantyService.updateWarranty(
                1L,
                "Phone",
                "PH001",
                "Sujithra",
                "2026-02-01"
        );

        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void deleteWarranty_shouldDeleteById() {
        warrantyService.deleteWarranty(1L);

        verify(repository).deleteById(1L);
    }
}