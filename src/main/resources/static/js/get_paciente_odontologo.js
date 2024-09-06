document.addEventListener("DOMContentLoaded", function () {
    // Función para obtener pacientes y llenar el select
    fetch('/pacientes')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener pacientes');
            }
            return response.json();
        })
        .then(data => {
            const pacienteSelect = document.getElementById('pacienteId');
            data.forEach(paciente => {
                const option = document.createElement('option');
                option.value = paciente.id;
                option.textContent = `${capitalizarPrimeraLetra(paciente.nombre)} ${capitalizarPrimeraLetra(paciente.apellido)}`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error:', error));

    // Función para obtener odontólogos y llenar el select
    fetch('/odontologos')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener odontólogos');
            }
            return response.json();
        })
        .then(data => {
            const odontologoSelect = document.getElementById('odontologoId');
            data.forEach(odontologo => {
                const option = document.createElement('option');
                option.value = odontologo.id;
                option.textContent = `${capitalizarPrimeraLetra(odontologo.nombre)} ${capitalizarPrimeraLetra(odontologo.apellido)}`;
                odontologoSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error:', error));
});
 function capitalizarPrimeraLetra(texto) {
            return texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
        }