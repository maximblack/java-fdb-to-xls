package fdbtoxml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel {

	private static final String SHEET_NAME = "First Sheet";

	public Excel(File file) {

		try {

			WritableWorkbook workbook = Workbook.createWorkbook(file);

			WritableSheet sheet = workbook.createSheet(SHEET_NAME, 0);

			ObservableList<TableColumn> tableColumns = Main.controllerTable.table
					.getColumns();

			int tableColumnsSize = tableColumns.size();

			for (int tableColumnIterator = 0; tableColumnIterator < tableColumnsSize; tableColumnIterator++) {

				TableColumn selectedColumn = tableColumns
						.get(tableColumnIterator);

				Label column = new Label(tableColumnIterator, 0,
						selectedColumn.getText());

				sheet.addCell(column);

			}

			ObservableList<ObservableList<String>> items = Main.controllerTable.table
					.getItems();

			Iterator<ObservableList<String>> columns = items.iterator();

			int row = 0;

			while (columns.hasNext()) {

				int column = 0;

				Iterator<String> rows = columns.next().iterator();

				while (rows.hasNext()) {

					String cell = rows.next();

					if (cell == null)
						cell = "";

					Label sheetRow = new Label(column, row + 1, cell);

					sheet.addCell(sheetRow);

					column++;

				}

				row++;

			}

			workbook.write();
			workbook.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}

	}

}