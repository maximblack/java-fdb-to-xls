package fdbtoxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import fdbtoxml.controllers.ControllerTable;

public class Main extends Application {

	public static final String DEFAULT_USERNAME = "SYSDBA";

	public static final String DEFAULT_PASSWORD = "masterkey";

	private static final String PATH_FXML = "fxml/";

	private static final String SCENE_CONNECT = "Connect.fxml";

	private static final String SCENE_TABLE = "Table.fxml";

	private static final String VALUE_MAIN_NAME = "FDB to XLS Converter";

	private static Database database;

	public static Stage connectStage;

	private static Stage tableStage;

	public static ControllerTable controllerTable;

	private static Image icon;

	public static String currentTable;

	@Override
	public void start(final Stage connectStage) {

		icon = new Image(getClass().getResourceAsStream("images/icon.png"));

		this.connectStage = connectStage;

		setScene(connectStage, SCENE_CONNECT);
		connectStage.setTitle(VALUE_MAIN_NAME + " - Connect to database");
		connectStage.setResizable(false);
		connectStage.getIcons().add(icon);
		connectStage.show();

		tableStage = new Stage();
		setScene(tableStage, SCENE_TABLE);
		tableStage.setTitle(VALUE_MAIN_NAME + " - Table view and export");
		tableStage.getIcons().add(icon);

		tableStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				connectStage.show();

			}
		});

	}

	public void setScene(Stage stage, String sceneName) {

		try {

			stage.setScene(new Scene((Parent) FXMLLoader.load(getClass()
					.getResource(PATH_FXML + sceneName))));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		Application.launch(args);

	}

	public static void setDatabase(String databaseFile,
			String databaseUsername, String databasePassword) {

		database = new Database(databaseFile, databaseUsername,
				databasePassword);

		tableStage.show();

	}

	public static void setControllerTable(ControllerTable ct) {

		controllerTable = ct;

		ObservableList<Object> data = FXCollections.observableArrayList();

	}
}
