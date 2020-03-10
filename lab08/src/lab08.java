import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class lab08 extends Application {
    protected String currentFilename = "records.csv";

    private TableView<StudentRecord> table = new TableView<>();
    private final ObservableList<StudentRecord> studentRecords = DataSource.getAllMarks();

    GridPane gridPane = new GridPane();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Student Records");
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(250);

        table.setEditable(true);

        TableColumn<StudentRecord, String> SIDCol = new TableColumn<>("SID");
        SIDCol.setMinWidth(100);
        SIDCol.setCellValueFactory(new PropertyValueFactory<>("StudentID"));

        TableColumn<StudentRecord, Float> assignmentsCol = new TableColumn<>("Assignments");
        assignmentsCol.setMinWidth(100);
        assignmentsCol.setCellValueFactory(new PropertyValueFactory<>("Assignments"));

        TableColumn<StudentRecord, Float> midtermCol = new TableColumn<>("Midterm");
        midtermCol.setMinWidth(100);
        midtermCol.setCellValueFactory(new PropertyValueFactory<>("Midterm"));

        TableColumn<StudentRecord, Float> examCol = new TableColumn<>("Final Exam");
        examCol.setMinWidth(100);
        examCol.setCellValueFactory(new PropertyValueFactory<>("FinalExam"));

        TableColumn<StudentRecord, Float> finalPercentCol = new TableColumn<>("Final Mark");
        finalPercentCol.setMinWidth(100);
        finalPercentCol.setCellValueFactory(new PropertyValueFactory<>("FinalGrade"));

        TableColumn<StudentRecord, Character> finalLetterCol = new TableColumn<>("Letter Grade");
        finalLetterCol.setMinWidth(100);
        finalLetterCol.setCellValueFactory(new PropertyValueFactory<>("LetterGrade"));

        table.setItems(studentRecords);
        table.getColumns().addAll(SIDCol, assignmentsCol, midtermCol, examCol, finalPercentCol, finalLetterCol);

        final Label SIDLabel = new Label("SID:");
        final TextField addSID = new TextField();
        addSID.setPromptText("SID");
        SIDLabel.setPadding(new Insets(10, 10, 10, 10));

        final Label AssignmentLabel = new Label("Assignments:");
        final TextField addAssignments = new TextField();
        addAssignments.setPromptText("Assignments/100");
        AssignmentLabel.setPadding(new Insets(10, 10, 10, 10));

        final Label MidtermLabel = new Label("Midterm:");
        final TextField addMidterm = new TextField();
        addMidterm.setPromptText("Midterm/100");
        MidtermLabel.setPadding(new Insets(10, 10, 10, 10));

        final Label ExamLabel = new Label("Final Exam:");
        final TextField addExam = new TextField();
        addExam.setPromptText("Final Exam/100");
        ExamLabel.setPadding(new Insets(10, 10, 10, 10));

        final Button addButton = new Button("Add");

        Label submissionInvalid = new Label("Invalid Submission.\nPlease check the entered values and try again.");
        submissionInvalid.setMinHeight(50);
        submissionInvalid.setTextFill(Color.color(1, 0, 0));
        gridPane.add(submissionInvalid, 1, 3);
        submissionInvalid.setOpacity(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> submissionInvalid.setOpacity(0)));

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);

        MenuItem mnNew = new MenuItem("New");

        MenuItem mnOpen = new MenuItem("Open");

        MenuItem mnSave = new MenuItem("Save");

        MenuItem mnSaveAs = new MenuItem("Save As");

        MenuItem mnExit = new MenuItem("Exit");

        menuFile.getItems().addAll(mnNew, mnOpen, mnSave, mnSaveAs, mnExit);

        addButton.setOnAction(e -> {
            if ((addSID.getText().length() == 9 && addSID.getText().charAt(0) == '1')
                    && (Float.parseFloat(addAssignments.getText()) >= 0 && Float.parseFloat(addAssignments.getText()) <= 100)
                    && (Float.parseFloat(addMidterm.getText()) >= 0 && Float.parseFloat(addMidterm.getText()) <= 100)
                    && (Float.parseFloat(addExam.getText()) >= 0 && Float.parseFloat(addExam.getText()) <= 100)) {
                studentRecords.add(new StudentRecord(addSID.getText(), addAssignments.getText(), addMidterm.getText(),
                        addExam.getText()));
                addSID.clear();
                addAssignments.clear();
                addMidterm.clear();
                addExam.clear();
                submissionInvalid.setOpacity(0);
            } else {
                submissionInvalid.setOpacity(100);
                timeline.play();
            }
        });

        mnExit.setOnAction(event -> System.exit(0));

        mnSave.setOnAction(event -> {
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mnOpen.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open CSV File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            fileChooser.setInitialDirectory(new File("./"));

            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                currentFilename = file.getPath();

                table.getItems().clear();

                try {
                    load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mnSaveAs.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            fileChooser.setInitialDirectory(new File("./"));

            File file = fileChooser.showSaveDialog(primaryStage);

            currentFilename = file.getPath();

            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mnNew.setOnAction(event -> {
            table.getItems().clear();
            currentFilename = "records.csv";
        });

        gridPane.add(SIDLabel, 0, 0);
        gridPane.add(addSID, 1, 0);
        gridPane.add(AssignmentLabel, 2, 0);
        gridPane.add(addAssignments, 3, 0);
        gridPane.add(MidtermLabel, 0, 1);
        gridPane.add(addMidterm, 1, 1);
        gridPane.add(ExamLabel, 2, 1);
        gridPane.add(addExam, 3, 1);
        gridPane.add(addButton, 1, 2);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(menuBar, table, gridPane);

        ((Group) scene.getRoot()).getChildren().add(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void save() throws IOException {
        Writer writer = null;

        try {
            File file = new File(currentFilename);
            writer = new BufferedWriter(new FileWriter(file));

            for (StudentRecord person : studentRecords) {
                String text = person.getStudentID() + "," + person.getAssignments() + "," + person.getMidterm() + "," + person.getFinalExam() + "\n";

                writer.write(text);
            }
        } catch (IOException ex) {
            Logger.getLogger(lab08.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public void load() throws IOException {
        File file = new File(currentFilename);
        String separator = ",";

        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));

            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(separator, -1);

                StudentRecord record = new StudentRecord(fields[0], fields[1], fields[2], fields[3]);
                studentRecords.add(record);
            }
        } catch (IOException ex) {
            Logger.getLogger(lab08.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

