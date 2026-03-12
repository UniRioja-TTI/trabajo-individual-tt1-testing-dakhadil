package servicios;

import modelo.DatosSolicitud;
import modelo.Entidad;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactoSimServiceTest {

    @Test
    void getEntities_returnsNonEmptyList() {
        ContactoSimService s = new ContactoSimService();
        List<Entidad> entities = s.getEntities();
        assertNotNull(entities);
        assertTrue(entities.size() > 0);
    }

    @Test
    void solicitarSimulation_returnsTokenNotMinusOne() {
        ContactoSimService s = new ContactoSimService();
        Map<Integer, Integer> nums = new HashMap<>();
        nums.put(1, 10);
        DatosSolicitud ds = new DatosSolicitud(nums);

        int token = s.solicitarSimulation(ds);
        assertNotEquals(-1, token);
    }

    @Test
    void descargarDatos_notImplementedYet_returnsNull() {
        ContactoSimService s = new ContactoSimService();
        assertNull(s.descargarDatos(123));
    }
}