package br.edu.ifspsaocarlos.sdm.pa2trabalho.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public class ContatoDAO extends AbstractDAO<Contato> {

    @Override
    public Contato get(Object id) {
        return null;
    }

    @Override
    public List<Contato> getAll() {
        return new ArrayList<Contato>();
    }
}
