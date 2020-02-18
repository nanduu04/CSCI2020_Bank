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
import javafx.stage.Stage;
import javafx.util.Duration;

public class lab05 extends Application {
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
        SIDLabel.setPadding(new Insets(10,10,10,10));

        final Label AssignmentLabel = new Label("Assignments:");
        final TextField addAssignments = new TextField();
        addAssignments.setPromptText("Assignments/100");
        AssignmentLabel.setPadding(new Insets(10,10,10,10));

        final Label MidtermLabel = new Label("Midterm:");
        final TextField addMidterm = new TextField();
        addMidterm.setPromptText("Midterm/100");
        MidtermLabel.setPadding(new Insets(10,10,10,10));

        final Label ExamLabel = new Label("Final Exam:");
        final TextField addExam = new TextField();
        addExam.setPromptText("Final Exam/100");
        ExamLabel.setPadding(new Insets(10,10,10,10));

        final Button addButton = new Button("Add");

        Label submissionInvalid = new Label("Invalid Submission.\nPlease check the entered values and try again.");
        submissionInvalid.setMinHeight(50);
        submissionInvalid.setTextFill(Color.color(1,0,0));
        gridPane.add(submissionInvalid, 1, 3);
        submissionInvalid.setOpacity(0);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> submissionInvalid.setOpacity(0)));

        addButton.setOnAction(e -> {
            if ((addSID.getText().length() == 9 && addSID.getText().charAt(0) == '1')
                    && (Float.parseFloat(addAssignments.getText()) >= 0 && Float.parseFloat(addAssignments.getText()) <= 100)
                    && (Float.parseFloat(addMidterm.getText()) >= 0 && Float.parseFloat(addMidterm.getText()) <= 100)
                    && (Float.parseFloat(addExam.getText()) >= 0 && Float.parseFloat(addExam.getText()) <= 100)) {
                studentRecords.add(new StudentRecord(addSID.getText(),
                        addAssignments.getText(),
                        addMidterm.getText(),
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
        vbox.getChildren().addAll(table, gridPane);

        ((Group) scene.getRoot()).getChildren().add(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

