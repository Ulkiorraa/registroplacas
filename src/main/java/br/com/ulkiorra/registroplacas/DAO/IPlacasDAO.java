package br.com.ulkiorra.registroplacas.DAO;

import br.com.ulkiorra.registroplacas.model.Placas;

import java.util.List;

public interface IPlacasDAO {
    Placas create(Placas placas);
    Placas update(Placas placas);
    void delete(Long id);
    List<Placas>FindAll();
    List<Placas>FindByPlaca(String placa);
    List<Placas>FindByName(String name);
}
