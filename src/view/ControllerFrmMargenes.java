package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private SistemaStock sistema = null;
    private ObservableList<ArticuloView> articulos;

    public void initialize() {
        sistema = SistemaStock.getInstancia();

        articulos = FXCollections.observableArrayList();

        configurarTableViewMovimientosStock();
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
}
