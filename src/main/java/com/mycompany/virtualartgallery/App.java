package com.mycompany.virtualartgallery;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application { // Room details: title, artist name, image path
      
    // Room details: title, artist name, image path
    private static final Room[] rooms = {
            new Room("The Starry Night", "Vincent van Gogh", "C:\\Users\\Raghad\\Downloads\\starry night.jpeg"),
            new Room("Mona Lisa", "Leonardo da Vinci", "C:\\Users\\Raghad\\Downloads\\Mona lisa.jpeg"),
            new Room("The Scream", "Edvard Munch", "C:\\Users\\Raghad\\Downloads\\The Scream.jpeg")
    };

    private int currentRoomIndex = 0; // Current room index

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Virtual Art Gallery");

        // Create navigation buttons
        Button prevButton = new Button("Previous Room");
        prevButton.setOnAction(e -> navigate(-1, primaryStage)); // Navigate to previous room
        Button nextButton = new Button("Next Room");
        nextButton.setOnAction(e -> navigate(1, primaryStage)); // Navigate to next room
        HBox navButtons = new HBox(10, prevButton, nextButton);
        navButtons.setAlignment(Pos.CENTER);

        // Create artwork display
        ImageView artworkView = new ImageView();
        artworkView.setFitWidth(400); // Adjust according to your image size
        artworkView.setPreserveRatio(true); // Preserve aspect ratio
        StackPane artworkPane = new StackPane(artworkView);
        artworkPane.setAlignment(Pos.CENTER);

        // Display artwork title and artist
        VBox artworkInfo = new VBox(10);
        artworkInfo.setAlignment(Pos.CENTER);
        artworkInfo.getChildren().addAll(
                new javafx.scene.control.Label("Title: " + rooms[currentRoomIndex].getTitle()),
                new javafx.scene.control.Label("Artist: " + rooms[currentRoomIndex].getArtistName())
        );

        // Layout for artwork display and navigation buttons
        VBox layout = new VBox(20, artworkPane, artworkInfo, navButtons);
        layout.setAlignment(Pos.CENTER);

        // Set up scene
        Scene scene = new Scene(layout, 600, 450); // Adjust according to your UI dimensions
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load initial artwork image
        loadImage(artworkView, rooms[currentRoomIndex].getImagePath());
    }

    // Method to navigate between rooms
    private void navigate(int step, Stage primaryStage) {
        currentRoomIndex = (currentRoomIndex + step + rooms.length) % rooms.length;
        // Update displayed artwork and information
        primaryStage.setTitle("Virtual Art Gallery - Room " + (currentRoomIndex + 1));
        loadImage((ImageView) ((StackPane) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(0)).getChildren().get(0), rooms[currentRoomIndex].getImagePath());
        // Update displayed artwork title and artist
        ((javafx.scene.control.Label) ((VBox) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(2)).getChildren().get(0)).setText("Title: " + rooms[currentRoomIndex].getTitle());
        ((javafx.scene.control.Label) ((VBox) ((VBox) primaryStage.getScene().getRoot()).getChildren().get(2)).getChildren().get(1)).setText("Artist: " + rooms[currentRoomIndex].getArtistName());
    }

    // Method to load an image into an ImageView
    private void loadImage(ImageView imageView, String imagePath) {
        try {
            // Convert the file path to a file object
            File file = new File(imagePath);
            // Convert the file object to a URL
            URL url = file.toURI().toURL();
            // Load the image from the URL
            Image image = new Image(url.toExternalForm());
            imageView.setImage(image);
        } catch (MalformedURLException e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    // Room class to encapsulate room information
    static class Room {
        private String title;
        private String artistName;
        private String imagePath;

        public Room(String title, String artistName, String imagePath) {
            this.title = title;
            this.artistName = artistName;
            this.imagePath = imagePath;
        }

        public String getTitle() {
            return title;
        }

        public String getArtistName() {
            return artistName;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}