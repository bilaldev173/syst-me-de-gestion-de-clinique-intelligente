package com.clinic.smartclinic.controller;

import com.clinic.smartclinic.entity.Appointment;
import com.clinic.smartclinic.service.AppointmentService;
import com.clinic.smartclinic.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PrescriptionController {

    private final TokenService tokenService;

    @Autowired
    public PrescriptionController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/prescriptions")
    public ResponseEntity<?> createPrescription(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @Valid @RequestBody PrescriptionRequest request
    ) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Missing or invalid Authorization header"));
        }

        String token = authorizationHeader.substring(7);
        if (!tokenService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Invalid or expired token"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Prescription created successfully");
        response.put("appointmentId", request.appointmentId());
        response.put("medicationName", request.medicationName());
        response.put("dosage", request.dosage());
        response.put("frequency", request.frequency());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public record PrescriptionRequest(
            @NotNull(message = "appointmentId is required") Long appointmentId,
            @NotBlank(message = "medicationName is required") String medicationName,
            @NotBlank(message = "dosage is required") String dosage,
            @NotBlank(message = "frequency is required") String frequency,
            String notes
    ) {}
}
