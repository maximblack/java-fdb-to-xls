package fdbtoxml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import org.firebirdsql.jdbc.field.TypeConversionException;

public class Database {

	private Connection connection;

	private final String DB_DRIVER = "org.firebirdsql.jdbc.FBDriver";

	private final String DB_NAME = "jdbc:firebirdsql:localhost/3050:";

	private static final String DB_USERNAME = "SYSDBA";

	private static final String DB_PASSWORD = "masterkey";

	public Database(String databaseFile) {

		this(databaseFile, DB_USERNAME, DB_PASSWORD);

	}

	public Database(String databaseFile, String databaseUsername,
			String databasePassword) {

		databaseFile = DB_NAME + databaseFile;

		try {

			Class.forName(DB_DRIVER);

			connection = DriverManager.getConnection(databaseFile,
					databaseUsername, databasePassword);

			// Important !!!
			connection.setAutoCommit(false);

			setMenuDatabaseTables();

		} catch (ClassNotFoundException ex) {

			ex.printStackTrace();

		} catch (SQLException e) {

			System.err.println("Cannot connect to this database.");

			e.printStackTrace();

		}
	}

	private Database setTable(String query) {

		Main.controllerTable.table.getColumns().clear();

		ResultSet rs = databaseQuery(query);

		if (rs == null) {

			Main.controllerTable.menuExport.setDisable(true);

			return this;

		}

		Main.controllerTable.menuExport.setDisable(false);

		try {

			ObservableList<ObservableList> data = FXCollections
					.observableArrayList();

			ResultSetMetaData rsmd = rs.getMetaData();

			int columns = rsmd.getColumnCount();

			for (int column = 0; column < columns; column++) {

				final int j = column;

				TableColumn tableColumn = new TableColumn(
						rsmd.getColumnName(column + 1));

				tableColumn.prefWidthProperty().bind(
						Main.controllerTable.table.widthProperty().divide(
								columns));

				tableColumn
						.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {

							@Override
							public ObservableValue<String> call(
									CellDataFeatures<ObservableList, String> param) {

								String s;

								try {

									Object value = param.getValue().get(j);

									s = value.toString();

								} catch (IndexOutOfBoundsException
										| NullPointerException e) {

									s = "";

								}

								return new SimpleStringProperty(s);

							}

						});

				Main.controllerTable.table.getColumns().addAll(tableColumn);

			}

			while (rs.next()) {

				ObservableList<String> row = FXCollections
						.observableArrayList();

				for (int column = 1; column <= columns; column++) {

					row.add(rs.getString(column));

				}

				data.add(row);

			}

			Main.controllerTable.table.setItems(data);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return this;

	}

	private Database setMenuDatabaseTables() {

		ArrayList<Query> queries = Query.getQueries();

		Iterator<Query> iterator = queries.iterator();

		while (iterator.hasNext()) {

			MenuItem mi = new MenuItem(iterator.next().queryName);

			mi.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					MenuItem mi = (MenuItem) event.getSource();

					setTable(Query.getQuery(mi.getText()));

				}
			});

			Main.controllerTable.menuTables.getItems().add(mi);

		}

		return this;

	}

	private ResultSet databaseQuery(String query) {

		try {

			return connection.createStatement().executeQuery(query);

		} catch (TypeConversionException e) {

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
}
