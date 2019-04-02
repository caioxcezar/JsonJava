/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caio.project.JsonJava;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author ccezar
 */
public class JsonParser {

    public void objToJsonFile(List<Cliente> cli, String caminho) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(caminho), cli);
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro na conversão");
            alerta.setHeaderText("Erro na classe objToJsonFile");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    public String objToJson(Cliente cli) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(cli);
            return jsonInString;
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro na conversão");
            alerta.setHeaderText("Erro na classe objToJson");

            TextArea textArea = new TextArea(e.getMessage());
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(new Label("Mensagem do erro: "), 0, 0);
            expContent.add(textArea, 0, 1);

            alerta.getDialogPane().setExpandableContent(expContent);

            alerta.showAndWait();
        }
        return "";
    }

    public List<Cliente> jsonToObj(String json) {
        try {
            //String conteudo = new String(Files.readAllBytes(Paths.get(json)));
            File file = new File(json);
            ObjectMapper mapper = new ObjectMapper();
            //Cliente[] obj = mapper.readValue(file, Cliente[].class);

            mapper.addMixIn(Cliente.class, ClienteMixin.class);

            Cliente[] obj = mapper.readValue(file, Cliente[].class);

            return Arrays.asList(obj);
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro na conversão");
            alerta.setHeaderText("Erro na classe jsonToObj");

            TextArea textArea = new TextArea(e.getMessage());
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(new Label("Mensagem do erro: "), 0, 0);
            expContent.add(textArea, 0, 1);

            alerta.getDialogPane().setExpandableContent(expContent);
            alerta.showAndWait();
        }
        return new ArrayList<Cliente>();
    }

    public void printJson(Cliente cli) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cli);
            System.out.println(json);
        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro na conversão printJson");
            alerta.setHeaderText("Erro na classe printJson");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    private static ObjectMapper buildMapper() {

        ObjectMapper mapper = new ObjectMapper();

        mapper.setVisibility(mapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

        return mapper;
    }
}
