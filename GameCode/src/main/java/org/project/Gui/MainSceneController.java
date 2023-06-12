package org.project.Gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainSceneController {
    private GuiUserInterface guiUserInterface;
    private GuiController guiController;

    @FXML
    GridPane GrigliaBoard;
    private String[] azureImage = {
            "/images/Trofei1.1.png",
            "/images/Trofei1.2.png",
            "/images/Trofei1.3.png"
    };

    private String[] bluImage= {
            "/images/Cornici1.1.png",
             "/images/Cornici1.2.png",
            "/images/Cornici1.3.png"
    };
    private String[] pinkImage= {
            "/images/Piante1.1.png",
            "/images/Piante1.2.png",
            "/images/Piante1.3.png"
    };
    private String[] yellowImage= {
            "/images/Giochi1.1.png",
            "/images/Giochi1.2.png",
            "/images/Giochi1.3.png"
    };
    private String[] whiteImage= {
            "/images/Libri1.1.png",
            "/images/Libri1.2.png",
            "/images/Libri1.3.png"
    };
    private String[] greenImage= {
            "/images/Gatti1.1.png",
            "/images/Gatti1.2.png",
            "/images/Gatti1.3.png"
    };

    public void InitializeBoard(){
        Platform.runLater(() -> {
            for(int i=0; i<9;i++){
                for(int j=0;j<9;j++){
                    if(!guiUserInterface.getClientView().getBoard()[i][j].equals("I")){
                        String color=guiUserInterface.getClientView().getBoard()[i][j];
                        switch(color){
                            case "A"->{
                                Random random=new Random();
                                int index=random.nextInt(azureImage.length);
                                InputStream s=getClass().getResourceAsStream(azureImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);
                                /*
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);
                                */
                                break;
                            }
                            case "G"->{

                                Random random=new Random();
                                int index=random.nextInt(greenImage.length);
                                InputStream s=getClass().getResourceAsStream(greenImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);

                                /*Random random=new Random();
                                int index=random.nextInt(greenImage.length);
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);*/
                                break;
                            }
                            case "W"->{

                                Random random=new Random();
                                int index=random.nextInt(whiteImage.length);
                                InputStream s=getClass().getResourceAsStream(whiteImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);

                                /*Random random=new Random();
                                int index=random.nextInt(whiteImage.length);
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);*/
                                break;
                            }
                            case "Y"->{

                                Random random=new Random();
                                int index=random.nextInt(yellowImage.length);
                                InputStream s=getClass().getResourceAsStream(yellowImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);

                                /*Random random=new Random();
                                int index=random.nextInt(yellowImage.length);
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);*/
                                break;
                            }
                            case "P"->{

                                Random random=new Random();
                                int index=random.nextInt(pinkImage.length);
                                InputStream s=getClass().getResourceAsStream(pinkImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);

                                /*Random random=new Random();
                                int index=random.nextInt(pinkImage.length);
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);*/
                                break;
                            }
                            case "B"->{

                                Random random=new Random();
                                int index=random.nextInt(bluImage.length);
                                InputStream s=getClass().getResourceAsStream(bluImage[index]);
                                Image image=new Image(s);
                                ImageView imageView=new ImageView(image);
                                imageView.setPreserveRatio(true);
                                imageView.setFitHeight(26);
                                GrigliaBoard.add(imageView, j, i);

                                /*Random random=new Random();
                                int index=random.nextInt(bluImage.length);
                                ImageView imageRandom = new ImageView(new Image(getClass().getResource("/resources/images/Trofei1.1.png").toExternalForm()));
                                imageRandom.setFitWidth(45);
                                imageRandom.setFitHeight(39);
                                GrigliaBoard.add(imageRandom, j, i); // Inverti riga e colonna
                                GridPane.setRowIndex(imageRandom, i); // Imposta la posizione di riga
                                GridPane.setColumnIndex(imageRandom, j);*/
                                break;
                            }
                        }
                    }
                }
            }
        });
    }


    public PropertyChangeListener getRefreshListener() {
        return refreshlistener;
    }


    PropertyChangeListener refreshlistener = new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Platform.runLater(() -> {

                if("refresh".equals(evt.getPropertyName())){
                    for(int i=0; i<9;i++){
                        for(int j=0;j<9;j++){
                            if(!guiUserInterface.getClientView().getBoard()[i][j].equals("I")){
                                String color=guiUserInterface.getClientView().getBoard()[i][j];
                                switch(color){
                                    case "A"->{
                                        Random random=new Random();
                                        int index=random.nextInt(azureImage.length);
                                        ImageView imageRandom=new ImageView( new Image(azureImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                    case "G"->{
                                        Random random=new Random();
                                        int index=random.nextInt(greenImage.length);
                                        ImageView imageRandom=new ImageView( new Image(greenImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                    case "W"->{
                                        Random random=new Random();
                                        int index=random.nextInt(whiteImage.length);
                                        ImageView imageRandom=new ImageView( new Image(whiteImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                    case "Y"->{
                                        Random random=new Random();
                                        int index=random.nextInt(yellowImage.length);
                                        ImageView imageRandom=new ImageView( new Image(yellowImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                    case "P"->{
                                        Random random=new Random();
                                        int index=random.nextInt(pinkImage.length);
                                        ImageView imageRandom=new ImageView( new Image(pinkImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                    case "B"->{
                                        Random random=new Random();
                                        int index=random.nextInt(bluImage.length);
                                        ImageView imageRandom=new ImageView( new Image(bluImage[index]));
                                        imageRandom.setFitWidth(45);
                                        imageRandom.setFitHeight(39);
                                        GrigliaBoard.add(imageRandom,i,j);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
    };

    public void setGuiUserInterface(GuiUserInterface guiUserInterface) {
        this.guiUserInterface = guiUserInterface;
    }

    public void setGuiController(GuiController guiController) {
        this.guiController = guiController;
    }
}
