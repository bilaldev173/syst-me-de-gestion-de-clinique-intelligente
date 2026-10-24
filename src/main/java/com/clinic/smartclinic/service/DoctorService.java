package com.clinic.smartclinic.service;

import com.clinic.smartclinic.entity.Doctor;
import com.clinic.smartclinic.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<LocalTime> getDoctorAvailability(Long doctorId, LocalDate date) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Doctor doctor = doctorOpt.get();
        if (doctor.getAvailableTimes() == null || doctor.getAvailableTimes().isEmpty()) {
            return Collections.emptyList();
        }

        return doctor.getAvailableTimes();
    }

    public boolean validateDoctorCredentials(String email, String password) {
        Optional<Doctor> doctorOpt = doctorRepository.findByEmail(email);
        if (doctorOpt.isEmpty()) {
            return false;
        }

        return doctorOpt.get().getPassword().equals(password);
    }

    public List<Doctor> searchDoctors(String specialty, String name) {
        if (specialty != null && !specialty.isBlank() && name != null && !name.isBlank()) {
            return doctorRepository.findBySpecialtyContainingIgnoreCaseAndNameContainingIgnoreCase(specialty, name);
        }

        if (specialty != null && !specialty.isBlank()) {
            return doctorRepository.findBySpecialtyContainingIgnoreCase(specialty);
        }

        if (name != null && !name.isBlank()) {
            return doctorRepository.findByNameContainingIgnoreCase(name);
        }

        return doctorRepository.findAll();
    }
}
