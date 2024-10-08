document.addEventListener('DOMContentLoaded', () => {
    const pacienteForm = document.getElementById('pacienteForm');
    const pacientesTableBody = document.querySelector('#pacientesTable tbody');
    const buscarPacienteForm = document.getElementById('buscarPacienteForm');
    const criterioBusqueda = document.getElementById('criterioBusqueda');
    const busquedaInput = document.getElementById('busqueda');
    const formTitle = document.getElementById('formTitle');
    const guardarPacienteBtn = document.getElementById('guardarPaciente');
    const confirmDeleteModal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    const confirmarEliminarBtn = document.getElementById('confirmarEliminarBtn');
    const inicioBtn = document.getElementById('inicioBtn');
    const listarTodosBtn = document.getElementById('listarTodosBtn');
    const tableBody = document.getElementById('pacientesTableBody');
    const formCard = document.getElementById('formContainer');
    const mostrarFormularioBtn = document.getElementById('mostrarFormularioBtn');
    const submitBtn = document.getElementById('submitBtn');
    const cancelarBtn = document.getElementById('cancelarBtn');

    let pacienteIdEliminar = null;



      // Redirigir a index.html al hacer clic en el botón
            inicioBtn.addEventListener('click', function() {
                window.location.href = 'index.html';
            });

      // Mostrar el formulario para agregar un nuevo paciente
           mostrarFormularioBtn.addEventListener('click', function() {
               formCard.style.display = 'block';
               mostrarFormularioBtn.style.display = 'none';
               submitBtn.textContent = 'Guardar';  // Restablecer el texto del botón a "Guardar"
               document.getElementById('pacienteId').value = ''; // Limpiar el ID oculto
               formCard.reset(); // Limpiar cualquier otro dato en el formulario
           });


      // Cancelar y ocultar el formulario
           cancelarBtn.addEventListener('click', function() {
               //formCard.reset();
               formCard.style.display = 'none';
               mostrarFormularioBtn.style.display = 'block';
           });


    // Función para agregar o actualizar un paciente
        pacienteForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const pacienteId = document.getElementById('pacienteId').value;



            const metodo = pacienteId ? 'PUT' : 'POST';
            const url = '/pacientes';

            const paciente = {
                id: pacienteId ? parseInt(pacienteId, 10)  : null,
                nombre: document.getElementById('nombre').value.toLowerCase().trim(),
                apellido: document.getElementById('apellido').value.toLowerCase().trim(),
                dni: document.getElementById('dni').value,
                fechaAlta: document.getElementById('fechaAlta').value,
                domicilio: {
                    calle: document.getElementById('calle').value.trim(),
                    numero: parseInt(document.getElementById('numero').value),
                    localidad: document.getElementById('localidad').value.trim(),
                    provincia: document.getElementById('provincia').value.trim()
                }
            };
             console.log('Paciente a guardar:', paciente);
              verificarUnicidadDni(paciente.dni)
                            .then(existe => {
                                if (existe && !paciente.id) {
                                    alert('Ya existe un paciente con ese dni');
                                    return;
                                }

            fetch(url, {
                method: metodo,
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(paciente)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al guardar el paciente.');
                }
                return response.json();
            })
            .then(data => {
                console.log('Respuesta del servidor:', data);
                alert('Paciente guardado/actualizado con éxito');
                pacienteForm.reset();
                document.getElementById('pacienteId').value = '';
                formTitle.textContent = 'Agregar Paciente';
                guardarPacienteBtn.textContent = 'Guardar';
                listarPacientes();
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error al guardar/actualizar paciente");
            })
            .catch(error => console.error('Error en la verificación de dni:', error));
            });
        });

    // Función para listar todos los pacientes
    function listarPacientes() {
        fetch('/pacientes')
            .then(response => response.json())
            .then(data => {

                pacientesTableBody.innerHTML = '';
                data.forEach(paciente => {
                    const row = pacientesTableBody.insertRow();
                    row.insertCell(0).innerText = paciente.id;
                    row.insertCell(1).innerText = capitalizarPrimeraLetra(paciente.nombre);
                    row.insertCell(2).innerText = capitalizarPrimeraLetra(paciente.apellido);
                    row.insertCell(3).innerText = paciente.dni;
                    row.insertCell(4).innerText = paciente.fechaAlta;
                    const accionesCell = row.insertCell(5);
                    accionesCell.innerHTML = `
                      <div class="d-flex justify-content-center" >
                        <button class="btn btn-warning btn-sm me-2" onclick="editarPaciente(${paciente.id})">Editar</button>
                        <button class="btn btn-danger btn-sm me-2" onclick="prepararEliminarPaciente(${paciente.id})">Eliminar</button>
                        <button class="btn btn-primary btn-sm" onclick="editarDomicilio(${paciente.domicilio.id})">Domicilio</button>
                       </div>
                    `;


                });
            })
            .catch(error => console.error('Error al listar pacientes:', error));

    }


    // Función para preparar la eliminación de un paciente
        window.prepararEliminarPaciente = function(id) {
            pacienteIdEliminar = id;
            confirmDeleteModal.show();
        };
    // Confirmar eliminación
        confirmarEliminarBtn.addEventListener('click', () => {
            if (pacienteIdEliminar) {
                fetch(`/pacientes/${pacienteIdEliminar}`, {
                    method: 'DELETE'
                })
                .then(response => {
                    if (response.ok) {
                        alert('Paciente eliminado con éxito');
                        listarPacientes();
                    } else {
                        throw new Error('No se pudo eliminar el paciente.');
                    }
                })
                .catch(error => {
                    console.error('Error al eliminar paciente:', error);
                    alert('Error al eliminar paciente:');
                })
                .finally(() => {
                    confirmDeleteModal.hide();
                    pacienteIdEliminar = null;
                });
            }
        });

    // Función para buscar pacientes
    buscarPacienteForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const busqueda = busquedaInput.value.trim();
        const criterio = criterioBusqueda.value;
        let url = '';

        if (criterio === 'id') {
            url = `/pacientes/${busqueda}`;
        } else if (criterio === 'dni') {
            url = `/pacientes/dni/${encodeURIComponent(busqueda)}`; // Asegúrate de tener este endpoint en tu backend

        }


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
                pacientesTableBody.innerHTML = '';
                if (data) {
                    const row = pacientesTableBody.insertRow();
                    row.insertCell(0).innerText = data.id;
                    row.insertCell(1).innerText = capitalizarPrimeraLetra(data.nombre);
                    row.insertCell(2).innerText = capitalizarPrimeraLetra(data.apellido);
                    row.insertCell(3).innerText = data.dni;
                    row.insertCell(4).innerText = data.fechaAlta;
                    const accionesCell = row.insertCell(5);
                    accionesCell.innerHTML = `
                        <button class="btn btn-warning btn-sm me-2" onclick="editarPaciente(${data.id})">Editar</button>
                        <button class="btn btn-danger btn-sm" onclick="prepararEliminarPaciente(${data.id})">Eliminar</button>


                    `;
                }
            })
            .catch(error => {
                pacientesTableBody.innerHTML = '';
                tableBody.innerHTML = ''; // Limpiar la tabla en caso de error
                const row = tableBody.insertRow();
                const cell = row.insertCell(0);
                cell.colSpan = 6;
                cell.textContent = `No se encontró el paciente con el ${criterio} proporcionado.`//error.message; // Mostrar el mensaje de error
                cell.style.textAlign = 'center'; // Centrar el mensaje en la tabla

            })
            .finally(() => {
                // Limpia el campo de búsqueda siempre, independientemente del resultado
                document.getElementById('busqueda').value = '';
                            });
    });

       // Función para editar un paciente
            window.editarPaciente = function(id) {
                fetch(`/pacientes/${id}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Error al obtener el paciente.');
                        }
                        return response.json();
                    })
                    .then(paciente => {
                        document.getElementById('pacienteId').value = paciente.id;
                        document.getElementById('nombre').value = capitalizarPrimeraLetra(paciente.nombre);
                        document.getElementById('apellido').value = capitalizarPrimeraLetra(paciente.apellido);
                        document.getElementById('dni').value = paciente.dni;
                        document.getElementById('fechaAlta').value = paciente.fechaAlta;
                        document.getElementById('calle').value = paciente.domicilio.calle;
                        document.getElementById('numero').value = paciente.domicilio.numero;
                        document.getElementById('localidad').value = paciente.domicilio.localidad;
                        document.getElementById('provincia').value = paciente.domicilio.provincia;
                        formTitle.textContent = 'Editar Paciente';
                        guardarPacienteBtn.textContent = 'Actualizar';
                        formCard.style.display = 'block';
                        mostrarFormularioBtn.style.display = 'none';
                        window.scrollTo(0, 0);
                    })
                    .catch(error => {
                        console.error('Error al editar paciente:', error);
                        alert(`No se encontró el paciente con el ${criterio} proporcionado.`);
                    });
            };



     listarTodosBtn.addEventListener('click', listarPacientes);

    // Inicializar la lista de pacientes al cargar la página
    listarPacientes();

     function capitalizarPrimeraLetra(texto) {
            return texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
        }
             // Verificar unicidad de matrícula
     function verificarUnicidadDni(dni) {
         return fetch(`/pacientes/dni/${encodeURIComponent(dni)}`)
             .then(response => response.json())
             .then(data => !!data) // Si data existe, la matrícula ya está en uso
             .catch(error => {
                 console.error('Error al verificar dni:', error);
                 return false; // Suponemos que la matrícula no está en uso si hay un error
             });
     }

});
