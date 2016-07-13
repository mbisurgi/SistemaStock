package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Articulo;
import model.ItemMargen;

import java.sql.Date;

public class ControllerFrmMargenes {
    @FXML
    TableView<Articulo> tblArticulosMargen;
    @FXML
    TableColumn<Articulo, String> colNroArticulo;
    @FXML
    TableColumn<Articulo, String> colNombreArticulo;
    @FXML
    TableColumn<Articulo, Double> colMargen;

    private SistemaStock sistema = null;
    private ObservableList<Articulo> articulos;

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
