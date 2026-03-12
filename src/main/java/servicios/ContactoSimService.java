package servicios;

import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ContactoSimService implements InterfazContactoSim {

    private final List<Entidad> entities;
    private final List<DatosSolicitud> solicitudes;
    private final Random rnd;

    public ContactoSimService() {
        this.entities = new ArrayList<>();
        this.solicitudes = new ArrayList<>();
        this.rnd = new Random();

        // Inventamos entidades (básico)
        entities.add(entidad(1, "Alpha", "Entidad Alpha"));
        entities.add(entidad(2, "Beta", "Entidad Beta"));
        entities.add(entidad(3, "Gamma", "Entidad Gamma"));
    }

    private Entidad entidad(int id, String name, String desc) {
        Entidad e = new Entidad();
        e.setId(id);
        e.setName(name);
        e.setDescripcion(desc);
        return e;
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        // Guardamos provisionalmente la solicitud
        solicitudes.add(sol);

        // Token aleatorio (positivo)
        int token = rnd.nextInt(Integer.MAX_VALUE);
        if (token == -1) token = 0;
        return token;
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        // De momento no procesamos nada: se implementará más adelante
        return null;
    }

    @Override
    public List<Entidad> getEntities() {
        return entities;
    }

    @Override
    public boolean isValidEntityId() {
        // Interfaz rara (no recibe id). De momento devolvemos true para no bloquear.
        return true;
    }
}