package br.edu.ifspsaocarlos.sdm.pa2trabalho.dao;

import java.util.List;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public interface DAO<T> {

    public T get(Object id);

    public List<T> getAll();

}
