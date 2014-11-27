package fdbtoxml.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import fdbtoxml.Main;

public class ControllerConnect implements Initializable {

	public CheckBox checkDefaults;
	public TextField fieldUsername;
	public PasswordField fieldPassword;
	public Button buttonDatabase;
	public TextField fieldDatabase;
	public Button buttonConnect;
	public Label alertEmpty;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		checkDefaults.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (checkDefaults.isSelected()) {
					fieldUsername.setText(Main.DEFAULT_USERNAME);
					fieldUsername.setEditable(false);
					fieldPassword.setText(Main.DEFAULT_PASSWORD);
					fieldPassword.setEditable(false);
				} else {
					fieldUsername.setText("");
					fieldUsername.setEditable(true);
					fieldPassword.setText("");
					fieldPassword.setEditable(true);
				}

			}

		});

		buttonDatabase.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				FileChooser fc = new FileChooser();

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
						"Firebird Database (*.fdb)", "*.fdb");
				fc.getExtensionFilters().add(extFilter);

				File file = fc.showOpenDialog(null);

				if (file != null)
					fieldDatabase.setText(file.getAbsolutePath());

			}

		});

		buttonConnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String databaseFile = fieldDatabase.getText();
				String databaseUsername = fieldUsername.getText();
				String databasePassword = fieldPassword.getText();

				if (!databaseFile.isEmpty() && !databaseUsername.isEmpty()
						&& !databasePassword.isEmpty()) {

					Main.setDatabase(databaseFile, databaseUsername,
							databasePassword);

					Main.connectStage.hide();

				} else {

					alertEmpty.setVisible(true);

					FadeTransition ft = new FadeTransition(Duration
							.millis(4000), alertEmpty);
					ft.setFromValue(1.0);
					ft.setToValue(0.0);
					ft.play();

				}
			}

		});

	}
}
