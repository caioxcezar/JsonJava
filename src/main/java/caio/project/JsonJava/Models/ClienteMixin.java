/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caio.project.JsonJava.Models;

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
            @JsonProperty("assistencias") List<String> assistencias) {

        System.out.println("Wont be called");

    }
}
