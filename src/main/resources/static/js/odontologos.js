document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('odontologoForm');
    const submitBtn = document.getElementById('submitBtn');
    const cancelarBtn = document.getElementById('cancelarBtn');
    const mostrarFormularioBtn = document.getElementById('mostrarFormularioBtn');
    const buscarBtn = document.getElementById('buscarBtn');
    const listarTodosBtn = document.getElementById('listarTodosBtn');
    const tableBody = document.getElementById('odontologosTableBody');
    const inicioBtn = document.getElementById('inicioBtn');


    // Redirigir a index.html al hacer clic en el botón
        inicioBtn.addEventListener('click', function() {
            window.location.href = 'index.html';
        });

    // Mostrar el formulario para agregar un nuevo odontólogo
    mostrarFormularioBtn.addEventListener('click', function() {
        form.style.display = 'block';
        mostrarFormularioBtn.style.display = 'none';
        submitBtn.textContent = 'Guardar';  // Restablecer el texto del botón a "Guardar"
        document.getElementById('odontologoId').value = ''; // Limpiar el ID oculto
        form.reset(); // Limpiar cualquier otro dato en el formulario
    });


    // Cancelar y ocultar el formulario
    cancelarBtn.addEventListener('click', function() {
        form.reset();
        form.style.display = 'none';
        mostrarFormularioBtn.style.display = 'block';
    });



    // Guardar o actualizar odontólogo
    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const odontologo = {
            id: document.getElementById('odontologoId').value.toLowerCase(),
            nombre: document.getElementById('nombre').value.toLowerCase(),
            apellido: document.getElementById('apellido').value.toLowerCase(),
            matricula: document.getElementById('matricula').value.toLowerCase()
        };

        const url = '/odontologos';
        const method = odontologo.id ? 'PUT' : 'POST';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(odontologo)
        })
        .then(response => response.json())
        .then(data => {
            alert(odontologo.id ? 'Odontólogo actualizado' : 'Odontólogo agregado');
            form.reset();
            form.style.display = 'none';
            mostrarFormularioBtn.style.display = 'block';
            submitBtn.textContent = 'Guardar';  // Restablecer el texto del botón a "Guardar"
            listarTodos();
        })
        .catch(error => console.error('Error:', error));
    });


    // Buscar odontólogo por ID o matrícula
    buscarBtn.addEventListener('click', function() {
        const busqueda = document.getElementById('busqueda').value.trim();
        const criterio = document.getElementById('criterioBusqueda').value;
        let url = '';

        if (criterio === 'id') {
            url = `/odontologos/${busqueda}`;
        } else if (criterio === 'matricula') {
            url = `/odontologos/matricula/${encodeURIComponent(busqueda)}`;
        }

        fetch(url)
        .then(response => {
            if (response.status === 404) {
                throw new Error(`No se encontró el odontólogo con el ${criterio} proporcionado.`);
            } else if (!response.ok) {
                throw new Error('Error en la búsqueda del odontólogo.');
            }
            return response.json();
        })
        .then(data => {
            tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar resultados
            if (data) {
                agregarFilaTabla(data); // Mostrar el odontólogo encontrado
            } else {
                const row = tableBody.insertRow();
                const cell = row.insertCell(0);
                cell.colSpan = 5;
                cell.textContent = `No se encontró el odontólogo con el ${criterio} proporcionado.`;
                cell.style.textAlign = 'center'; // Centrar el mensaje en la tabla

            }
        })
        .catch(error => {
            tableBody.innerHTML = ''; // Limpiar la tabla en caso de error
            const row = tableBody.insertRow();
            const cell = row.insertCell(0);
            cell.colSpan = 5;
            cell.textContent = error.message; // Mostrar el mensaje de error
            cell.style.textAlign = 'center'; // Centrar el mensaje en la tabla

        })
        .finally(() => {
            // Limpia el campo de búsqueda siempre, independientemente del resultado
            document.getElementById('busqueda').value = '';
                });
    });




    // Listar todos los odontólogos
    function listarTodos() {
        fetch('/odontologos')
        .then(response => response.json())
        .then(data => {
            tableBody.innerHTML = '';
            data.forEach(odontologo => agregarFilaTabla(odontologo));
        })
        .catch(error => console.error('Error:', error));
    }

    listarTodosBtn.addEventListener('click', listarTodos);

    // Función para agregar una fila a la tabla
    function agregarFilaTabla(odontologo) {
        const row = tableBody.insertRow();
        row.innerHTML = `
            <td>${odontologo.id}</td>
            <td>${capitalizarPrimeraLetra(odontologo.nombre)}</td>
            <td>${capitalizarPrimeraLetra(odontologo.apellido)}</td>
            <td>${capitalizarPrimeraLetra(odontologo.matricula)}</td>
            <td>
                <button class="btn btn-sm btn-warning editar-btn" data-id="${odontologo.id}">Editar</button>
                <button class="btn btn-sm btn-danger eliminar-btn" data-id="${odontologo.id}">Eliminar</button>
            </td>
        `;
    }

    // Evento para editar odontólogo
    tableBody.addEventListener('click', function(e) {
        if(e.target.classList.contains('editar-btn')) {
            const id = e.target.getAttribute('data-id');
            fetch(`/odontologos/${id}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById('odontologoId').value = data.id;
                document.getElementById('nombre').value = capitalizarPrimeraLetra(data.nombre);
                document.getElementById('apellido').value = capitalizarPrimeraLetra(data.apellido);
                document.getElementById('matricula').value = data.matricula;
                submitBtn.textContent = 'Actualizar';
                form.style.display = 'block';
                mostrarFormularioBtn.style.display = 'none';
            })
            .catch(error => console.error('Error:', error));
        }
    });

    // Evento para eliminar odontólogo
    tableBody.addEventListener('click', function(e) {
        if(e.target.classList.contains('eliminar-btn')) {
            const id = e.target.getAttribute('data-id');
            if(confirm('¿Está seguro de que desea eliminar este odontólogo?')) {
                fetch(`/odontologos/${id}`, { method: 'DELETE' })
                .then(response => {
                    if(response.ok) {
                        alert('Odontólogo eliminado');
                        listarTodos();
                    } else {
                        alert('No se pudo eliminar el odontólogo');
                    }
                })
                .catch(error => console.error('Error:', error));
            }
        }
    });

    // Cargar la lista de odontólogos al iniciar
    listarTodos();

    function capitalizarPrimeraLetra(texto) {
        return texto.charAt(0).toUpperCase() + texto.slice(1).toLowerCase();
    }
});
