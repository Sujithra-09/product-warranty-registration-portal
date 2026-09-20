package com.warranty.portal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

class LoginControllerTest {

    private LoginController controller;
    private WarrantyService warrantyService;
    private HttpSession session;
    private Model model;

    @BeforeEach
    void setUp() {
        controller = new LoginController();

        warrantyService = mock(WarrantyService.class);
        session = mock(HttpSession.class);
        model = mock(Model.class);

        // Inject WarrantyService manually
        try {
            var field = LoginController.class
                    .getDeclaredField("warrantyService");
            field.setAccessible(true);
            field.set(controller, warrantyService);

            var adminField = LoginController.class
                    .getDeclaredField("adminPasswordHash");
            adminField.setAccessible(true);
            adminField.set(controller,
                    "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");

            var userField = LoginController.class
                    .getDeclaredField("userPasswordHash");
            userField.setAccessible(true);
            userField.set(controller,
                    "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testLoginPage() {
        assertEquals("login", controller.loginPage());
    }

    @Test
    void testAdminDashboardWithoutAdminRole() {
        when(session.getAttribute("role")).thenReturn("USER");

        assertEquals("login", controller.adminDashboard(session));
    }

    @Test
    void testAdminDashboardWithAdminRole() {
        when(session.getAttribute("role")).thenReturn("ADMIN");

        assertEquals("admin-dashboard",
                controller.adminDashboard(session));
    }

    @Test
    void testHealth() {
        assertEquals("OK", controller.health());
    }

    @Test
    void testRegisterWithErrors() {
        WarrantyRegistrationRequest request =
                new WarrantyRegistrationRequest();

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        assertEquals(
                "registration",
                controller.register(request, result)
        );

        verify(warrantyService, never())
                .createWarranty(any());
    }

    @Test
    void testRegisterSuccessfully() {
        WarrantyRegistrationRequest request =
                new WarrantyRegistrationRequest();

        request.setProductName("Laptop");
        request.setProductId("P001");
        request.setCustomerName("Sujithra");
        request.setPurchaseDate("2026-09-20");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        assertEquals(
                "registration-success",
                controller.register(request, result)
        );

        verify(warrantyService)
                .createWarranty(any(WarrantyRegistration.class));
    }

    @Test
    void testViewWarrantiesWithoutAdmin() {
        when(session.getAttribute("role")).thenReturn("USER");

        assertEquals(
                "login",
                controller.viewWarranties(model, session)
        );
    }

    @Test
    void testViewWarrantiesAsAdmin() {
        when(session.getAttribute("role")).thenReturn("ADMIN");

        when(warrantyService.getAllWarranties())
                .thenReturn(java.util.List.of());

        assertEquals(
                "warranty-list",
                controller.viewWarranties(model, session)
        );

        verify(model).addAttribute(
                eq("warranties"),
                any()
        );
    }

    @Test
    void testUpdateSelectionAsAdmin() {
        when(session.getAttribute("role")).thenReturn("ADMIN");

        when(warrantyService.getAllWarranties())
                .thenReturn(java.util.List.of());

        assertEquals(
                "update-selection",
                controller.updateSelection(model, session)
        );
    }

    @Test
    void testDeleteSelectionAsAdmin() {
        when(session.getAttribute("role")).thenReturn("ADMIN");

        when(warrantyService.getAllWarranties())
                .thenReturn(java.util.List.of());

        assertEquals(
                "delete-selection",
                controller.deleteSelection(model, session)
        );
    }

    @Test
    void testUpdateWarrantyWithoutAdmin() {
        when(session.getAttribute("role")).thenReturn("USER");

        assertEquals(
                "login",
                controller.updateWarrantyPage(1L, model, session)
        );
    }

    @Test
    void testDeleteWarrantyWithoutAdmin() {
        when(session.getAttribute("role")).thenReturn("USER");

        assertEquals(
                "login",
                controller.deleteWarranty(1L, session)
        );
    }
}