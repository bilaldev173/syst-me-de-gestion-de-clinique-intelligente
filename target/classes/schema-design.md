# Smart Clinic Management System Database Schema

This document describes the core MySQL schema for the Smart Clinic Management System.

## Tables

### 1. patients
| Column | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY AUTO_INCREMENT | Unique patient ID |
| name | VARCHAR(100) | NOT NULL | Patient full name |
| email | VARCHAR(150) | NOT NULL UNIQUE | Patient email |
| phone_number | VARCHAR(20) | NOT NULL UNIQUE | Patient phone number |
| password | VARCHAR(255) | NOT NULL | Hashed password |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation date |

### 2. doctors
| Column | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY AUTO_INCREMENT | Unique doctor ID |
| name | VARCHAR(100) | NOT NULL | Doctor full name |
| specialty | VARCHAR(100) | NOT NULL | Doctor specialization |
| email | VARCHAR(150) | NOT NULL UNIQUE | Doctor email |
| password | VARCHAR(255) | NOT NULL | Hashed password |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation date |

### 3. appointments
| Column | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY AUTO_INCREMENT | Unique appointment ID |
| doctor_id | BIGINT | NOT NULL, FK -> doctors.id | Doctor assigned |
| patient_id | BIGINT | NOT NULL, FK -> patients.id | Patient booking |
| appointment_time | DATETIME | NOT NULL | Appointment date and time |
| status | VARCHAR(30) | DEFAULT 'SCHEDULED' | Status: SCHEDULED, COMPLETED, CANCELLED |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation date |

### 4. prescriptions
| Column | Type | Constraints | Description |
| --- | --- | --- | --- |
| id | BIGINT | PRIMARY KEY AUTO_INCREMENT | Prescription ID |
| appointment_id | BIGINT | NOT NULL, FK -> appointments.id | Linked appointment |
| medication_name | VARCHAR(200) | NOT NULL | Medicine name |
| dosage | VARCHAR(100) | NOT NULL | Prescribed dosage |
| frequency | VARCHAR(100) | NOT NULL | Frequency of administration |
| notes | TEXT | NULL | Clinical notes |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Record creation date |

## Relationships
- Each patient may have many appointments.
- Each doctor may have many appointments.
- Each appointment may have one or more prescriptions.

## SQL DDL Example
```sql
CREATE TABLE patients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE doctors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialty VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    appointment_time DATETIME NOT NULL,
    status VARCHAR(30) DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patients(id)
);

CREATE TABLE prescriptions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    appointment_id BIGINT NOT NULL,
    medication_name VARCHAR(200) NOT NULL,
    dosage VARCHAR(100) NOT NULL,
    frequency VARCHAR(100) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prescription_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
```
