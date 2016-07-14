package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArticuloView;

import java.sql.Date;

public class ControllerFrmMargenes {
    @FXML
    TableView<ArticuloView> tblArticulosMargen;
    @FXML
    TableColumn<ArticuloView, String> colNroArticulo;
    @FXML
    TableColumn<ArticuloView, String> colNombreArticulo;
    @FXML
    TableColumn<ArticuloView, Double> colMargen;
    @FXML
    Label lblMargen;
    @FXML
    DatePicker dtpDesde;
    @FXML
    DatePicker dtpHasta;
    @FXML
    Button btnVer;

    private SistemaStock sistema = null;
    private ObservableList<ArticuloView> articulos;

    public void initialize() {
        sistema = SistemaStock.getInstancia();

        articulos = FXCollections.observableArrayList();

        configurarTableViewMovimientosStock();
        cargarArticulos();

        lblMargen.setText(String.valueOf(calcularMargenTotal()));
    }

    private void configurarTableViewMovimientosStock() {
        colNroArticulo.setCellValueFactory(new PropertyValueFactory<>("nroArticulo"));
        colNombreArticulo.setCellValueFactory(new PropertyValueFactory<>("nombreArticulo"));
        colMargen.setCellValueFactory(new PropertyValueFactory<>("margen"));

        tblArticulosMargen.setItems(articulos);
    }

    private void cargarArticulos() {
        articulos.clear();

        articulos.addAll(sistema.getArticulos());
    }

    private double calcularMargenTotal() {
        double margenTotal = 0;

        for (ArticuloView art: articulos) {
            margenTotal = margenTotal + art.getMargen();
        }

        return margenTotal;
    }

    public void btnVerOnMouseClicked() {
        articulos.clear();

        articulos.addAll(sistema.getArticulosFecha((Date.valueOf(dtpDesde.getValue())), Date.valueOf(dtpHasta.getValue())));

        lblMargen.setText(String.valueOf(calcularMargenTotal()));
    }
}
