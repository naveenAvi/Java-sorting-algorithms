package com.mycompany.mavenproject3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 * JavaFX App update
 */
public class App extends Application {
    Button btnSort;
    TableView  table = new TableView ();
    List<String[]> csvData = new ArrayList<>();


    @Override
    public void start(Stage stage) {
        Button btn = new Button("Upload a csv file");
        FileChooser fileChooser = new FileChooser();

        ComboBox cmbBox = new ComboBox();
        cmbBox.getItems().add("Please upload a file");

        btn.setScaleX(1.2);
        btn.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                csvData.clear();
                csvData.addAll(readCSV(selectedFile));

                table.getColumns().clear();
                table.getItems().clear();

                cmbBox.getItems().clear();

                String[] headers =  csvData.get(0);
                for (int colIndex = 0; colIndex < headers.length; colIndex++) {
                    TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers[colIndex]);
                    final int index = colIndex;
                    column.setCellValueFactory(cellData -> {
                                try{
                                    return new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(index));
                                }catch(IndexOutOfBoundsException ex){
                                    return new javafx.beans.property.SimpleStringProperty("");
                                }
                            }
                    );
                    table.getColumns().add(column);

                    cmbBox.getItems().add(headers[colIndex]);
                }
                ObservableList<ObservableList<String>> rows = FXCollections.observableArrayList();
                for (int rowIndex = 1; rowIndex < csvData.size(); rowIndex++) {
                    rows.add(FXCollections.observableArrayList(csvData.get(rowIndex)));
                }
                table.setItems(rows);
            }
        });

        var label = new Label("Hello, JavaFX " );

        HBox hbox = new HBox(30);
        VBox  vbox = new VBox();

        hbox.setSpacing(2);
        hbox.getChildren().addAll(btn, label);
        hbox.setMargin(btn, new Insets(20, 20, 0, 20));
        hbox.setMargin(label, new Insets(20, 20, 0, 80));
        HBox.setHgrow(label, javafx.scene.layout.Priority.ALWAYS);

//new

        HBox columnSelectorbox = new HBox();
        btnSort = new Button("Sort");
        btnSort.setOnAction(e -> {
            if( csvData.size() < 10 ){
                showError( "select a valid csv file", "Please select a valid csv file with more than at least 10 rows(including header rows)!" );
            }else if ((cmbBox.getSelectionModel().getSelectedIndex() > 0 )){

                int validDataCount = 0;
                for (int i = 0; i < csvData.size()-2; i++) {
                    if( isNumber(csvData.get(i)[cmbBox.getSelectionModel().getSelectedIndex()] ) ){
                        validDataCount++;
                    }
                    if (validDataCount == 10 || i  > 12) {
                        break;
                    }
                }
                if( validDataCount > 9 ){
                    SortWindow sortWIndow = new SortWindow();
                    sortWIndow.show(csvData,  cmbBox.getSelectionModel().getSelectedIndex());
                }else{
                    showError( "invalid column!", "Please select a column with valid numerical data !" );
                }

            }else{
                showError( "select column to sort", "Please select a column to sort!" );
            }
        });

        columnSelectorbox.getChildren().addAll(cmbBox,btnSort);
        columnSelectorbox.setMargin(cmbBox, new Insets(20, 20, 20, 20));
        columnSelectorbox.setMargin(btnSort, new Insets(20, 20, 0, 20));


        vbox.setSpacing(10);

        vbox.getChildren().addAll(hbox, table, columnSelectorbox);
        var scene = new Scene(new StackPane(vbox), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private void showError(String title, String body){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(body );
        alert.showAndWait().ifPresent(rs -> { });
    }
    private List<String[]> readCSV(File file) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(",")); // Split CSV by commas
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }

    private boolean isNumber(String number){
        try {
            double i = Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}