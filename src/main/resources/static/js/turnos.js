document.addEventListener('DOMContentLoaded', () => {
    const turnoForm = document.getElementById('turnoForm');
    const turnosTableBody = document.querySelector('#turnosTable tbody');
    const formTitle = document.getElementById('formTitle');
    const guardarTurnoBtn = document.getElementById('guardarTurno');
    const limpiarFormularioBtn = document.getElementById('limpiarFormulario');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    const confirmarEliminarBtn = document.getElementById('confirmarEliminarBtn');
    let turnoIdEliminar = null;

    // Función para listar todos los turnos
    function listarTurnos() {
        fetch('/turnos')
            .then(response => response.json())
            .then(data => {
                turnosTableBody.innerHTML = '';
                data.forEach(turno => {
                    const row = turnosTableBody.insertRow();
                    row.insertCell(0).innerText = turno.id;
                    row.insertCell(1).innerText = turno.odontologo.nombre+" "+turno.odontologo.apellido;
                    row.insertCell(2).innerText = turno.paciente.nombre+" "+turno.paciente.apellido;
                    row.insertCell(3).innerText = turno.fecha;
                    const accionesCell = row.insertCell(4);
                    accionesCell.innerHTML = `
                        <button class="btn btn-warning btn-sm me-2" onclick="editarTurno(${turno.id})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="prepararEliminarTurno(${turno.id})">Eliminar</button>
                    `;
                });
            })
            .catch(error => console.error('Error al listar turnos:', error));
    }

    // Función para agregar o actualizar un turno
    turnoForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const turnoId = document.getElementById('turnoId').value;
        const metodo = turnoId ? 'PUT' : 'POST';
        const url = '/turnos';

        const turno = {
            id: turnoId ? parseLong(turnoId) : null,
            odontologo: { id: parseInt(document.getElementById('odontologoId').value) },
            paciente: { id: parseInt(document.getElementById('pacienteId').value) },
            fecha: document.getElementById('fechaTurno').value
        };

        fetch(url, {
            method: metodo,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(turno)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al guardar el turno.');
            }
            return response.json();
        })
        .then(data => {
            alert('Turno guardado/actualizado con éxito');
            turnoForm.reset();
            document.getElementById('turnoId').value = '';
            formTitle.textContent = 'Agregar Turno';
            guardarTurnoBtn.textContent = 'Guardar';
            listarTurnos();
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
    });

    // Función para limpiar el formulario
    limpiarFormularioBtn.addEventListener('click', () => {
        turnoForm.reset();
        document.getElementById('turnoId').value = '';
        formTitle.textContent = 'Agregar Turno';
        guardarTurnoBtn.textContent = 'Guardar';
    });

    // Función para editar un turno
    window.editarTurno = function(id) {
        fetch(`/turnos/${id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al obtener el turno.');
                }
                return response.json();
            })
            .then(turno => {
                document.getElementById('turnoId').value = turno.id;
                document.getElementById('odontologoId').value = turno.odontologo.id;
                document.getElementById('pacienteId').value = turno.paciente.id;
                document.getElementById('fechaTurno').value = turno.fecha;
                formTitle.textContent = 'Editar Turno';
                guardarTurnoBtn.textContent = 'Actualizar';
                window.scrollTo(0, 0);
            })
            .catch(error => {
                console.error('Error al editar turno:', error);
                alert(error.message);
            });
    };

    // Función para preparar la eliminación de un turno
    window.prepararEliminarTurno = function(id) {
        turnoIdEliminar = id;
        confirmDeleteModal.show();
    };

    // Confirmar eliminación
    confirmarEliminarBtn.addEventListener('click', () => {
        if (turnoIdEliminar) {
            fetch(`/turnos/${turnoIdEliminar}`, {
                method: 'DELETE'
            })
            .then(response => {
                if (response.ok) {
                    alert('Turno eliminado con éxito');
                    listarTurnos();
                } else {
                    throw new Error('No se pudo eliminar el turno.');
                }
            })
            .catch(error => {
                console.error('Error al eliminar turno:', error);
                alert(error.message);
            })
            .finally(() => {
                confirmDeleteModal.hide();
                turnoIdEliminar = null;
            });
        }
    });

    // Inicializar la lista de turnos al cargar la página
    listarTurnos();
});
