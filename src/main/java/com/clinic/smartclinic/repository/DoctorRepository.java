package com.clinic.smartclinic.repository;

import com.clinic.smartclinic.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialtyContainingIgnoreCase(String specialty);

    List<Doctor> findByNameContainingIgnoreCase(String name);

    List<Doctor> findBySpecialtyContainingIgnoreCaseAndNameContainingIgnoreCase(String specialty, String name);
}
