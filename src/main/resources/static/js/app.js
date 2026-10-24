document.addEventListener('DOMContentLoaded', () => {
    const adminLoginForm = document.getElementById('admin-login-form');
    const doctorForm = document.getElementById('doctor-form');
    const doctorLoginForm = document.getElementById('doctor-login-form');
    const patientLoginForm = document.getElementById('patient-login-form');
    const doctorSearchForm = document.getElementById('doctor-search-form');

    if (adminLoginForm) {
        adminLoginForm.addEventListener('submit', (event) => {
            event.preventDefault();
            alert('Admin login submitted. Connect this form to your backend authentication API.');
        });
    }

    if (doctorForm) {
        doctorForm.addEventListener('submit', (event) => {
            event.preventDefault();
            alert('Doctor form submitted. Connect with /api/doctors/create or your admin API endpoint.');
        });
    }

    if (doctorLoginForm) {
        doctorLoginForm.addEventListener('submit', (event) => {
            event.preventDefault();
            alert('Doctor login submitted. Connect this form to your JWT authentication endpoint.');
        });
    }

    if (patientLoginForm) {
        patientLoginForm.addEventListener('submit', (event) => {
            event.preventDefault();
            alert('Patient login submitted. Connect this form to your patient auth endpoint.');
        });
    }

    if (doctorSearchForm) {
        doctorSearchForm.addEventListener('submit', (event) => {
            event.preventDefault();
            const name = document.getElementById('search-name').value.trim();
            const specialty = document.getElementById('search-specialty').value.trim();
            const resultBox = document.getElementById('doctor-search-results');

            resultBox.innerHTML = `
                <div class="result-item">
                    <strong>Search criteria:</strong><br>
                    Name: ${name || 'Any'}<br>
                    Specialty: ${specialty || 'Any'}
                </div>
                <div class="result-item">This search is ready to be connected to GET /api/doctors/search.</div>
            `;
        });
    }
});
