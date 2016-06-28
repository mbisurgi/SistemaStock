package view;

import controller.SistemaStock;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ControllerFrmPrincipal {
    @FXML
    Button btnSyncArticulos;
    @FXML
    Button btnSyncComprobantes;

    public void initialize() {

    }

    public void btnSyncArticulosOnClick() {
        try {
            SistemaStock.getInstancia().sincronizarArticulos();

            mostrarMensaje("Articulos");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    public void btnSyncComprobantesOnClick() {
        try {
            SistemaStock.getInstancia().sincronizarComprobantes();

            mostrarMensaje("Comprobantes");
        } catch (Exception ex) {
            mostrarError(ex);
        }
    }

    private void mostrarMensaje(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sincronizacion Exitosa");
        alert.setHeaderText(null);
        alert.setContentText(msg + " sincronizados correctamente");
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
