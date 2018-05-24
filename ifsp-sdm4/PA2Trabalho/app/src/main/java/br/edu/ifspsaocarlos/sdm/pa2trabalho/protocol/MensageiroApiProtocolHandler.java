package br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class MensageiroApiProtocolHandler {

    /*

    CONTATO
     - nome[100]
     - apelido [100]

     protocolo apelido[50]
     '#PT' - id do app
     'C' ou 'G' - Identifica se eh Contato ou Grupo
     'FF' - ID do grupo
     'FF...' Id gos grupos que o contato participa (10 grupos)


    MENSAGEM
     - assunto [50] - Protocolo
     - corpo [150]

     protocolo assunto[50]
     '#PT' - id do app
     'C'/'F' - Mensagem completa ou fracionada
     '123456' - Id mensagem anterior

     */
}
