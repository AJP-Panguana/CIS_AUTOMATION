package mz.co.maximo.cis.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Generic HTML table: read headers, rows, cells and act on a row by its text. */
public class Table extends BaseComponent {

    private static final By HEADERS = By.cssSelector("thead th");
    private static final By ROWS = By.cssSelector("tbody tr");
    private static final By CELLS = By.cssSelector("td");

    public Table(By root) {
        super(root);
    }

    public List<String> headers() {
        return findAll(HEADERS).stream().map(h -> h.getText().trim()).toList();
    }

    public int rowCount() {
        return rows().size();
    }

    /** Text of a cell; row and column start at 0. */
    public String cell(int row, int column) {
        return rows().get(row).findElements(CELLS).get(column).getText().trim();
    }

    public String cell(int row, String header) {
        int column = headers().indexOf(header);
        if (column < 0) {
            throw new IllegalArgumentException("No column '" + header + "'. Columns: " + headers());
        }
        return cell(row, column);
    }

    /** Index of the first row containing the text, or -1. */
    public int indexOfRowContaining(String text) {
        List<WebElement> rows = rows();
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getText().contains(text)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasRowContaining(String text) {
        return indexOfRowContaining(text) >= 0;
    }

    /** Clicks an element (e.g. edit/delete button) inside the row that contains the text. */
    public void clickInRow(String rowText, By action) {
        int index = indexOfRowContaining(rowText);
        if (index < 0) {
            throw new IllegalArgumentException("No row containing '" + rowText + "'");
        }
        rows().get(index).findElement(action).click();
    }

    private List<WebElement> rows() {
        return findAll(ROWS);
    }
}
