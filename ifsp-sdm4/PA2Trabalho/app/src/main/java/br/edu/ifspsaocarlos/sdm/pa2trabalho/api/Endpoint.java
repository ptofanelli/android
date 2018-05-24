package br.edu.ifspsaocarlos.sdm.pa2trabalho.api;

import java.io.IOException;
import java.util.List;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public interface Endpoint<T> {

    public T get(Object... args);

    public List<T> getAll() throws IOException;


}
