package net.rl86.mdh.util;

import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class NumberInputDialog extends Dialog<Double> {

    /* ************************************************************************
     *
     * Fields
     *
     **************************************************************************/

    private final GridPane grid;
    private final Label label;
    private final NumberField numField;
    private final Double defaultValue;



    /* ************************************************************************
     *
     * Constructors
     *
     **************************************************************************/

    /**
     * Creates a new NumberInputDialog without a default value entered into the
     * dialog {@link TextField}.
     */
    public NumberInputDialog() {
        this(0.0);
    }

    /**
     * Creates a new NumberInputDialog with the default value entered into the
     * dialog {@link TextField}.
     * @param defaultValue the default value entered into the dialog
     */
    public NumberInputDialog(@NamedArg("defaultValue") Double defaultValue) {
        final DialogPane dialogPane = getDialogPane();

        this.numField = new NumberField(defaultValue);
        this.numField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(numField, Priority.ALWAYS);
        GridPane.setFillWidth(numField, true);

        label = new Label(dialogPane.getContentText());
        label.setPrefWidth(Region.USE_COMPUTED_SIZE);
        label.textProperty().bind(dialogPane.contentTextProperty());

        this.defaultValue = defaultValue;

        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);

        dialogPane.contentTextProperty().addListener(o -> updateGrid());
        dialogPane.getStyleClass().add("text-input-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        updateGrid();

        setResultConverter((dialogButton) -> {
            ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonData.OK_DONE ? Double.valueOf(numField.getText()) : null;
        });
    }



    /* ************************************************************************
     *
     * Public API
     *
     **************************************************************************/

    /**
     * Returns the {@link TextField} used within this dialog.
     * @return the {@link TextField} used within this dialog
     */
    public final NumberField getEditor() {
        return numField;
    }

    /**
     * Returns the default value that was specified in the constructor.
     * @return the default value that was specified in the constructor
     */
    public final Double getDefaultValue() {
        return defaultValue;
    }



    /* ************************************************************************
     *
     * Private Implementation
     *
     **************************************************************************/

    private void updateGrid() {
        grid.getChildren().clear();

        grid.add(label, 0, 0);
        grid.add(numField, 1, 0);
        getDialogPane().setContent(grid);

        Platform.runLater(() -> numField.requestFocus());
    }
}