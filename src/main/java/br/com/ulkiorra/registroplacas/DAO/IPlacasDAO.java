package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.model.Placas;
import br.com.ulkiorra.registroplacas.model.Status;

import java.util.List;

public interface IPlacasDAO {
    Placas create(Placas placas);
    Placas update(Placas placas);
    void delete(Placas id);
    List<Placas>FindAll();
    List<Placas>FindByPlaca(String placa);
    List<Placas>FindByName(String name);
    List<Placas>FindByStatus(Status status);
    boolean Open();
}
