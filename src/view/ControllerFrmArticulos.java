package view;

import controller.SistemaStock;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Articulo;

public class ControllerFrmArticulos {
    private SistemaStock sistema = null;

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
    ComboBox<String> cboValorizacion;

    public void initialize() {
        sistema = SistemaStock.getInstancia();

        cargarComboBox();
    }

    private void cargarComboBox() {
        cboValorizacion.getItems().addAll("PEPS", "UEPS", "UCPA", "PPP");
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
        }
        else {
            lblNro.setText("invalido");
            lblNro.setTextFill(Color.RED);
            lblNombre.setText("invalido");
            lblNombre.setTextFill(Color.RED);
            lblCantidad.setText("");
            lblValorizado.setText("");
            cboValorizacion.setDisable(true);
        }
    }

    public void lblBuscarOnMouseEntered() {
        lblBuscar.setUnderline(true);
    }

    public void lblBuscarOnMouseExited() {
        lblBuscar.setUnderline(false);
    }
}
