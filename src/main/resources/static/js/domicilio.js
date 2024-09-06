  document.addEventListener("DOMContentLoaded", function() {
            // Obtener el domicilioId de la URL
            const params = new URLSearchParams(window.location.search);
            const domicilioId = params.get("domicilioId");
            const domicilioForm = document.getElementById("domicilioForm");

            if (domicilioId) {
                // Cargar los datos del domicilio si existe el domicilioId
                cargarDomicilio(domicilioId);
            }

            // Función para cargar los datos del domicilio (simulación)
            function cargarDomicilio(id) {
                 fetch(`/domicilios/${id}`)
                 .then(response => {
                     if (!response.ok) {
                         throw new Error('Error al obtener el paciente.');
                        }
                     return response.json();
                  })
                   .then(domicilio => {


                // Rellenar los campos con los datos del domicilio
                document.getElementById("id").value = id;
                document.getElementById("calle").value = domicilio.calle;
                document.getElementById("numero").value = domicilio.numero;
                document.getElementById("localidad").value = domicilio.localidad;
                document.getElementById("provincia").value = domicilio.provincia;
           })
             .catch(error => {
                 console.error('Error al editar paciente:', error);
                 alert(error.message);
              });
           }


            // Manejar el evento de guardar
            document.getElementById("domicilioForm").addEventListener("submit", function(event) {
                event.preventDefault();
                const url = '/domicilios';

                console.log("id :"+document.getElementById("id").value);
                 if (!document.getElementById("id").value) {
                     alert("El ID del domicilio es requerido");
                     //return;
                 }
                const domicilio = {
                // Obtener los valores del formulario
                    id : document.getElementById("id").value,
                    calle: document.getElementById("calle").value,
                    numero: document.getElementById("numero").value,
                    localidad : document.getElementById("localidad").value,
                    provincia : document.getElementById("provincia").value
                }
                   console.log("domicilio -->"+domicilio);
                fetch(url, {
                       method: "PUT",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(domicilio)
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al actualizar el DOMICILIO.');
                    }
                    return response.json();
                })
                .then(data => {
                    alert('Domicilio actualizado con éxito');
                    window.close();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(error.message);
                });

            });

            // Manejar el evento de cancelar
            document.getElementById("cancelarBtn").addEventListener("click", function() {
                window.close();
            });
        });

    function editarDomicilio(domicilioId) {
            // Abre una nueva ventana con la página de domicilio.html, pasando el id del domicilio
            const url = `domicilio.html?domicilioId=${domicilioId}`;
            const width = 500; // Ancho de la ventana
            const height = 400; // Altura de la ventana
            const left = (screen.width - width) / 2; // Centra la ventana horizontalmente
            const top = (screen.height - height) / 2; // Centra la ventana verticalmente

            // Abre la ventana emergente
            window.open(url, 'Editar Domicilio', `width=${width},height=${height},top=${top},left=${left}`);
        }
