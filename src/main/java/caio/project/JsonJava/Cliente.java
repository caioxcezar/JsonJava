package caio.project.JsonJava;

import java.util.List;

public class Cliente {

    private List<Integer> nSerie;
    private String nome;
    private String assistencias;
    /**
     * @param nSerie: coloca o valor do numero de serie
     * @param nome: coloca o valor do nome
     * @param assistencias: coloca o valores das listas de assistencias
     */
    public Cliente(List<Integer> nSerie, String nome, String assistencias){
        this.nSerie = nSerie;
        this.nome = nome;
        this.assistencias = assistencias;
    }

    /**
     * @return retorna o numero de serie
     */
    public List<Integer> getnSerie() {
        return nSerie;
    }

    /**
     * @param nSerie: coloca o valor do numero de serie
     */
    public void setnSerie(List<Integer> nSerie) {
        this.nSerie = nSerie;
    }

    /**
     * @return retorna o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome: coloca o valor do nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the assistencias
     */
    public String getAssistencias() {
        return assistencias;
    }

    /**
     * @param assistencias the assistencias to set
     */
    public void setAssistencias(String assistencias) {
        this.assistencias = assistencias;
    }
}
