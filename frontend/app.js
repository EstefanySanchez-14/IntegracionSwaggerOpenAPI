const state = {
    apiUrl: localStorage.getItem("apiUrl") || "http://localhost:8080",
    username: localStorage.getItem("username") || "admin",
    password: localStorage.getItem("password") || "123456"
};

const $ = (selector) => document.querySelector(selector);

function setMessage(text, isError = false) {
    const message = $("#message");
    message.textContent = text;
    message.style.color = isError ? "#b91c1c" : "#334155";
}

function authHeader() {
    return "Basic " + btoa(`${state.username}:${state.password}`);
}

async function request(path, options = {}) {
    const response = await fetch(`${state.apiUrl}${path}`, {
        ...options,
        headers: {
            "Authorization": authHeader(),
            "Content-Type": "application/json",
            ...(options.headers || {})
        }
    });

    if (!response.ok) {
        let detail = `Error ${response.status}`;
        try {
            const body = await response.json();
            detail = body.message || detail;
        } catch {
            detail = await response.text();
        }
        throw new Error(detail);
    }

    if (response.status === 204) return null;
    return response.json();
}

function saveSession() {
    localStorage.setItem("apiUrl", state.apiUrl);
    localStorage.setItem("username", state.username);
    localStorage.setItem("password", state.password);
    $("#sessionLabel").textContent = `Sesion: ${state.username}`;
    $("#swaggerLink").href = `${state.apiUrl}/swagger-ui.html`;
}

function renderRooms(rooms) {
    $("#roomsCount").textContent = rooms.length;
    $("#roomsList").innerHTML = rooms.map(room => `
        <div class="item">
            <strong>Cuarto ${room.numero} - ${room.tipo}</strong>
            <small>Id: ${room.id} | Camas: ${room.numeroCamas} | Precio: $${room.precio} | Disponible: ${room.disponible ? "Si" : "No"}</small>
            <div class="item-actions">
                <button class="secondary" type="button" onclick="fillReservationRoom(${room.id})">Reservar</button>
                <button type="button" onclick="toggleRoom(${room.id}, ${!room.disponible})">${room.disponible ? "Marcar no disponible" : "Marcar disponible"}</button>
            </div>
        </div>
    `).join("");
}

function renderReservations(reservations) {
    $("#reservationsCount").textContent = reservations.length;
    $("#reservationsList").innerHTML = reservations.map(reservation => `
        <div class="item">
            <strong>Reservacion ${reservation.idReservacion} - ${reservation.estado}</strong>
            <small>Usuario: ${reservation.idUsuario} | Cuarto: ${reservation.idCuarto} | ${reservation.fechaEntrada} a ${reservation.fechaSalida}</small>
            <div class="item-actions">
                <button class="danger" type="button" onclick="cancelReservation(${reservation.idReservacion})">Cancelar</button>
            </div>
        </div>
    `).join("");
}

async function loadRooms() {
    const rooms = await request("/api/v1/cuartos");
    renderRooms(rooms);
}

async function loadReservations() {
    const reservations = await request("/api/v1/reservaciones");
    renderReservations(reservations);
}

window.fillReservationRoom = (id) => {
    $("#reservationRoom").value = id;
    setMessage(`Cuarto ${id} seleccionado para reservar.`);
};

window.toggleRoom = async (id, disponible) => {
    try {
        await request(`/api/v1/cuartos/${id}/disponibilidad`, {
            method: "PATCH",
            body: JSON.stringify({ disponible })
        });
        await loadRooms();
        setMessage("Disponibilidad actualizada.");
    } catch (error) {
        setMessage(error.message, true);
    }
};

window.cancelReservation = async (id) => {
    try {
        await request(`/api/v1/reservaciones/${id}/cancelar`, { method: "PATCH" });
        await loadReservations();
        setMessage("Reservacion cancelada.");
    } catch (error) {
        setMessage(error.message, true);
    }
};

$("#loginForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    state.apiUrl = $("#apiUrl").value.replace(/\/$/, "");
    state.username = $("#username").value;
    state.password = $("#password").value;
    saveSession();

    try {
        await loadRooms();
        setMessage("Conexion correcta.");
    } catch (error) {
        setMessage(error.message, true);
    }
});

$("#logoutBtn").addEventListener("click", () => {
    localStorage.clear();
    $("#sessionLabel").textContent = "Sin sesion";
    setMessage("Sesion local limpiada.");
});

$("#loadRoomsBtn").addEventListener("click", () => loadRooms().catch(error => setMessage(error.message, true)));
$("#loadReservationsBtn").addEventListener("click", () => loadReservations().catch(error => setMessage(error.message, true)));

$("#roomForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    try {
        await request("/api/v1/cuartos", {
            method: "POST",
            body: JSON.stringify({
                tipo: $("#roomType").value,
                numero: Number($("#roomNumber").value),
                precio: Number($("#roomPrice").value),
                numeroCamas: Number($("#roomBeds").value)
            })
        });
        event.target.reset();
        await loadRooms();
        setMessage("Cuarto creado correctamente.");
    } catch (error) {
        setMessage(error.message, true);
    }
});

$("#reservationForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    try {
        await request("/api/v1/reservaciones", {
            method: "POST",
            body: JSON.stringify({
                idUsuario: Number($("#reservationUser").value),
                idCuarto: Number($("#reservationRoom").value),
                fechaEntrada: $("#reservationStart").value,
                fechaSalida: $("#reservationEnd").value
            })
        });
        await loadReservations();
        setMessage("Reservacion creada correctamente.");
    } catch (error) {
        setMessage(error.message, true);
    }
});

$("#apiUrl").value = state.apiUrl;
$("#username").value = state.username;
$("#password").value = state.password;
saveSession();
