package caio.project.JsonJava;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController implements Initializable {

    @FXML
    private TextField txtNome, txtSerie, txtBSerie, txtBNome;
    @FXML
    private TextArea txtInteracoes;
    @FXML
    private TableView<Cliente> tabela;
    private List<Cliente> listaClientes = new ArrayList<Cliente>();
    private String caminhoArquivo = "Assistencia.json";
    private Cliente cliSelecionado;

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
            List<Cliente> listaTabela = new ArrayList<Cliente>();
            if (nSerie != 0) {
                for (Cliente cli : listaClientes) {
                    if (cli.getnSerie().contains(nSerie)) {
                        listaTabela.add(cli);
                    }
                }
            } else if (txtBNome.getText() != "") {
                for (Cliente cli : listaClientes) {
                    if (cli.getNome().toLowerCase().contains(txtBNome.getText().toLowerCase())) {
                        listaTabela.add(cli);
                    }
                }
            }
            carregarTabela(listaTabela);
        } else {
            carregarTabela(listaClientes);
        }
    }

    @FXML
    private void Excluir(ActionEvent event) {
        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Exclusão");
        alerta.setHeaderText("Deseja excluir o cliente?");
        alerta.setContentText("Nome: " + cliSelecionado.getNome() + "\n"
                + "nSerie: " + cliSelecionado.getnSerie() + "\n"
                + "Assistencias: " + cliSelecionado.getAssistencias());
        var resposta = alerta.showAndWait();
        if (resposta.get() == ButtonType.OK) {
            if (cliSelecionado != null) {
                listaClientes.remove(cliSelecionado);
                carregarTabela(listaClientes);
                JsonParser js = new JsonParser();
                js.objToJsonFile(listaClientes, caminhoArquivo);
            }
        }
    }

    @FXML
    private void tabelaMouseClicked() {
        if (tabela.getSelectionModel().getSelectedIndex() != -1) {
            Cliente cli = tabela.getSelectionModel().getSelectedItem();
            txtNome.setText(cli.getNome());
            txtSerie.setText(cli.getnSerie().toString().replace("[", "").replace("]", "").replace(" ", ""));
            txtInteracoes.setText(cli.getAssistencias().toString().replace("[", "").replace("]", ""));
            cliSelecionado = cli;
        }
    }

    @FXML
    private void Adicionar(ActionEvent event) {
        String nome = txtNome.getText();
        String nSerie = txtSerie.getText();
        List<String> interacao = Arrays.asList(txtInteracoes.getText().split(","));
        if (nome.isBlank() || nSerie.isBlank() || interacao.size() <= 0) {
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
            if (interacao.size() <= 0) {
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
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Cadastro feito com sucesso");
            alerta.setHeaderText("Cadastro do Cliente " + cli.getNome() + " feito com sucesso");
            alerta.setContentText("Nome: " + cli.getNome() + "\n"
                    + "nSerie: " + cli.getnSerie() + "\n"
                    + "Assistencias: " + cli.getAssistencias());
            alerta.showAndWait();
            carregarTabela(listaClientes);
        }
    }

    @FXML
    private void Alterar(ActionEvent event) {
        if (cliSelecionado != null) {
            for (int i = 0; i < listaClientes.size(); i++) {
                if (listaClientes.get(i) == cliSelecionado) {
                    String nome = txtNome.getText();
                    String nSerie = txtSerie.getText();
                    List<String> interacao = Arrays.asList(txtInteracoes.getText().split(","));
                    List<Integer> ListaSerie = new ArrayList<Integer>();
                    for (String valor : Arrays.asList(nSerie.split(","))) {
                        ListaSerie.add(Integer.parseInt(valor));
                    }
                    listaClientes.set(i, new Cliente(ListaSerie, nome, interacao));
                    carregarTabela(listaClientes);
                    JsonParser js = new JsonParser();
                    js.objToJsonFile(listaClientes, caminhoArquivo);
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Alterar");
                    alerta.setHeaderText("Alterado com sucesso");
                    alerta.setContentText("Cliente " + cliSelecionado.getNome() + " alterado com sucesso");
                    alerta.showAndWait();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JsonParser js = new JsonParser();
        for (Cliente cli : js.jsonToObj(caminhoArquivo)) {
            listaClientes.add(cli);
        }
        carregarTabela(listaClientes);
    }

    private void carregarTabela(List<Cliente> cli) {
        tabela.getColumns().clear();

        TableColumn<Cliente, String> colCli = new TableColumn<>("Clientes");
        TableColumn<Cliente, String> colSerie = new TableColumn<>("N. Serie");
        TableColumn<Cliente, String> colAss = new TableColumn<>("Assistencias");

        colCli.setPrefWidth(70.0);
        colSerie.setPrefWidth(70.0);
        colAss.setPrefWidth(140.0);

        colCli.setCellValueFactory(
                new PropertyValueFactory<>("nome"));
        colSerie.setCellValueFactory(
                (TableColumn.CellDataFeatures<Cliente, String> p)
                -> {
            List<Integer> lista = p.getValue().getnSerie();
            String val = lista.toString().replace("[", "").replace("]", "").replace(",", "\n");
            return new ReadOnlyStringWrapper(val);
        });
        colAss.setCellValueFactory(
                (TableColumn.CellDataFeatures<Cliente, String> p)
                -> {
            List<String> lista = p.getValue().getAssistencias();
            String val = lista.toString().replace("[", "").replace("]", "");
            return new ReadOnlyStringWrapper(val);
        });

        ObservableList<Cliente> obList = FXCollections.observableArrayList(cli);

        tabela.setItems(obList);

        tabela.getColumns().setAll(colCli, colSerie, colAss);
    }
    /*
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
            cliTree.setExpanded(false);
            root.getChildren().add(cliTree);
        }
        TreeTableColumn<String, String> colCli = new TreeTableColumn<>("Clientes");
        TreeTableColumn<String, String> colAss = new TreeTableColumn<>("Assistencias");
        colCli.setPrefWidth(150);

        colCli.setCellValueFactory((CellDataFeatures<String, String> p) -> new ReadOnlyStringWrapper(
                p.getValue().getValue()));
        root.setExpanded(true);
        tabela.setRoot(root);
        tabela.getColumns().add(colCli);
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
                        cliTree.setExpanded(false);
                        root.getChildren().add(cliTree);
                    }
                }
            } else if (!"".equals(nome)) {
                if (nome.equals(cli.getNome())) {
                    TreeItem<String> cliTree = new TreeItem<>(cli.getNome());
                    cliTree.getChildren().add(new TreeItem<>(cli.getnSerie().toString()));
                    cliTree.getChildren().add(new TreeItem<>(cli.getAssistencias()));
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
     */
}
