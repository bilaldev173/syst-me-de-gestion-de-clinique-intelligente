package com.clinic.smartclinic.controller;

import com.clinic.smartclinic.entity.Appointment;
import com.clinic.smartclinic.service.AppointmentService;
import com.clinic.smartclinic.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final TokenService tokenService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, TokenService tokenService) {
        this.appointmentService = appointmentService;
        this.tokenService = tokenService;
    }

    @GetMapping("/patients/{patientId}/appointments")
    public ResponseEntity<?> getPatientAppointments(
            @PathVariable Long patientId,
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

        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patientId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("patientId", patientId);
        response.put("appointments", appointments);

        return ResponseEntity.ok(response);
    }
}
