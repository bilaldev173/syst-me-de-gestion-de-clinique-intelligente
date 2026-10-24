package com.clinic.smartclinic.controller;

import com.clinic.smartclinic.entity.Doctor;
import com.clinic.smartclinic.service.DoctorService;
import com.clinic.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final TokenService tokenService;

    @Autowired
    public DoctorController(DoctorService doctorService, TokenService tokenService) {
        this.doctorService = doctorService;
        this.tokenService = tokenService;
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getDoctorAvailability(
            @PathVariable Long id,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
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

        List<?> availableTimes = doctorService.getDoctorAvailability(id, date);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("doctorId", id);
        response.put("date", date);
        response.put("availableTimes", availableTimes);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDoctors(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String name,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader
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

        List<Doctor> doctors = doctorService.searchDoctors(specialty, name);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("count", doctors.size());
        response.put("doctors", doctors);
        return ResponseEntity.ok(response);
    }
}
