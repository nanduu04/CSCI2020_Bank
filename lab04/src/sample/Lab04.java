import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab04 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(10,10,10,10));
        pane.setVgap(5);
        pane.setHgap(5);


        pane.add(new Label("Username"),0,0);
        TextField text1 = new TextField();
        pane.add(text1, 1,0);
        pane.add(new Label("Password:"), 0,1);
        pane.add(new PasswordField(), 1,1);
        pane.add(new Label("Full name:"), 0 , 2);
        TextField text2 = new TextField();
        pane.add(text2, 1 ,2);
        pane.add(new Label("E-mail:"),0,3);
        TextField text3 = new TextField();
        pane.add(text3, 1 ,3);
        pane.add(new Label("Phone #:"),0,4);
        TextField text4 = new TextField();
        pane.add(text4, 1 ,4);
        pane.add(new Label("Date of Birth:"),0,5);
        pane.add(new DatePicker(), 1 ,5);




        Button btAdd = new Button("Register");
        pane.add(btAdd, 1,6);
        GridPane.setHalignment(btAdd, HPos.LEFT);
        btAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(text1.getText());
                System.out.println(text2.getText());
                System.out.println(text3.getText());
                String num;
                num = text4.getText().replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                System.out.println(num);
            }
        });

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lab 04 Solution");
        primaryStage.show();
    }
}
