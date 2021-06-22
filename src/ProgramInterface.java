import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProgramInterface extends Application {

    //TODO настроить внешний вид окна человеческий
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    Group root = new Group();
    VBox fields = new VBox();
    HBox sections = new HBox();
    TextField name = new TextField();
    TextField mainText = new TextField();
    TextField dateOfAlarm = new TextField();
    TextField contacts = new TextField();
    Button buttonAdd = new Button("Add");
    Button buttonUpdate = new Button("Update");
    Button buttonDelete = new Button("Delete");

    final private int WIDTH = 1000;
    final private int HEIGHT = 600;

    Journal journal = new Journal();
    Alarms alarms = new Alarms();

    private Task selectedTask;

    public void start(Stage primaryStage) throws Exception {

        journal.journalStart();
        journal.journalLoad();
        alarms.makeAlarm(journal.getTasks());

        ObservableList<Task> tasks = FXCollections.observableArrayList(
                journal.getTasks()
        );

        TableView<Task> taskTable = new TableView<Task>(tasks);

        taskTable.setPrefWidth(250);
        taskTable.setPrefHeight(200);

        // Task name Column
        TableColumn<Task, String> nameColumn = new TableColumn<Task, String>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("name"));
        taskTable.getColumns().add(nameColumn);
        TableColumn<Task, Date> dateColumn = new TableColumn<Task, Date>("Time");
        dateColumn.setCellFactory(column -> {
            TableCell<Task, Date> cell = new TableCell<Task, Date>() {

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(dateFormat.format(item));
                    }
                }
            };

            return cell;
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<Task, Date>("dateOfAlarm"));
        taskTable.getColumns().add(dateColumn);

        Actions actions = new Actions();

        fields.setSpacing(5);
        fields.getChildren().add(new Text("Set name of task:"));
        fields.getChildren().add(name);
        fields.getChildren().add(new Text("Set the text of task:"));
        fields.getChildren().add(mainText);
        fields.getChildren().add(new Text("Set the date of alarm (dd-MM-yyyy HH:mm:ss):"));
        fields.getChildren().add(dateOfAlarm);
        fields.getChildren().add(new Text("Set the contacts for task:"));
        fields.getChildren().add(contacts);
        fields.getChildren().add(buttonAdd);
        fields.getChildren().add(buttonUpdate);
        fields.getChildren().add(buttonDelete);

        sections.getChildren().add(fields);

        sections.getChildren().add(taskTable);

        root.getChildren().add(sections);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Company");
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Task task = new Task();

                task.setName(name.getText());
                task.setBody(mainText.getText());
                try {
                    task.setDateOfAlarm(dateFormat.parse(dateOfAlarm.getText()));
                } catch (ParseException e) {
                    System.out.println("There is something wrong with date format!" );
                }
                task.setContacts(contacts.getText());
                task.setId(journal.getTasksNumber() + 1);

                ArrayList<Task> journalTasks = journal.getTasks();
                journalTasks.add(task);
                journal.setTasks(journalTasks);

                actions.saveTask(task, journal);
                tasks.add(task);
                taskTable.refresh();
                alarms.addAlarm(task);

            }
        });

        buttonUpdate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    actions.updateTask(selectedTask, journal, name.getText(), mainText.getText(),
                            dateFormat.parse(dateOfAlarm.getText()), contacts.getText());
                } catch (ParseException e) {
                    System.out.println("There is something wrong with date format!" );
                }
                taskTable.refresh();

            }
        });

        buttonDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                actions.deleteTask(selectedTask, journal);
                taskTable.getItems().remove(selectedTask.getId()-1);
                taskTable.refresh();

            }
        });

        taskTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task task, Task t1) {

                selectedTask = taskTable.getSelectionModel().getSelectedItem();
                name.setText(selectedTask.getName());
                mainText.setText(selectedTask.getBody());
                dateOfAlarm.setText(dateFormat.format(selectedTask.getDateOfAlarm()));
                contacts.setText(selectedTask.getContacts());

            }
        });

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                try {
                    journal.saveData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Platform.exit();
                System.exit(0);
            }

        });
    }

    public static void interfaceLaunch() {
        launch();
    }

}
