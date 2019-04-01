package caio.project.JsonJava;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;

public class FXMLController implements Initializable {

    @FXML
    private TextField txtNome, txtSerie, txtBSerie, txtBNome;
    @FXML
    private TextArea txtInteracoes;
    @FXML
    private TreeTableView<String> tabela;
    private List<Cliente> listaClientes = new ArrayList<Cliente>();
    private String caminhoArquivo = "/home/ccezar/Assistencia.json";

    @FXML
    private void buscar(ActionEvent event) {
        if (!"".equals(txtBSerie.getText()) || !"".equals(txtBNome.getText())) {
            int nSerie = 0;
            if (!"".equals(txtBSerie.getText())) {
                try {
                    nSerie = Integer.parseInt(txtBSerie.getText());
                } catch (NumberFormatException e) {
                    nSerie = 0;
                }
            }
            carregarTabela(nSerie, txtBNome.getText());
        } else {
            carregarTabela();
        }
    }

    @FXML
    private void Adicionar(ActionEvent event) {
        String nome = txtNome.getText();
        String nSerie = txtSerie.getText();
        String interacao = txtInteracoes.getText();
        if (nome.isBlank() || nSerie.isBlank() || interacao.isBlank()) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            String camposVazios = "";
            alerta.setTitle("Prenxa todos os campos");
            alerta.setContentText(null);
            alerta.setHeaderText("Campos vazios: ");
            if (nome.isBlank()) {
                camposVazios += "Nome\n";
            }
            if (nSerie.isBlank()) {
                camposVazios += "Número de Serie\n";
            }
            if (interacao.isBlank()) {
                camposVazios += "Interações";
            }
            alerta.setContentText(camposVazios);
            alerta.showAndWait();
        } else {
            JsonParser js = new JsonParser();
            List<Integer> ListaSerie = new ArrayList<Integer>();
            for (String valor : Arrays.asList(nSerie.split(","))) {
                ListaSerie.add(Integer.parseInt(valor));
            }
            Cliente cli = new Cliente(ListaSerie, nome, interacao);
            listaClientes.add(cli);
            js.objToJsonFile(listaClientes, caminhoArquivo);
            carregarTabela();
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Campos OK");
            alerta.setContentText("OK");
            alerta.showAndWait();
        }

    }

    @FXML
    private void Alterar(ActionEvent event) {
        System.out.println("Alterar");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JsonParser js = new JsonParser();
        listaClientes = js.jsonToObj(caminhoArquivo);
        carregarTabela();
    }

    private void tabelaExemplo() {
        TreeItem<String> childNode1 = new TreeItem<>("Node 1");
        TreeItem<String> childNode2 = new TreeItem<>("Node 2");
        TreeItem<String> childNode3 = new TreeItem<>("Node 3");

        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);

        root.getChildren().setAll(childNode1, childNode2, childNode3);

        TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
        column.setPrefWidth(150);

        column.setCellValueFactory((CellDataFeatures<String, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue()));

        tabela.setRoot(root);
        tabela.getColumns().add(column);
    }

    private void carregarTabela() {
        tabela.getColumns().clear();
        TreeItem<String> root = new TreeItem<>("Clientes");
        for (Cliente cli : listaClientes) {
            TreeItem<String> cliTree = new TreeItem<>(cli.getNome());
            cliTree.getChildren().add(new TreeItem<>(cli.getnSerie().toString()));
            cliTree.getChildren().add(new TreeItem<>(cli.getAssistencias()));
            cliTree.setExpanded(true);
            root.getChildren().add(cliTree);
        }
        TreeTableColumn<String, String> column = new TreeTableColumn<>("Clientes");
        column.setPrefWidth(150);

        column.setCellValueFactory((CellDataFeatures<String, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue()));

        tabela.setRoot(root);
        tabela.getColumns().add(column);
    }

    private void carregarTabela(int nSerie, String nome) {
        tabela.getColumns().clear();
        TreeItem<String> root = new TreeItem<>("Clientes");
        
        for (Cliente cli : listaClientes) {
            if (nSerie != 0) {
                List<Integer> nseries = cli.getnSerie();
                for (int n : nseries) {
                    if (n == nSerie) {
                        TreeItem<String> cliTree = new TreeItem<>(cli.getNome());
                        cliTree.getChildren().add(new TreeItem<>(cli.getnSerie().toString()));
                        cliTree.getChildren().add(new TreeItem<>(cli.getAssistencias()));
                        cliTree.setExpanded(true);
                        root.getChildren().add(cliTree);
                    }
                }
            } else if (!"".equals(nome)) {
                if (nome.equals(cli.getNome())) {
                    TreeItem<String> cliTree = new TreeItem<>(cli.getNome());
                    cliTree.getChildren().add(new TreeItem<>(cli.getnSerie().toString()));
                    cliTree.getChildren().add(new TreeItem<>(cli.getAssistencias()));
                    cliTree.setExpanded(true);
                    root.getChildren().add(cliTree);
                }
            }

        }
        TreeTableColumn<String, String> column = new TreeTableColumn<>("Clientes");
        column.setPrefWidth(150);

        column.setCellValueFactory((CellDataFeatures<String, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue()));

        tabela.setRoot(root);
        tabela.getColumns().add(column);
    }
}
