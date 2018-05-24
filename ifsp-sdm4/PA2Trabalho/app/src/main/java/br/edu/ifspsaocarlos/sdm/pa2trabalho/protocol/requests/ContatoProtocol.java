package br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.AbstractProtocol;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public class ContatoProtocol extends AbstractProtocol<Contato> {
    @Override
    public Contato encode(Contato obj) {
        return null;
    }

    @Override
    public List<Contato> encode(List<Contato> list) {
        return null;
    }

    @Override
    public Contato decode(Contato contato) {

        if(contato.getApelido().startsWith("#PT")) {
            contato = removeProtocolData(contato);
            return contato;
        }

        return null;
    }

    @Override
    public List<Contato> decode(List<Contato> list) {
        List<Contato> contatosResult = new ArrayList<>();

        for (Contato contato : list) {
            contato = decode(contato);
            if(contato != null) {
                contatosResult.add(contato);
            }
        }

        return contatosResult;
    }


    private Contato removeProtocolData(Contato contato) {
        contato.setApelido(contato.getApelido().split("!")[1]);
    }

    private List<Contato> removeProtocolData(List<Contato> contatos) {
        for (Contato contato : contatos) {
            removeProtocolData(contato);
        }

        return contatos;
    }
}
