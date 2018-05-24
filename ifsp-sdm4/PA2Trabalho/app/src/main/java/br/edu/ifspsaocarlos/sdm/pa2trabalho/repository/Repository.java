package br.edu.ifspsaocarlos.sdm.pa2trabalho.repository;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public interface Repository<T> {

    public T get(Object id);

    public List<Contato> getAll() throws IOException;

}
