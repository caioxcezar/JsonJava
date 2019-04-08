package caio.project.JsonJava.Models;

import java.util.Date;

public class Assistencia {
    private Date data;
    private String problema;
    private String solucao;

    /**
     * @return retorna a data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data: coloca valor da data
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return retorna o problema
     */
    public String getProblema() {
        return problema;
    }

    /**
     * @param problema: coloca valor do problema
     */
    public void setProblema(String problema) {
        this.problema = problema;
    }

    /**
     * @return retorna a solução
     */
    public String getSolucao() {
        return solucao;
    }

    /**
     * @param solucao: coloca valor da solução
     */
    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

}
