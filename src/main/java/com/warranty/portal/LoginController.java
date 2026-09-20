package com.warranty.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;

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
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String loginId,
            @RequestParam String password,
            HttpSession session) {

        if (loginId.equals("admin")
                && !adminPasswordHash.isEmpty()
                && passwordEncoder.matches(password, adminPasswordHash)) {

            session.setAttribute("role", "ADMIN");
            return "admin-dashboard";
        }

        if (loginId.equals("user")
                && !userPasswordHash.isEmpty()
                && passwordEncoder.matches(password, userPasswordHash)) {

            session.setAttribute("role", "USER");
            return "registration";
        }

        return "login";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "login";
        }

        return "admin-dashboard";
    }

    @PostMapping("/register")
public String register(
        @Valid @ModelAttribute WarrantyRegistrationRequest request,
        org.springframework.validation.BindingResult result) {

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

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "OK";
    }

}