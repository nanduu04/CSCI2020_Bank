import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class PieChartDisplay extends Application{
    int radius = 200;
    int legendBox = 15;
    public void start (Stage stage)  {
        final Canvas pieChart = new Canvas(1000,1000);
        GraphicsContext gc1 = pieChart.getGraphicsContext2D();
        String[] warningTypeString = {"FLASH FLOOD", "SEVERE THUNDERSTORM", "SPECIAL MARINE", "TORNADO"};
        int[] warningType = new int[4];
        Color[] pieColours = {Color.AQUA, Color.GOLD, Color.DARKORANGE,Color.DARKSALMON};

        fillArray(warningTypeString,warningType);

        double total = createTotal(warningType);
        double startAngle = 0;
        double endAngle;
        for(int i=0;i<warningType.length;i++) {
            endAngle = (warningType[i]/total)*360;
            gc1.setFill(pieColours[i]);
            gc1.fillArc(200,40,radius,radius,startAngle,endAngle,ArcType.ROUND);
            startAngle+=(warningType[i]/total)*360;
        }

        for(int j=0;j<pieColours.length;j++) {
            gc1.setFill(pieColours[j]);
            gc1.fillRect(30, (25*j)+100, legendBox*2, legendBox);
            gc1.setFill(Color.BLACK);
            gc1.fillText(warningTypeString[j],30+(legendBox*2) , (25*j)+(100+legendBox));
        }



        HBox hBox = new HBox();
        hBox.getChildren().add(pieChart);
        stage.setTitle("Lab 07");
        Scene scene = new Scene(hBox, 420, 250);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args){
        launch(args);
    }

    public static void fillArray(String[] warningTypeString, int[] warningType) {
        String csvFile = "weatherwarnings-2015.csv";
        String line = "";
        String[] words=null;
        try {
            BufferedReader r = new BufferedReader(new FileReader(csvFile));
            while((line = r.readLine()) != null) {
                words=line.split(",");
                for(String word:words) {
                    if(word.equals(warningTypeString[0])) {
                        warningType[0]++;
                    }
                    else if(word.equals(warningTypeString[1])) {
                        warningType[1]++;
                    }
                    else if(word.equals(warningTypeString[2])) {
                        warningType[2]++;
                    }
                    else if(word.equals(warningTypeString[3])) {
                        warningType[3]++;
                    }
                    else {

                    }
                }

            }
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static double createTotal(int a[]) {
        int total=0;
        for(int i=0;i<a.length;i++) {
            total+=a[i];
        }
        return total;
    }
}
