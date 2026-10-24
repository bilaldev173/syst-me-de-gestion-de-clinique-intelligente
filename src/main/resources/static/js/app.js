const toast = document.createElement('div');
toast.className = 'fixed right-5 top-5 z-[60] hidden max-w-sm rounded-2xl border border-slate-200 bg-white p-4 shadow-2xl';
document.body.appendChild(toast);

function showToast(message, type = 'success') {
  const colors = {
    success: 'border-emerald-200 bg-emerald-50 text-emerald-700',
    info: 'border-clinic-200 bg-clinic-50 text-clinic-700',
    warning: 'border-amber-200 bg-amber-50 text-amber-700',
  };

  toast.className = `fixed right-5 top-5 z-[60] max-w-sm rounded-2xl border p-4 shadow-2xl ${colors[type] || colors.success}`;
  toast.innerHTML = `
    <div class="flex items-center gap-3">
      <div class="flex h-8 w-8 items-center justify-center rounded-full bg-white/80 text-base font-bold">✓</div>
      <p class="text-sm font-semibold">${message}</p>
    </div>
  `;
  toast.classList.remove('hidden');

  setTimeout(() => {
    toast.classList.add('hidden');
  }, 2800);
}

const sampleDoctors = [
  {
    id: 1,
    name: 'Dr. Emma Clarke',
    specialty: 'Cardiology',
    email: 'emma.clarke@clinic.com',
    availability: 'Today • 9:00 AM, 11:00 AM',
    rating: 4.9,
    nextSlot: '9:00 AM'
  },
  {
    id: 2,
    name: 'Dr. Sophia Martin',
    specialty: 'Pediatrics',
    email: 'sophia.martin@clinic.com',
    availability: 'Today • 10:30 AM, 2:00 PM',
    rating: 4.8,
    nextSlot: '10:30 AM'
  },
  {
    id: 3,
    name: 'Dr. Daniel Brooks',
    specialty: 'Neurology',
    email: 'daniel.brooks@clinic.com',
    availability: 'Today • 1:30 PM, 4:00 PM',
    rating: 4.7,
    nextSlot: '1:30 PM'
  },
  {
    id: 4,
    name: 'Dr. Claire Hudson',
    specialty: 'Dermatology',
    email: 'claire.hudson@clinic.com',
    availability: 'Today • 11:00 AM, 3:30 PM',
    rating: 4.9,
    nextSlot: '11:00 AM'
  }
];

function renderDoctorCards(doctors = sampleDoctors) {
  const container = document.getElementById('doctor-results');
  if (!container) return;

  container.innerHTML = doctors.map((doctor) => `
    <article class="rounded-3xl border border-slate-200 bg-white p-5 shadow-soft transition hover:-translate-y-1 hover:shadow-card">
      <div class="flex items-center justify-between">
        <div class="flex h-12 w-12 items-center justify-center rounded-2xl bg-gradient-to-br from-clinic-100 to-sky-100 text-sm font-black text-clinic-700">
          ${doctor.name.split(' ').map(part => part[0]).slice(0, 2).join('')}
        </div>
        <span class="rounded-full bg-emerald-100 px-2.5 py-1 text-[11px] font-semibold text-emerald-700">Available</span>
      </div>

      <div class="mt-4">
        <h4 class="text-xl font-bold text-slate-900">${doctor.name}</h4>
        <p class="mt-1 text-sm font-medium text-clinic-700">${doctor.specialty}</p>
      </div>

      <div class="mt-4 space-y-2 text-sm text-slate-600">
        <div class="flex items-center justify-between">
          <span>Rating</span>
          <span class="font-semibold text-slate-900">★ ${doctor.rating}</span>
        </div>
        <div class="flex items-center justify-between">
          <span>Next slot</span>
          <span class="font-semibold text-slate-900">${doctor.nextSlot}</span>
        </div>
      </div>

      <p class="mt-4 text-sm text-slate-500">${doctor.availability}</p>

      <button type="button" data-doctor-name="${doctor.name}" data-doctor-specialty="${doctor.specialty}" class="book-doctor mt-5 inline-flex w-full items-center justify-center rounded-xl bg-gradient-to-r from-clinic-600 to-clinic-500 px-4 py-3 text-sm font-semibold text-white shadow-card transition hover:-translate-y-0.5 hover:shadow-lg">
        Book Appointment
      </button>
    </article>
  `).join('');

  document.querySelectorAll('.book-doctor').forEach((button) => {
    button.addEventListener('click', () => {
      const name = button.getAttribute('data-doctor-name');
      const specialty = button.getAttribute('data-doctor-specialty');
      const modal = document.getElementById('booking-modal');
      const input = document.getElementById('booking-doctor');
      if (input) input.value = `${name} (${specialty})`;
      if (modal) modal.classList.remove('hidden');
      modal.classList.add('flex');
    });
  });
}

function filterDoctors() {
  const query = (document.getElementById('search-doctor')?.value || '').trim().toLowerCase();
  const specialty = (document.getElementById('specialty-filter')?.value || '').trim();

  const filtered = sampleDoctors.filter((doctor) => {
    const matchesQuery = !query || doctor.name.toLowerCase().includes(query) || doctor.specialty.toLowerCase().includes(query);
    const matchesSpecialty = !specialty || doctor.specialty === specialty;
    return matchesQuery && matchesSpecialty;
  });

  renderDoctorCards(filtered);
}

function attachGlobalHandlers() {
  const forms = document.querySelectorAll('form');
  forms.forEach((form) => {
    form.addEventListener('submit', (event) => {
      event.preventDefault();
      const formId = form.id;

      if (formId === 'doctor-form') {
        const name = document.getElementById('doctor-name')?.value?.trim();
        if (!name) {
          showToast('Please enter the doctor name.', 'warning');
          return;
        }
        showToast('Doctor added successfully', 'success');
      }

      if (formId === 'prescription-form') {
        showToast('Prescription saved successfully', 'success');
      }

      if (formId === 'booking-form') {
        const date = document.getElementById('booking-date')?.value;
        if (!date) {
          showToast('Please select a date.', 'warning');
          return;
        }
        showToast('Appointment confirmed successfully', 'success');
        const modal = document.getElementById('booking-modal');
        if (modal) {
          modal.classList.add('hidden');
          modal.classList.remove('flex');
        }
      }
    });
  });

  document.getElementById('search-doctors-btn')?.addEventListener('click', filterDoctors);
  document.getElementById('search-doctor')?.addEventListener('input', filterDoctors);
  document.getElementById('specialty-filter')?.addEventListener('change', filterDoctors);

  document.getElementById('open-prescription-modal')?.addEventListener('click', () => {
    const modal = document.getElementById('prescription-modal');
    if (modal) {
      modal.classList.remove('hidden');
      modal.classList.add('flex');
    }
  });

  document.querySelectorAll('.close-modal').forEach((button) => {
    button.addEventListener('click', () => {
      const modal = button.closest('.fixed');
      if (modal) {
        modal.classList.add('hidden');
        modal.classList.remove('flex');
      }
    });
  });
}

window.addEventListener('DOMContentLoaded', () => {
  renderDoctorCards();
  attachGlobalHandlers();
});
