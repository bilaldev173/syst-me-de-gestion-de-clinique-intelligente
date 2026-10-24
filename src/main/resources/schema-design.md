# Smart Clinic Management System Database Schema

This document outlines a MySQL schema for a smart clinic management system with core clinical entities.

## 1) doctors
| Field | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique doctor identifier |
| name | VARCHAR(100) | NOT NULL | Doctor full name |
| specialty | VARCHAR(100) | NOT NULL | Specialization |
| email | VARCHAR(150) | UNIQUE, NOT NULL | Doctor email address |
| password | VARCHAR(255) | NOT NULL | Hashed password |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation time |

## 2) patients
| Field | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique patient identifier |
| name | VARCHAR(100) | NOT NULL | Patient full name |
| email | VARCHAR(150) | UNIQUE, NOT NULL | Patient email |
| phone_number | VARCHAR(20) | UNIQUE, NOT NULL | Patient phone number |
| date_of_birth | DATE | NULL | Date of birth |
| address | VARCHAR(255) | NULL | Patient address |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation time |

## 3) appointments
| Field | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique appointment ID |
| doctor_id | BIGINT | NOT NULL, FK -> doctors.id | Refers to assigned doctor |
| patient_id | BIGINT | NOT NULL, FK -> patients.id | Refers to patient |
| appointment_time | DATETIME | NOT NULL | Appointment date and time |
| status | ENUM('SCHEDULED','COMPLETED','CANCELLED') | DEFAULT 'SCHEDULED' | Appointment status |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation time |

Foreign keys:
- appointments.doctor_id -> doctors.id
- appointments.patient_id -> patients.id

## 4) prescriptions
| Field | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Unique prescription ID |
| appointment_id | BIGINT | NOT NULL, FK -> appointments.id | Related appointment |
| doctor_id | BIGINT | NOT NULL, FK -> doctors.id | Prescribing doctor |
| patient_id | BIGINT | NOT NULL, FK -> patients.id | Receiving patient |
| medication_name | VARCHAR(200) | NOT NULL | Medicine name |
| dosage | VARCHAR(100) | NOT NULL | Dose instructions |
| frequency | VARCHAR(100) | NULL | Frequency per day |
| notes | TEXT | NULL | Clinical notes |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Creation time |

Foreign keys:
- prescriptions.appointment_id -> appointments.id
- prescriptions.doctor_id -> doctors.id
- prescriptions.patient_id -> patients.id

## Relationship summary
- One doctor can have many appointments.
- One patient can have many appointments.
- One appointment can have many prescriptions.
- Each prescription is linked to a doctor and patient for traceability.

## Sample DDL
```sql
CREATE TABLE doctors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    date_of_birth DATE NULL,
    address VARCHAR(255) NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status ENUM('SCHEDULED','COMPLETED','CANCELLED') DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    medication_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NULL,
    notes TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);
```
