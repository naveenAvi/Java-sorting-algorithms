/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author navee
 */
public class SortWindow {
    TableView copiedTable = new TableView<>();        
        TableView resultsTable = new TableView<>();
        PieChart pieChart = new PieChart();
        ListView<Text> resultsList = new ListView<>();
        int testNumber = 1;
         Map<String, Long> resultsMap =  new HashMap<>();         
         Map<Integer, Map<String, Long>> resultsMapAllResults =  new HashMap<>();
         Label ResultNameLabel = new Label();

        
    public void show(List<String[]>  csvData, int selectedIndex) {
        String[] headers =  csvData.get(0);
         TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers[selectedIndex]);
                    column.setCellValueFactory(cellData -> 
                        new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(selectedIndex))
                    );
                    copiedTable.getColumns().add(column);
                    
                ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList();
                for (int rowIndex = 1; rowIndex < csvData.size(); rowIndex++) {
                    rows.add(FXCollections.observableArrayList(csvData.get(rowIndex)));
                }
                copiedTable.setItems(rows);


        Stage newStage = new Stage();
        newStage.setTitle("Sorting algorithms");
        
        HBox tableNResults = new HBox();
        
        VBox resultsLayout = new VBox(10);
        
        resultsLayout.getChildren().addAll(resultsList,ResultNameLabel, pieChart);
        tableNResults.getChildren().addAll(copiedTable,  resultsLayout);

        VBox newLayout = new VBox(10);
        Button rerunButton = new Button("Re run the test!");
        newLayout.getChildren().addAll(rerunButton, tableNResults);

        Scene newScene = new Scene(newLayout, 640, 680);
        newStage.setScene(newScene);

        newStage.show();
        
    }

    
    private  double[] getCsvColumn(List<String[]>  csvData, int selectedCOlIndex){
        double[] columnData = new double[csvData.size()];
        for (int i = 0; i < csvData.size(); i++) {
            try {
                    Double intval = Double.parseDouble(csvData.get(i)[selectedCOlIndex]);
                     columnData[i] = intval;
            } catch (NumberFormatException nfe) {
            }
        }
        return columnData;
    }
    private void addSortResults(ListView resultsList,String algorithm,  long timetaken){
        long durationInMs = TimeUnit.NANOSECONDS.toMillis(timetaken);
        resultsList.getItems().add(algorithm + " : " + String.valueOf( timetaken)  );
    }
    private void addSortNaming(ListView resultsList,String itemText){
        Text text = new Text(itemText);
        text.setStyle("-fx-font-weight: bold;");  // CSS for bold
        resultsList.getItems().add(text  );
    }
    
    private void showPie( PieChart pieChart , Map<String, Long> resultsMap ){
       ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
        for (Map.Entry<String, Long> entry : resultsMap.entrySet()) {
            String label = entry.getKey();
            Long value = entry.getValue(); 

            pieChartData.add(new PieChart.Data(label, value));
        }
        
        pieChart.setData(pieChartData);
    }
    
}
