package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArticuloView;

public class ControllerFrmStock {
    @FXML
    TableView<ArticuloView> tblArticulosStock;
    @FXML
    TableColumn<ArticuloView, String> colNroArticulo;
    @FXML
    TableColumn<ArticuloView, String> colNombreArticulo;
    @FXML
    TableColumn<ArticuloView, Integer> colUnidades;
    @FXML
    TableColumn<ArticuloView, Double> colValorizacion;
    @FXML
    Label lblStock;

    private SistemaStock sistema = null;
    private ObservableList<ArticuloView> articulos;

    public void initialize() {
        sistema = SistemaStock.getInstancia();

        articulos = FXCollections.observableArrayList();

        configurarTableViewMovimientosStock();
        cargarArticulos();

        lblStock.setText(String.valueOf(calcularValorTotal()));
    }

    private void configurarTableViewMovimientosStock() {
        colNroArticulo.setCellValueFactory(new PropertyValueFactory<>("nroArticulo"));
        colNombreArticulo.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
        colUnidades.setCellValueFactory(new PropertyValueFactory<>("stockUni"));
        colValorizacion.setCellValueFactory(new PropertyValueFactory<>("stockVal"));

        tblArticulosStock.setItems(articulos);
    }

    private void cargarArticulos() {
        articulos.clear();

        articulos.addAll(sistema.getArticulos());
    }

    private double calcularValorTotal() {
        double valorTotal = 0;

        for (ArticuloView art: articulos) {
            valorTotal = valorTotal + art.getStockVal();
        }

        return valorTotal;
    }
}
