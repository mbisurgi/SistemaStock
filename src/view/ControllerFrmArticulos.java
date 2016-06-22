package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.Articulo;
import model.ItemStock;
import model.strategy.*;
import java.sql.Date;

public class ControllerFrmArticulos {
    @FXML
    TextField txtNroArticulo;
    @FXML
    Label lblBuscar;
    @FXML
    Label lblNro;
    @FXML
    Label lblNombre;
    @FXML
    Label lblCantidad;
    @FXML
    Label lblValorizado;
    @FXML
    ComboBox<Valorizacion> cboValorizacion;
    @FXML
    Label lblMargen;
    @FXML
    TableView<ItemStock> tblMovimientosStock;
    @FXML
    TableColumn<ItemStock, Integer> colId;
    @FXML
    TableColumn<ItemStock, Date> colFecha;
    @FXML
    TableColumn<ItemStock, Integer> colCantidad;
    @FXML
    TableColumn<ItemStock, Double> colPrecio;
    @FXML
    TableColumn<ItemStock, Integer> colDisponible;

    private SistemaStock sistema = null;
    private ObservableList<ItemStock> movimientosStock;

    public void initialize() {
        sistema = SistemaStock.getInstancia();

        movimientosStock = FXCollections.observableArrayList();

        cargarComboBox();
        configurarTableViewMovimientosStock();
    }

    private void configurarTableViewMovimientosStock() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idItem"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("cantidadDisponible"));

        tblMovimientosStock.setItems(movimientosStock);
    }

    private void cargarComboBox() {
        cboValorizacion.getItems().addAll(new ValorizacionPEPS(), new ValorizacionUEPS(), new ValorizacionUCPA(), new ValorizacionPPP());
    }

    private void cargarMovimientosStock(Articulo art) {
        movimientosStock.clear();

        movimientosStock.addAll(art.getStock().getItems());
    }

    public void lblBuscarOnMouseClicked() {
        Articulo art = sistema.buscarArticulo(txtNroArticulo.getText());

        if (art != null) {
            lblNro.setText(art.getNroArticulo());
            lblNro.setTextFill(Color.BLACK);
            lblNombre.setText(art.getNombreArticulo());
            lblNombre.setTextFill(Color.BLACK);
            lblCantidad.setText(String.valueOf(art.getStock().getCantidad()));
            lblValorizado.setText(String.valueOf(art.getStock().valorizar()));
            cboValorizacion.setDisable(false);
            cboValorizacion.getSelectionModel().clearSelection();
            lblMargen.setText(String.valueOf(art.getMargen().margen()));
            cargarMovimientosStock(art);
        }
        else {
            lblNro.setText("invalido");
            lblNro.setTextFill(Color.RED);
            lblNombre.setText("invalido");
            lblNombre.setTextFill(Color.RED);
            lblCantidad.setText("");
            lblValorizado.setText("");
            cboValorizacion.setDisable(true);
            cboValorizacion.getSelectionModel().clearSelection();
            lblMargen.setText("");
        }
    }

    public void lblBuscarOnMouseEntered() {
        lblBuscar.setUnderline(true);
    }

    public void lblBuscarOnMouseExited() {
        lblBuscar.setUnderline(false);
    }

    public void cboValorizacionOnItemChanged() {
        Articulo art = sistema.buscarArticulo(txtNroArticulo.getText());

        if (art != null && cboValorizacion.getSelectionModel().getSelectedItem() != null) {
            art.getStock().setValorizacion(cboValorizacion.getSelectionModel().getSelectedItem());
            lblValorizado.setText(String.valueOf(art.getStock().valorizar()));
        }
    }
}
