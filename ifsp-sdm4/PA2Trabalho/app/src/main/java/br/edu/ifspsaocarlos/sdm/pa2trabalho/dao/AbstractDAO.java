package br.edu.ifspsaocarlos.sdm.pa2trabalho.dao;

import java.util.List;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public abstract class AbstractDAO<T> implements DAO<T> {

    public abstract T get(Object id);

    public abstract List<T> getAll();

}
