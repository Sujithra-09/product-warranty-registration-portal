package com.warranty.portal;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class LoginController {

    @Autowired
    private WarrantyService warrantyService;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Value("${ADMIN_PASSWORD_HASH}")
    private String adminPasswordHash;

    @Value("${USER_PASSWORD_HASH}")
    private String userPasswordHash;

    // Login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Login
    @PostMapping("/login")
    public String login(
            @RequestParam String loginId,
            @RequestParam String password,
            HttpSession session) {

        // Admin login
        if ("admin".equals(loginId)
                && passwordEncoder.matches(password, adminPasswordHash)) {

            session.setAttribute("role", "ADMIN");
            return "admin-dashboard";
        }

        // User login
        if ("user".equals(loginId)
                && passwordEncoder.matches(password, userPasswordHash)) {

            session.setAttribute("role", "USER");
            return "registration";
        }

        // Invalid login
        return "login";
    }

    // Admin dashboard
    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        return "admin-dashboard";
    }

    // User registration
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute WarrantyRegistrationRequest request,
            BindingResult result) {

        if (result.hasErrors()) {
            return "registration";
        }

        WarrantyRegistration warranty =
                new WarrantyRegistration(
                        request.getProductName(),
                        request.getProductId(),
                        request.getCustomerName(),
                        request.getPurchaseDate()
                );

        warrantyService.createWarranty(warranty);

        return "registration-success";
    }

    // View warranties
    @GetMapping("/warranties")
    public String viewWarranties(
            Model model,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        model.addAttribute(
                "warranties",
                warrantyService.getAllWarranties()
        );

        return "warranty-list";
    }

    // Update selection
    @GetMapping("/update-selection")
    public String updateSelection(
            Model model,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        model.addAttribute(
                "warranties",
                warrantyService.getAllWarranties()
        );

        return "update-selection";
    }

    // Delete selection
    @GetMapping("/delete-selection")
    public String deleteSelection(
            Model model,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        model.addAttribute(
                "warranties",
                warrantyService.getAllWarranties()
        );

        return "delete-selection";
    }

    // Update warranty page
    @GetMapping("/update-warranty")
    public String updateWarrantyPage(
            @RequestParam Long id,
            Model model,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        WarrantyRegistration warranty =
                warrantyService.getWarrantyById(id);

        model.addAttribute("warranty", warranty);

        return "update-warranty";
    }

    // Update warranty
    @PostMapping("/update-warranty")
    public String updateWarranty(
            @RequestParam Long id,
            @RequestParam String productName,
            @RequestParam String productId,
            @RequestParam String customerName,
            @RequestParam String purchaseDate,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        warrantyService.updateWarranty(
                id,
                productName,
                productId,
                customerName,
                purchaseDate
        );

        return "redirect:/warranties";
    }

    // Delete warranty
    @PostMapping("/delete-warranty")
    public String deleteWarranty(
            @RequestParam Long id,
            HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        warrantyService.deleteWarranty(id);

        return "redirect:/warranties";
    }

    // Health check
    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }
}
