package utils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * Creates a TableCell that contains a TextArea similar to a TextFieldTableCell.
 * <p>
 * Originally created by eckig: https://gist.github.com/eckig/30abf0d7d51b7756c2e7
 * <p>
 * Adapted from the original TextAreaTableCell by Brad Turek.
 *
 * @param <T>
 */
@SuppressWarnings("WeakerAccess")
public class TextAreaListCell<T> extends ListCell<T> {

    public static Callback<ListView<String>, ListCell<String>> forListView() {
        return forListView(new DefaultStringConverter());
    }

    public static <T> Callback<ListView<T>, ListCell<T>> forListView(final StringConverter<T> converter) {
        return list -> new TextAreaListCell<>(converter);
    }

    private static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
        return converter == null ? cell.getItem() == null ? "" : cell.getItem()
                .toString() : converter.toString(cell.getItem());
    }

    private static <T> TextArea createTextArea(final Cell<T> cell, final StringConverter<T> converter) {
        TextArea textArea = new TextArea(getItemText(cell, converter));
        textArea.setWrapText(false);
        textArea.setPrefRowCount(2);


        textArea.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                cell.cancelEdit();
                t.consume();
            } else if (t.getCode() == KeyCode.ENTER && t.isShiftDown()) {
                t.consume();
                textArea.insertText(textArea.getCaretPosition(), "\n");
            } else if (t.getCode() == KeyCode.ENTER) {
                if (converter == null) {
                    throw new IllegalStateException(
                            "Attempting to convert text input into Object, but provided "
                                    + "StringConverter is null. Be sure to set a StringConverter "
                                    + "in your cell factory.");
                }
                cell.commitEdit(converter.fromString(textArea.getText()));
                t.consume();
            }
        });
        return textArea;
    }

    private void startEdit(final Cell<T> cell, final StringConverter<T> converter) {
        textArea.setText(getItemText(cell, converter));

        cell.setText(null);
        cell.setGraphic(textArea);

        // Make sure the text area stays the right size:
        /* The following solution is courtesy of James_D: https://stackoverflow.com/a/22733264/5432315 */
        // Perform a lookup for an element with a css class of "text"
        // This will give the Node that actually renders the text inside the
        // TextArea
        Node text = textArea.lookup(".text");
        // Bind the preferred height of the text area to the actual height of the text
        // This will make the text area the height of the text, plus some padding
        // of 20 pixels, as long as that height is between the text area's minHeight
        // and maxHeight. The max height will be the height of its parent (usually).
        if (text != null) {

            textArea.prefHeightProperty().bind(Bindings.createDoubleBinding(() ->
                    text.getBoundsInLocal().getHeight(), text.boundsInLocalProperty()).add(20)
            );
        }


        textArea.selectAll();
        textArea.requestFocus();
    }

    private static <T> void cancelEdit(Cell<T> cell, final StringConverter<T> converter) {
        cell.setText(getItemText(cell, converter));
        cell.setGraphic(null);
    }

    private void updateItem(final Cell<T> cell, final StringConverter<T> converter) {

        if (cell.isEmpty()) {
            cell.setText(null);
            cell.setGraphic(null);
            cell.setTooltip(null);

        } else {
            if (cell.isEditing()) {
                if (textArea != null) {
                    textArea.setText(getItemText(cell, converter));
                }
                cell.setText(null);
                cell.setGraphic(textArea);
                cell.setTooltip(null);
            } else {
                cell.setText(getItemText(cell, converter));
                cell.setGraphic(null);

                //Add text as tooltip so that user can read text without editing it.
                Tooltip tooltip = new Tooltip(getItemText(cell, converter));
                tooltip.setWrapText(true);
                tooltip.prefWidthProperty().bind(cell.widthProperty());
                cell.setTooltip(tooltip);
            }
        }
    }

    private TextArea textArea;
    private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<>(this, "converter");

    public TextAreaListCell() {
        this(null);
    }

    public TextAreaListCell(StringConverter<T> converter) {
        this.getStyleClass().add("text-area-table-cell");
        setConverter(converter);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override
    public void startEdit() {
        if (!isEditable()) {
            return;
        }

        super.startEdit();

        if (isEditing()) {
            if (textArea == null) {
                textArea = createTextArea(this, getConverter());
            }

            startEdit(this, getConverter());
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        cancelEdit(this, getConverter());
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        updateItem(this, getConverter());
    }

}
