package utilidades;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Cliente HTTP para el servicio de simulación expuesto por la máquina virtual.
 * Consume los endpoints descritos en localhost:8080/swagger/v1/swagger.json.
 */
public class ServicioSimulacionClient {

    private final RestClient restClient;

    public ServicioSimulacionClient(String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    // ──────────────────────────────────────────────────────
    // POST /Email
    // Envía un correo electrónico a través del servicio.
    // ──────────────────────────────────────────────────────
    public EmailResponse enviarEmail(String emailAddress, String message) {
        return restClient.post()
                .uri("/Email?emailAddress={ea}&message={msg}", emailAddress, message)
                .retrieve()
                .body(EmailResponse.class);
    }

    // ──────────────────────────────────────────────────────
    // POST /Solicitud/Solicitar
    // Envía una solicitud de simulación al servidor de cómputo.
    // Devuelve la respuesta en bruto; el token se extrae del campo correspondiente.
    // ──────────────────────────────────────────────────────
    public void solicitarSimulacion(String nombreUsuario, Solicitud solicitud) {
        restClient.post()
                .uri("/Solicitud/Solicitar?nombreUsuario={u}", nombreUsuario)
                .body(solicitud)
                .retrieve()
                .toBodilessEntity();
    }

    // ──────────────────────────────────────────────────────
    // GET /Solicitud/GetSolicitudesUsuario
    // Devuelve la lista de tokens de solicitudes de un usuario.
    // ──────────────────────────────────────────────────────
    public List<Integer> getSolicitudesUsuario(String nombreUsuario) {
        return restClient.get()
                .uri("/Solicitud/GetSolicitudesUsuario?nombreUsuario={u}", nombreUsuario)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    // ──────────────────────────────────────────────────────
    // GET /Solicitud/ComprobarSolicitud
    // Comprueba si una simulación con el token dado ha terminado y devuelve sus datos.
    // ──────────────────────────────────────────────────────
    public List<Integer> comprobarSolicitud(String nombreUsuario, int tok) {
        return restClient.get()
                .uri("/Solicitud/ComprobarSolicitud?nombreUsuario={u}&tok={t}", nombreUsuario, tok)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    // ──────────────────────────────────────────────────────
    // POST /Resultados
    // Descarga los resultados finales de una simulación.
    // ──────────────────────────────────────────────────────
    public void descargarResultados(String nombreUsuario, int tok) {
        restClient.post()
                .uri("/Resultados?nombreUsuario={u}&tok={t}", nombreUsuario, tok)
                .retrieve()
                .toBodilessEntity();
    }

    // ──────────────────────────────────────────────────────
    // DTOs
    // ──────────────────────────────────────────────────────

    /** Cuerpo de la petición POST /Solicitud/Solicitar */
    public static class Solicitud {
        private List<Integer> cantidadesIniciales;
        private List<String> nombreEntidades;

        public Solicitud(List<Integer> cantidadesIniciales, List<String> nombreEntidades) {
            this.cantidadesIniciales = cantidadesIniciales;
            this.nombreEntidades = nombreEntidades;
        }

        public List<Integer> getCantidadesIniciales() { return cantidadesIniciales; }
        public List<String> getNombreEntidades() { return nombreEntidades; }
    }

    /** Respuesta de POST /Email */
    public record EmailResponse(boolean done, String errorMessage) {}
}
