/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lerabeassignment3;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author Lerabe
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private Label durationLabel;

        @FXML
    private Label durationLabel2;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button playButton;

    @FXML
    private Button stopBtn;
    
      @FXML
    private Button NextBtn;


    @FXML
    private Slider volumeSlider;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Button chooseFileBtn;
    @FXML
    private Button backBtn;  
    @FXML
    private MediaView mediaView;
    
    private MediaPlayer mediaPlayer;


    @FXML
    void PauseButtonAction(ActionEvent event) {
        mediaPlayer.pause();

    }
    @FXML
    void BackButtonAction(ActionEvent event) {
         mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-10)));
    }
    @FXML
    void PlayButtonAction(ActionEvent event) {  
        mediaPlayer.play();
    }

    @FXML
    void StopButtonAction(ActionEvent event) {
        mediaPlayer.stop();
    }

     @FXML
    void NextButtonAction(ActionEvent event) {
         mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(5)));
    }

   
    @FXML
    void openVideo(ActionEvent event) {
    
    FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.mp4");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
 
           mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue){
                slider.setValue(newValue.toSeconds());
                
            }
           });
           
           slider.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                mediaPlayer.seek(Duration.seconds(slider.getValue()));
            }
        });
       
            slider.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                mediaPlayer.seek(Duration.seconds(slider.getValue()));
            }
        });
        
        mediaPlayer.setOnReady(new Runnable(){
                      
            @Override
            public void run(){
                Duration total = media.getDuration();
                slider.setMax(total.toSeconds());
                
                int duration = (int) media.getDuration().toSeconds();
                int hours = duration / 3600;
                int minutes = (duration % 3600) / 60;
                int seconds = duration % 60;
                String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                durationLabel.setText(timeString);
                 
            }
        });

             slider.valueProperty().addListener((ObservableValue<? extends Number> num, Number oldValue, Number newValue) ->{
                 Float  currentTime=Float.valueOf(String.format("%.1f",newValue));
                  durationLabel2.setText(""+currentTime);

         });
        
        
         volumeSlider.setValue(mediaPlayer.getVolume() * 100.0);
            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                mediaPlayer.setVolume(newValue.doubleValue() / 100.0);
            });    
         }
    } 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
    
 
    }    
    
    
}
