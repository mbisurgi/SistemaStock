package view;

import controller.SistemaStock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ControllerFrmPrincipal {
    @FXML
    Button btnSyncArticulos;
    @FXML
    Button btnSyncComprobantes;
    @FXML
    Button btnIngresoStock;
    @FXML
    Button btnEgresoStock;
    @FXML
    Button btnMargenPrecio;
    @FXML
    MenuItem menuConsultaArticulo;

    public void initialize() {

    }

    public void btnSyncArticulosOnClick() {
        try {
            SistemaStock.getInstancia().sincronizarArticulos();

            mostrarMensaje("Sincronizacion Exitosa", "Articulos sincronizados correctamente");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    public void btnSyncComprobantesOnClick() {
        try {
            SistemaStock.getInstancia().sincronizarComprobantes();

            mostrarMensaje("Sincronizacion Exitosa", "Comprobantes sincronizados correctamente");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    public void btnIngresoStockOnClick() {
        try {
            SistemaStock.getInstancia().ingresarStock();

            mostrarMensaje("Procesado", "Ingreso de stock generado correctamente");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    public void btnEgresoStockOnClick() {
        SistemaStock.getInstancia().egresarStock();
    }

    public void btnMargenPrecioOnClick() {

    }

    public void menuConsultaArticuloOnClick(ActionEvent event) {
        loadFrmArticulos(event);
    }

    private void loadFrmArticulos(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("FrmArticulos.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Articulos");
            stage.setScene(new Scene(parent, 600, 500));
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            mostrarError(ex);
        }
    }

    private void mostrarMensaje(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostrarError(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Mensaje de Error");
        alert.setHeaderText("Se ha producido una excepcion");
        alert.setContentText(ex.toString());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("El seguimiento de la excepcion es:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
