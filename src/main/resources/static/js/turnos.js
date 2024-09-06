document.addEventListener('DOMContentLoaded', () => {
    const turnoForm = document.getElementById('turnoForm');
    //const turnosTableBody = document.querySelector('#turnosTable tbody');
    const formTitle = document.getElementById('formTitle');
    const form = document.getElementById('turnoForm');
    const formCard = document.getElementById('formContainer');
    const guardarTurnoBtn = document.getElementById('guardarTurno');
    const submitBtn = document.getElementById('submitBtn');
    const limpiarFormularioBtn = document.getElementById('limpiarFormulario');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    const confirmarEliminarBtn = document.getElementById('confirmarEliminarBtn');
    const buscarTurnoForm = document.getElementById('buscarTurnoForm');
    const inicioBtn = document.getElementById('inicioBtn');
    const listarTodosBtn = document.getElementById('listarTodosBtn');
    const turnosTableBody = document.getElementById('turnosTableBody');
    const criterioBusqueda = document.getElementById('criterioBusqueda');
    const busquedaInput = document.getElementById('busqueda');
    //const cancelarBtn = document.getElementById('cancelarBtn');
    const mostrarFormularioBtn = document.getElementById('mostrarFormularioBtn');

    let turnoIdEliminar = null;

     // Redirigir a index.html al hacer clic en el botón
                inicioBtn.addEventListener('click', function() {
                    window.location.href = 'index.html';
                });

     // Mostrar el formulario para agregar un nuevo turno
         mostrarFormularioBtn.addEventListener('click', function() {
             formCard.style.display = 'block';
             mostrarFormularioBtn.style.display = 'none';
             //submitBtn.textContent = 'Guardar';  // Restablecer el texto del botón a "Guardar"
             document.getElementById('turnoId').value = ''; // Limpiar el ID oculto
             turnoForm.reset(); // Limpiar cualquier otro dato en el formulario
         });


         // Cancelar y ocultar el formulario
         cancelarBtn.addEventListener('click', function() {
             //formCard.reset();
             formCard.style.display = 'none';
             mostrarFormularioBtn.style.display = 'block';
         });

    // Función para listar todos los turnos
    function listarTurnos() {
        fetch('/turnos')
            .then(response => response.json())
            .then(data => {
             console.log("--->"+data);
                turnosTableBody.innerHTML = '';
                data.forEach(turno => {
                    const fechaHora = new Date(turno.fecha);
                     // Obtener la fecha local formateada
                     const fechaFormateada = fechaHora.toLocaleDateString('es-ES'); // Cambia 'es-ES' por tu configuración regional si es necesario
                    // Obtener la hora local formateada
                     const horaFormateada = fechaHora.toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' });

                    const row = turnosTableBody.insertRow();
                    row.insertCell(0).innerText = turno.id;
                    row.insertCell(1).innerText = (turno.odontologo?.nombre || '') + " " + (turno.odontologo?.apellido || '');
                    row.insertCell(2).innerText = (turno.paciente?.nombre || '') + " " + (turno.paciente?.apellido || '');
                    row.insertCell(3).innerText = fechaFormateada+" "+horaFormateada;
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
            id: turnoId ? parseInt(turnoId) : null,
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
                formCard.style.display = 'block';
                mostrarFormularioBtn.style.display = 'none';
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

    // Función para buscar turnos
        buscarTurnoForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const busqueda = busquedaInput?.value.trim().substring(0, 1).toUpperCase()+busquedaInput?.value.trim().substring(1).toLowerCase();
            const criterio = criterioBusqueda.value;
            let url = '';

            if (criterio === 'nombrePaciente') {
                url = `/turnos/nombrePaciente/${busqueda}`;
            } else if (criterio === 'nombreOdontologo') {
                url = `/turnos/nombreOdontologo/${busqueda}`;
            }
            console.log("url --->"+url);
            fetch(url)
                .then(response => {
                    if (response.status === 404) {
                        throw new Error(`No se encontró el paciente con el ${criterio} proporcionado.`);
                    } else if (!response.ok) {
                        throw new Error('Error en la búsqueda del paciente.');
                    }
                    return response.json();
                })
                .then(data => {

                    turnosTableBody.innerHTML = '';
                    if (data && data.length > 0) {
                     data.forEach(turno => {
                        console.log(turno);
                        const row = turnosTableBody.insertRow();
                        row.insertCell(0).innerText = turno.id || '';
                        row.insertCell(1).innerText =`${turno.odontologo?.nombre || ''} ${turno.odontologo?.apellido || ''}`;
                        row.insertCell(2).innerText = `${turno.paciente?.nombre || ''} ${turno.paciente?.apellido || ''}`;
                        row.insertCell(3).innerText = turno.fecha || '';

                        const accionesCell = row.insertCell(4);
                        accionesCell.innerHTML = `
                            <button class="btn btn-warning btn-sm me-2" onclick="editarTurno(${turno.id})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="prepararEliminarTurno(${turno.id})">Eliminar</button>
                        `;
                        });
                    }
                })
                .catch(error => {
                    turnosTableBody.innerHTML = '';
                    alert(error.message);
                })
                .finally(() => {
                                    // Limpia el campo de búsqueda siempre, independientemente del resultado
                                    document.getElementById('busqueda').value = '';
                                });
        });

    listarTodosBtn.addEventListener('click', listarTurnos);
    // Inicializar la lista de turnos al cargar la página
    listarTurnos();
});
