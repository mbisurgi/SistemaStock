package view;

import controller.SistemaStock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ArticuloView;
import model.strategy.*;

import java.sql.Date;

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
    Button btnPeps;
    @FXML
    Button btnUeps;
    @FXML
    Button btnPpp;
    @FXML
    Button btnUcpa;
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

    private void cargarArticulosValorizado(Valorizacion valorizacion) {
        articulos.clear();

        articulos.addAll(sistema.getArticulosValorizados(valorizacion));
    }

    private double calcularValorTotal() {
        double valorTotal = 0;

        for (ArticuloView art: articulos) {
            valorTotal = valorTotal + art.getStockVal();
        }

        return valorTotal;
    }

    public void btnPepsOnMouseClicked() {
        cargarArticulosValorizado(new ValorizacionPEPS());

        lblStock.setText(String.valueOf(calcularValorTotal()));
    }

    public void btnUepsOnMouseClicked() {
        cargarArticulosValorizado(new ValorizacionUEPS());

        lblStock.setText(String.valueOf(calcularValorTotal()));
    }

    public void btnPppOnMouseClicked() {
        cargarArticulosValorizado(new ValorizacionPPP());

        lblStock.setText(String.valueOf(calcularValorTotal()));
    }

    public void btnUcpaOnMouseClicked() {
        cargarArticulosValorizado(new ValorizacionUCPA());

        lblStock.setText(String.valueOf(calcularValorTotal()));
    }
}
