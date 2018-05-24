package br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol;

import java.util.List;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public interface Protocol<T> {

    public T encode(T obj);

    public List<T> encode(List<T> list);

    public T decode(T obj);

    public List<T> decode(List<T> list);

}
