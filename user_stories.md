# User Stories for Smart Clinic Management System

This document defines the core user stories for the Smart Clinic Management System for three main actors: Admin, Doctor, and Patient.

## Admin User Stories

1. As an Admin, I want to log in securely to the system so that only authorized staff can manage clinic data.
2. As an Admin, I want to add a doctor to the system so that the clinic can maintain an updated doctor list.
3. As an Admin, I want to view all doctors so that I can manage specialties and schedules effectively.
4. As an Admin, I want to view all patient appointments so that I can monitor clinic operations.
5. As an Admin, I want to manage patient records so that the clinic can keep accurate information.
6. As an Admin, I want to assign or update doctor availability so that patients can book valid appointments.
7. As an Admin, I want to ensure prescriptions are tied to valid appointments so that patient treatment records remain accurate.
8. As an Admin, I want to generate daily or monthly clinic reports so that I can evaluate doctor productivity and patient activity.

## Doctor User Stories

1. As a Doctor, I want to log in to the system so that I can access my personal schedule and patient data.
2. As a Doctor, I want to view my appointments for a selected day so that I can prepare for consultations.
3. As a Doctor, I want to search for patients by name or email so that I can quickly access their profiles.
4. As a Doctor, I want to create a prescription for a patient so that the treatment plan is documented clearly.
5. As a Doctor, I want to check my available consultation times so that I can inform patients accurately.
6. As a Doctor, I want to see my scheduled appointments by date so that I can organize my working day.
7. As a Doctor, I want to update appointment status so that the patient and admin have current information.
8. As a Doctor, I want to review past prescriptions so that I can track medication history.

## Patient User Stories

1. As a Patient, I want to register an account so that I can access clinic services online.
2. As a Patient, I want to log in securely so that I can manage my appointments and profile.
3. As a Patient, I want to search for a doctor by name or specialty so that I can choose the right clinician.
4. As a Patient, I want to view doctor availability so that I can select a convenient time.
5. As a Patient, I want to book an appointment so that I can receive medical assistance.
6. As a Patient, I want to view my booked appointments so that I can keep track of my schedule.
7. As a Patient, I want to receive confirmation of my appointment booking so that I know my request was successful.
8. As a Patient, I want to view my prescription history so that I can follow treatment instructions.

## Acceptance Criteria Summary

- Authentication must be secure and role-based for Admin, Doctor, and Patient.
- Doctors must have specialties and availability records.
- Patients must be able to search doctors and book appointments.
- Appointments must be linked to valid doctor and patient entities.
- Prescriptions must be associated with valid appointments.
- Core clinic data must be stored in MySQL using normalized tables.

## Priority

- High Priority: Login, doctor management, appointment booking, prescription creation
- Medium Priority: Reporting, search filters, appointment status updates
- Low Priority: UI enhancements, analytics dashboards

## Related Entities

- Patient
- Doctor
- Appointment
- Prescription

## Notes

These user stories form the basis of the Smart Clinic Management System backend, frontend, and database design.
