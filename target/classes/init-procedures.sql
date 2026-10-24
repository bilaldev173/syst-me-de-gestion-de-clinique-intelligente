-- MySQL script for Smart Clinic Management System database utilities
-- Example commands
SHOW TABLES;
SELECT * FROM patient LIMIT 5;

DELIMITER $$

CREATE PROCEDURE GetDailyAppointmentReportByDoctor(IN doctorId INT, IN reportDate DATE)
BEGIN
    SELECT
        a.id AS appointment_id,
        p.name AS patient_name,
        p.email AS patient_email,
        a.appointment_time,
        a.status
    FROM appointments a
    INNER JOIN patients p ON p.id = a.patient_id
    WHERE a.doctor_id = doctorId
      AND DATE(a.appointment_time) = reportDate
    ORDER BY a.appointment_time;
END$$

CREATE PROCEDURE GetDoctorWithMostPatientsByMonth(IN month INT, IN year INT)
BEGIN
    SELECT
        d.id,
        d.name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) AS patient_count
    FROM doctors d
    LEFT JOIN appointments a
        ON d.id = a.doctor_id
       AND MONTH(a.appointment_time) = month
       AND YEAR(a.appointment_time) = year
    GROUP BY d.id, d.name, d.specialty
    ORDER BY patient_count DESC, d.name ASC
    LIMIT 1;
END$$

CREATE PROCEDURE GetDoctorWithMostPatientsByYear(IN year INT)
BEGIN
    SELECT
        d.id,
        d.name,
        d.specialty,
        COUNT(DISTINCT a.patient_id) AS patient_count
    FROM doctors d
    LEFT JOIN appointments a
        ON d.id = a.doctor_id
       AND YEAR(a.appointment_time) = year
    GROUP BY d.id, d.name, d.specialty
    ORDER BY patient_count DESC, d.name ASC
    LIMIT 1;
END$$

DELIMITER ;
