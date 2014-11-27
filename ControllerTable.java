package fdbtoxml.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import fdbtoxml.Excel;
import fdbtoxml.Main;

public class ControllerTable implements Initializable {

	public TableView table;
	public Menu menuTables;
	public MenuItem menuExport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		menuExport.setDisable(true);

		Main.setControllerTable(this);

		menuExport.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fc = new FileChooser();

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
						"Excel (*.xls)", "*.xls");

				fc.getExtensionFilters().add(extFilter);

				File file = fc.showSaveDialog(null);

				if (file != null) {

					if (!file.getName().contains(".")) {
						file = new File(file.getAbsolutePath() + ".xls");
					}

					new Excel(file);

				}

			}

		});

	}
}
