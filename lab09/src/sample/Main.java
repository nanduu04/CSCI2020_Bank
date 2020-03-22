import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

public class lab09 extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stocks");

        //create arrays of historical value
        ArrayList<Double> stock1 = downloadInfo("MSFT");
        ArrayList<Double> stock2 = downloadInfo("AAPL");
        //figure out the scale
        double maxValue1 = Collections.max(stock1);
        double maxValue2 = Collections.max(stock2);
        double scaleY = Math.max(maxValue1, maxValue2);
        double maxValueX = Math.max(stock1.size(), stock2.size());

        Canvas canvas = new Canvas(600, 600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.strokeLine(50, 50, 50, 550);
        graphicsContext.strokeLine(50, 550, 550, 550);

        graph(graphicsContext, stock1, scaleY, maxValueX, Color.BLUE);
        graph(graphicsContext, stock2, scaleY, maxValueX, Color.RED);

        primaryStage.setScene(new Scene(new HBox(canvas), 600, 600));
        primaryStage.show();
    }

    public void graph(GraphicsContext graphicsContext, ArrayList<Double> stock,
                      double scaleY, double maxValueX, Color color){

        graphicsContext.moveTo(50, 550);
        graphicsContext.beginPath();

        double graphX = 550;
        double stepX = 500 / maxValueX;
        for (double i : stock){
            graphicsContext.lineTo(graphX -= stepX, 550 - i / scaleY * 500);
        }
        graphicsContext.setStroke(color);
        graphicsContext.stroke();
    }

    public ArrayList<Double> downloadInfo(final String stock){
        String key = "GX3X7F29WLES2V96";
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY&symbol=" + stock + "&apikey=" + key;

        JsonObject tier1;
        JsonObject tier2;
        ArrayList<Double> closingPrice = new ArrayList<>();

        StringBuilder dataToParse = new StringBuilder();

        //grab data and store in dataToParse
        try {
            URL apiURL = new URL(url);
            URLConnection apiCon = apiURL.openConnection();
            Scanner conScan = new Scanner(apiCon.getInputStream());
            while (conScan.hasNextLine()){
                dataToParse.append(conScan.nextLine());
            }
            conScan.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        //parse first fold
        JsonParser parser = new JsonParser();
        tier1 = parser.parse(dataToParse.toString()).getAsJsonObject();

        if(tier1 != null){

            //parse second fold
            tier2 = tier1.get("Monthly Time Series").getAsJsonObject();

            //parse third fold
            for(Map.Entry<String, JsonElement> jsonElement : tier2.entrySet()){
                closingPrice.add(jsonElement.getValue().getAsJsonObject().get("4. close").getAsDouble());
            }
        }
        return closingPrice;
    }
}