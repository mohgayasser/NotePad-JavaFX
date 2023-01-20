package fxml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public  class FXMLNotepadBase extends BorderPane {

    protected final MenuBar menuBar;
    protected final Menu FileMenu;
    protected final MenuItem NewMenuItem;
    protected final MenuItem OpenMenuItem;
    protected final MenuItem SaveMenuItem;
    protected final MenuItem SaveAsMenuItem;
   
    protected final MenuItem ExiteMenuItem;
    protected final Menu EditMenu;
    protected final MenuItem UndoMenuItem;
    protected final MenuItem CutMenuItem;
    protected final MenuItem CopyMenuItem;
    protected final MenuItem PastMenuItem;
    protected final MenuItem DeleteMenuItem;
    protected final MenuItem SelectAllMenuItem;
    protected final Menu HelpMenu;
    protected final MenuItem AboutMenuItem;
    protected final TextArea textArea;
    
    
   public static volatile File OpendFile = null;
   public static boolean saved = false;
    public FXMLNotepadBase() {

        menuBar = new MenuBar();
        FileMenu = new Menu();
        NewMenuItem = new MenuItem();
        OpenMenuItem = new MenuItem();
        SaveMenuItem = new MenuItem();
        SaveAsMenuItem = new MenuItem();
        ExiteMenuItem = new MenuItem();
        EditMenu = new Menu();
        UndoMenuItem = new MenuItem();
        CutMenuItem = new MenuItem();
        CopyMenuItem = new MenuItem();
        PastMenuItem = new MenuItem();
        DeleteMenuItem = new MenuItem();
        SelectAllMenuItem = new MenuItem();
        HelpMenu = new Menu();
        AboutMenuItem = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        FileMenu.setMnemonicParsing(false);
        FileMenu.setText("File");

        NewMenuItem.setMnemonicParsing(false);
        NewMenuItem.setText("New");
        NewMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        OpenMenuItem.setMnemonicParsing(false);
        OpenMenuItem.setText("Open");
        OpenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        SaveMenuItem.setMnemonicParsing(false);
        SaveMenuItem.setText("Save");
        SaveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        
        SaveAsMenuItem.setMnemonicParsing(false);
        SaveAsMenuItem.setText("Save As ..");

        ExiteMenuItem.setMnemonicParsing(false);
        ExiteMenuItem.setText("Exit");
        ExiteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        EditMenu.setMnemonicParsing(false);
        EditMenu.setText("Edit");

        UndoMenuItem.setMnemonicParsing(false);
        UndoMenuItem.setText("Undo");

        CutMenuItem.setMnemonicParsing(false);
        CutMenuItem.setText("Cut");

        CopyMenuItem.setMnemonicParsing(false);
        CopyMenuItem.setText("Copy");

        PastMenuItem.setMnemonicParsing(false);
        PastMenuItem.setText("Paste");
       
        

        DeleteMenuItem.setMnemonicParsing(false);
        DeleteMenuItem.setText("Delete");

        SelectAllMenuItem.setMnemonicParsing(false);
        SelectAllMenuItem.setText("Select All");

        HelpMenu.setMnemonicParsing(false);
        HelpMenu.setText("Help");

        AboutMenuItem.setMnemonicParsing(false);
        AboutMenuItem.setText("About Notepad");
        setTop(menuBar);

        

        FileMenu.getItems().add(NewMenuItem);
        FileMenu.getItems().add(OpenMenuItem);
        FileMenu.getItems().add(SaveMenuItem);
        FileMenu.getItems().add(SaveAsMenuItem);
        FileMenu.getItems().add(ExiteMenuItem);
        menuBar.getMenus().add(FileMenu);
        EditMenu.getItems().add(UndoMenuItem);
        EditMenu.getItems().add(CutMenuItem);
        EditMenu.getItems().add(CopyMenuItem);
        EditMenu.getItems().add(PastMenuItem);
        EditMenu.getItems().add(DeleteMenuItem);
        EditMenu.getItems().add(SelectAllMenuItem);
        menuBar.getMenus().add(EditMenu);
        HelpMenu.getItems().add(AboutMenuItem);
        menuBar.getMenus().add(HelpMenu);

     
        textArea.getStyleClass().add("text-area");
         VBox layout = new VBox(
                10,
                textArea
        );
        layout.setPadding(new Insets(10));

        VBox.setVgrow(textArea, Priority.ALWAYS);
        
        BorderPane.setAlignment(layout, javafx.geometry.Pos.CENTER);
     
        setCenter(layout);
        
        //ScheduledExecutorService executorService =Executors.newSingleThreadScheduledExecutor();
        
        
        OpenMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent  event){
                chooseFile(event);
            }     
        });
        SaveMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(OpendFile != null){
                    writeFile(OpendFile.getAbsolutePath());
                    Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
                    currentStage.setTitle(OpendFile.getName());
                }else {
                     saveDate(event);
                }
            }
                
        });
        SelectAllMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.selectAll();    
            }
        });
        UndoMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.undo();
            }
        });
        CopyMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.copy();
            }
        });
        CutMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.cut();
            }
        });
        PastMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.paste();
            }
        });
       
        ExiteMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(! saved){
                    //Create an Alert with predefined warning image
                    Alert alert = new Alert(AlertType.WARNING);
                    //Set text in conveinently pre-defined layout
                    alert.setTitle("Warning");
                    alert.setHeaderText("Are you sure?");
                    alert.setContentText("Do you want to close the application without saving ?");
                    //Set custom buttons
                    ButtonType okButton= new ButtonType("Yes, exit", ButtonData.OK_DONE);
                    ButtonType saveButton = new ButtonType("Save,and exit",ButtonData.YES);
                    ButtonType cancelButton= new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(okButton,saveButton, cancelButton);
                    //Prevent all interaction with application until resolved.
                    alert.initModality(Modality.APPLICATION_MODAL);
                    //Launch
                    Optional<ButtonType> result = alert.showAndWait();
                    System.out.println(result.get());
                    if(result.get().getButtonData() == ButtonData.YES){
                        writeFile(OpendFile.getAbsolutePath());
                        Platform.exit();
                        System.exit(0);
                    }else if(result.get().getButtonData() == ButtonData.OK_DONE){
                         Platform.exit();
                         System.exit(0);
                    }
                }else {
                    Platform.exit();
                    System.exit(0); 
                }
               
            }
        });
        
        DeleteMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.setText(textArea.getText().replace(textArea.getSelectedText(),""));
                
            }
        });
        
        textArea.setWrapText(true);
        
        textArea.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   saved = false;
                   if(OpendFile != null){
                        Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
                        currentStage.setTitle("*" + OpendFile.getName());
                    }
            }
        
        });
        SaveAsMenuItem.addEventHandler(ActionEvent.ACTION, (event) -> {
            saveDate(event);
        });
        NewMenuItem.addEventHandler(ActionEvent.ACTION, (event) -> {
            OpendFile =null;
            textArea.setText("");
            Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
            currentStage.setTitle("Untitled - Notepad");
        });
        AboutMenuItem.addEventHandler(ActionEvent.ACTION, (event) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Message Here...");
            alert.setHeaderText("this is Notepad created using JavaFx");
            alert.setContentText(" by Mohga ");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        });
        /*if(OpendFile !=null){
            Runnable autoSave = () ->{
                System.out.println("Hello");
                writeFile(OpendFile.getAbsolutePath());
                Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
                currentStage.setTitle(OpendFile.getName()+ "Changes Saved");
            };
            executorService.scheduleWithFixedDelay(autoSave, 0, 2, TimeUnit.MILLISECONDS);
        }*/
    }
    
    public void chooseFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
         OpendFile = fileChooser.showOpenDialog(null);
        if(OpendFile != null){
            
            loadFileToTextArea(OpendFile);
            
            Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
            currentStage.setTitle(OpendFile.getName());
        }
    }

    private void loadFileToTextArea(File fileToLoad) {
        try {
            // Create a buffered stream
            Scanner input = new Scanner(fileToLoad);
            textArea.setText("");

            while (input.hasNext()) {
               textArea.appendText(input.nextLine() + '\n');
            }
            input.close();

         } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
         }
    }
    public boolean saveDate(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save");
        if(OpendFile ==null){
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files (*.txt)", "*.txt"));
        }else {
            fileChooser.setInitialFileName(OpendFile.getName());
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text files (*.txt)", "*.txt"));
        }
        OpendFile = fileChooser.showSaveDialog(null);
       
        writeFile(OpendFile.getAbsolutePath());
        Stage currentStage =(Stage)((Node) menuBar).getScene().getWindow();
        currentStage.setTitle(OpendFile.getName());
     return false;  
    }
    
     public void writeFile(String location) {
      
           try(BufferedWriter bw = new BufferedWriter(new FileWriter(new File(location)))) {
               String text = textArea.getText();
               bw.write(text);
               saved = true;
           } catch(IOException e) {
               System.out.println(e.getMessage());
           }
    }
}
