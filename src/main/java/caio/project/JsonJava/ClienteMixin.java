/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caio.project.JsonJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author ccezar
 */
public abstract class ClienteMixin {

    @JsonCreator
    public ClienteMixin(
            @JsonProperty("nSerie") List<Integer> nSerie,
            @JsonProperty("nome") String nome,
            @JsonProperty("assistencias") String assistencias) {

        System.out.println("Wont be called");

    }
}
