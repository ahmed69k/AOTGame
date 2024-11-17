package game.gui;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;



import java.awt.TextArea;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class View extends Application {
	
	private static Battle battle;
    private static Stage primaryStage;
    private static ArrayList<Lane> temp = new ArrayList<>();

    Button next2 = new Button("NEXT");
    Button next = new Button("NEXT");
    TextField textField = new TextField();
    int weaponCode;

    static VBox emptyVbox = new VBox();
    static VBox emptyVbox2 = new VBox();
    static VBox laneButtonsHard = new VBox();
    static VBox laneButtonsEasy2 = new VBox();

    
    Text scoreText= new Text("Score: 0");;
    Text resourcesText;
    Text turnsText=new Text("Turns: 1");;
    Text bpText;
    
    Text scoreText2 = new Text("Score: 0");
    Text resourcesText2=new Text("Resources: 0");
    Text turnsText2=new Text("Turns: 1");
    Text bpText2=new Text("Battle Phase: ");
    
    VBox textAreas = new VBox();
    

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Line underline = new Line();
        Line line = new Line();
      
        Font.loadFont(getClass().getResourceAsStream("Onyx.ttf"), 70);
        Text text = new Text("Attack On Titan: Utopia");
        
        text.setFill(Color.WHITE);
        text.setStroke(Color.BLACK);
        underline.setStroke(Color.WHITE); 
        underline.setStrokeWidth(2);
        line.setEndX(560);
        line.setFill(Color.WHITE);
        line.setStroke(Color.WHITE);

        underline.translateYProperty().bind(text.layoutYProperty().add(text.getLayoutBounds().getHeight() + 2));


        Transition underlineAnimation = new Transition() {
            {
                setCycleDuration(Duration.seconds(2));
                setCycleCount(1); 
                setInterpolator(Interpolator.EASE_BOTH);
            }

            @Override
            protected void interpolate(double frac) {

                underline.setEndX(text.getLayoutBounds().getWidth() * frac);
            }
        };
        
        InputStream fontStream = getClass().getResourceAsStream("/Onyx.ttf");
        Font onyx = Font.loadFont(fontStream, 70);
        
        InputStream gothStream = getClass().getResourceAsStream("/GothTitan.ttf");
        Font gothTitan = Font.loadFont(gothStream, 100);
        
       
        text.setFont(Font.font("Onyx",100));

        Button start = new Button("START");
        ToggleButton options = new ToggleButton("OPTIONS");
        Button exit = new Button("EXIT");
        

        Text skip = new Text("Press Anywhere To Skip");
        skip.setStyle("-fx-font-size: 60px; -fx-fill: darkred;-fx-stroke: white;");
        skip.setFont(Font.font("Onyx"));

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(7), skip);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), skip);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeIn.play();
        fadeIn.setOnFinished(e -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                fadeOut.play();
            });
            pause.play();
        });

        start.setFont(Font.font("Onyx", 50));
        options.setFont(Font.font("Onyx", 50));
        exit.setFont(Font.font("Onyx", 50));
        

        String swordPath = "sword.png";
        //String filePath = "file:///C:/Users/Ahmed/Desktop/aotg.gif";
        //String musicPath = "file:///C:/Users/Ahmed/Desktop/aots.mp3";
        //String slicePath = "file:///C:/Users/Ahmed/Desktop/slice.mp3";
        //String videoPath = "file:///C:/Users/Ahmed/Desktop/aotv.mp4";
        //String introPath = "file:///C:/Users/Ahmed/Desktop/introv.mp4";
        //String bgPath = "file:///C:/Users/Ahmed/Desktop/bg.mp4";
        //String armorPath = "file:///C:/Users/Ahmed/Desktop/game images/armoredTitan";
        //String purePath = "file:///C:/Users/Ahmed/Desktop/game images/pureTitan";
        //String colossalPath = "file:///C:/Users/Ahmed/Desktop/game images/colossalTitan";
        
//        ImageView armor = new ImageView(armorPath);
//        ImageView pure = new ImageView(purePath);
//        ImageView colossal = new ImageView(colossalPath);

        Image overP = new Image("file:///C:/Users/Ahmed/Desktop/gameOver.png");
        Image swordP = new Image(swordPath);
        //Image background = new Image(filePath);
        Slider vs = new Slider(0, 1, 0.5);
        vs.setMaxWidth(100);
        //BackgroundImage bg = new BackgroundImage(background,
         //       BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
         //       BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //Background backgroundStyle = new Background(bg);
        ImageView ov = new ImageView(overP);

        
        ImageView sv = new ImageView(swordP);
        ImageView sv1 = new ImageView(swordP);
        ImageView sv2 = new ImageView(swordP);
        ImageView sv3 = new ImageView(swordP);
        ImageView sv4 = new ImageView(swordP);
        ImageView sv5 = new ImageView(swordP);
        ImageView sv6 = new ImageView(swordP);
        ImageView sv7 = new ImageView(swordP);
        ImageView sv8 = new ImageView(swordP);
        
        
        Media music = new Media(getClass().getResource("/Resources/aots.mp3").toExternalForm());
        MediaPlayer mp = new MediaPlayer(music);

        Media slice = new Media(getClass().getResource("/Resources/slice.mp3").toExternalForm());
        MediaPlayer sliceP = new MediaPlayer(slice);

        Media video = new Media(getClass().getResource("/Resources/aotv.mp4").toExternalForm());
        MediaPlayer videoP = new MediaPlayer(video);

        Media intro = new Media(getClass().getResource("/Resources/introv.mp4").toExternalForm());
        MediaPlayer introP = new MediaPlayer(intro);
        introP.setAutoPlay(true);
        Media bg1 = new Media(getClass().getResource("/Resources/bg.mp4").toExternalForm());
        MediaPlayer bgP = new MediaPlayer(bg1);
        bgP.setAutoPlay(true);
        bgP.setCycleCount(MediaPlayer.INDEFINITE);
        


        MediaView mv = new MediaView(videoP);
        MediaView vid = new MediaView(mp);
        MediaView introV = new MediaView(introP);
        MediaView iv = new MediaView(bgP);
        MediaView iv2 = new MediaView(bgP);
        MediaView iv3 = new MediaView(bgP);
        MediaView iv4 = new MediaView(bgP);
        MediaView iv5 = new MediaView(bgP);
        MediaView iv6 = new MediaView(bgP);
        MediaView iv7 = new MediaView(bgP);
        MediaView iv8 = new MediaView(bgP);
        
        Button mtt = new Button("MEET THE TITANS");

        StackPane ap = new StackPane();
        //ap.setBackground(backgroundStyle);
        ap.getChildren().add(iv);
        //ap.getChildren().add(iv);
        ap.getChildren().add(text);
        ap.getChildren().addAll(underline);
       // underlineAnimation.setAutoReverse(true);
        underlineAnimation.setOnFinished(event->{ap.getChildren().remove(underline);ap.getChildren().add(line);});
        line.setStrokeWidth(2);
        
        ap.getChildren().add(start);
        ap.getChildren().add(mtt);

        ap.getChildren().add(options);
        ap.getChildren().add(exit);
        ap.getChildren().add(mv);
        ap.getChildren().add(vid);

        ap.getChildren().add(skip);
        ap.getChildren().add(introV);
        

        introP.setOnEndOfMedia(() -> {
            ap.getChildren().remove(introV);
            videoP.play();
        });

        start.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        start.setOnMouseEntered(e -> {start.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");ap.getChildren().add(sv1);});
        start.setOnMouseExited(e -> {start.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");ap.getChildren().remove(sv1);});

        options.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        options.setOnMouseEntered(e -> {options.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");ap.getChildren().add(sv);});
        options.setOnMouseExited(e -> {options.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");ap.getChildren().remove(sv);});

        exit.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        exit.setOnMouseEntered(e -> {exit.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");ap.getChildren().add(sv2);});
        exit.setOnMouseExited(e -> {exit.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");ap.getChildren().remove(sv2);});
        
        mp.setVolume(0.7);
        videoP.setOnStopped(() -> {
            mp.setAutoPlay(true);
            underlineAnimation.play();
        });
        introV.setOnMouseClicked(event -> {
            introP.stop();
            ap.getChildren().remove(introV);
        });
        introP.setOnStopped(() -> {
            videoP.setAutoPlay(true);
        });

        ap.setMargin(underline,new Insets(0,0,700,0));
        ap.setMargin(line, new Insets(0,0,370,0));

        
        StackPane.setAlignment(mv, Pos.CENTER);
        mv.fitWidthProperty().bind(ap.widthProperty());
        mv.fitHeightProperty().bind(ap.heightProperty());

        start.setPrefSize(300, 30);
        options.setPrefSize(300, 30);
        exit.setPrefSize(300, 30);
        //ap.setBackground(backgroundStyle);
        
        sliceP.setOnEndOfMedia(()-> {sliceP.stop();});
        mp.setOnEndOfMedia(()->{mp.setCycleCount(MediaPlayer.INDEFINITE);});

        StackPane startPane = new StackPane();
        Scene startScene = new Scene(startPane, 1280, 720);

        StackPane easyPane = new StackPane();
        Scene easyScene = new Scene(easyPane, 1280, 720);
        Text instructions = new Text("Game Instructions:");
        instructions.setFont(Font.font("Onyx", 55));
        instructions.setFill(Color.DARKRED);
        Text desc = new Text("Welcome to EASY mode! \n Your objective is to survive. In EASY mode, you have less lanes, more starting resources, \n and less titans to defend your base from! \n \n Titans will attack each lane you have, and since you have chosen EASY mode, you have 3 lanes to defend! \n To defend your lanes, you have to purchase weapons using your resources! \n \n Good luck, fighter. We're all depending on you! ");
        desc.setFont(Font.font("Onyx",40));
        desc.setTextAlignment(TextAlignment.CENTER);
        Button next = this.next;
        next.setFont(Font.font("Onyx",50));
        next.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        next.setOnMouseEntered(e -> next.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        next.setOnMouseExited(e -> next.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        easyPane.setMargin(next,new Insets(500, 0, 0, 0));
        StackPane easyGame = new StackPane();
        Scene easyStart = new Scene(easyGame,1280,720);
        next.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                primaryStage.setScene(easyStart);
                primaryStage.setFullScreen(true);
                
                
                try {
					battle = new Battle(1, 0, 50, 3, 250);
					temp.addAll(battle.getLanes());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
            } else
                sliceP.play();
            primaryStage.setScene(easyStart);
            try {
				battle = new Battle(1, 0, 50, 3, 250);
				temp.addAll(battle.getLanes());
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
        });
        Button back3 = new Button("Return To Menu");
        //easyGame.setBackground(backgroundStyle);
        easyGame.getChildren().add(iv4);
        easyGame.getChildren().add(back3);
        easyGame.setMargin(back3, new Insets(0, 1100, -700, 0));

        ImageView backeasy = new ImageView(new Image("rectanglebackeasy.png"));
        easyGame.getChildren().add(backeasy);
        Rectangle info = new Rectangle (200,600);
        info.setStroke(Color.BLACK);
        info.setFill(Color.WHITESMOKE);
        info.setStrokeWidth(2);
        easyGame.setMargin(info,new Insets(0, 0, 0, 1100));
        easyGame.getChildren().add(info);
        Rectangle info2 = new Rectangle(200,600);
        info2.setStroke(Color.BLACK);
        info2.setFill(Color.WHITESMOKE);
        info2.setStrokeWidth(2);
        easyGame.setMargin(info2,new Insets(0,0,0,-1100));
        easyGame.getChildren().add(info2);

        
        
        
        

        StackPane.setMargin(skip, new Insets(0, 0, -600, 0));
        StackPane.setMargin(text, new Insets(0, 0, 500, 0));
        StackPane.setMargin(start, new Insets(0, 0, 200, 0));
        StackPane.setMargin(options, new Insets(0, 0, 0, 0));
        StackPane.setMargin(exit, new Insets(0, 0, -400, 0));
        
        ap.setMargin(sv1, new Insets(0,0,200,0));
        ap.setMargin(sv2,new Insets(0,0,-400,0));
        ap.setMargin(sv8,new Insets(0,0,-200,0));

        Scene menu = new Scene(ap, 1280, 720);
        start.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                primaryStage.setScene(startScene);
                primaryStage.setFullScreen(true);
            } else
                sliceP.play();
            primaryStage.setScene(startScene);
        });
        startPane.getChildren().add(iv2);
        Text mode = new Text("Choose your difficulty!");
        mode.setFill(Color.WHITE);
        mode.setStroke(Color.BLACK);
        Rectangle border = new Rectangle(1100, 600);
        border.setStroke(Color.BLACK); // Set the border color
        border.setFill(Color.WHITE); // Set the fill color to transparent
        border.setStrokeWidth(2); // Set the border width
        easyPane.setMargin(instructions, new Insets(0,0,500,0));
        mode.setFont(Font.font("Onyx", 80));
        
        startPane.setMargin(mode, new Insets(0, 0, 500, 0));

        startPane.getChildren().add(mode);
        Button easy = new Button("EASY");
        Button hard = new Button("HARD");
        Button back = new Button("BACK");
        Button back2 = new Button("BACK");
        
        
        //easyPane.setBackground(backgroundStyle);
        easyPane.getChildren().add(iv3);
        easyPane.getChildren().add(back2);
        easyPane.getChildren().add(border);
        easyPane.getChildren().add(instructions);
        easyPane.getChildren().add(desc);
        easyPane.getChildren().add(next);
        

        

        easy.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true) {
                sliceP.play();
                primaryStage.setScene(easyScene);
                primaryStage.setFullScreen(true);
            } else
                sliceP.play();
            primaryStage.setScene(easyScene);
        });

        
        startPane.setMargin(back, new Insets(0, 1100, -700, 0));
        easyPane.setMargin(back2, new Insets(0, 1100, -700, 0));
        back.setPrefSize(300, 20);
        back2.setPrefSize(300, 20);
        back3.setPrefSize(300, 100);
        double[] points = {0, 5, 10, 0, 0, -5};
        back.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true) {

                sliceP.play();
                underlineAnimation.play();
                primaryStage.setScene(menu);
                primaryStage.setFullScreen(true);
            } else
            	sliceP.play();
            underlineAnimation.play();
                primaryStage.setScene(menu);
        });

        back2.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true) {

            	sliceP.play();
            	underlineAnimation.play();
                primaryStage.setScene(startScene);
                primaryStage.setFullScreen(true);
            } else
            	sliceP.play();
            underlineAnimation.play();
                primaryStage.setScene(startScene);
        });
        back3.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true) {

            	sliceP.play();
            	underlineAnimation.play();
                primaryStage.setScene(menu);
                primaryStage.setFullScreen(true);
                temp.clear();
            } else
            	sliceP.play();
            underlineAnimation.play();
                primaryStage.setScene(menu);
                temp.clear();
        });

        easy.setFont(Font.font("Onyx", 30));
        hard.setFont(Font.font("Onyx", 30));
        easy.setPrefSize(150, 30);
        hard.setPrefSize(150, 30);
        startPane.setMargin(easy, new Insets(0, 0, 100, 0));
        startPane.setMargin(hard, new Insets(0, 0, -100, 0));

        startPane.getChildren().addAll(easy, hard, back);

        mv.setOnMouseClicked(event -> {
            skip.setText("");
            videoP.stop();
            ap.getChildren().remove(mv);
        });
        exit.setOnAction(event -> {
            primaryStage.close();
        });

        easy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        easy.setOnMouseEntered(e -> {easy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");startPane.getChildren().add(sv3);});
        easy.setOnMouseExited(e -> {easy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");startPane.getChildren().remove(sv3);});
        
        hard.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        hard.setOnMouseEntered(e -> {hard.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");startPane.getChildren().add(sv4);});
        hard.setOnMouseExited(e -> {hard.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");startPane.getChildren().remove(sv4);});
        
        back.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        back.setOnMouseEntered(e -> back.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        back.setOnMouseExited(e -> back.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        back2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        back2.setOnMouseEntered(e -> back2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        back2.setOnMouseExited(e -> back2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        back3.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        back3.setOnMouseEntered(e -> back3.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        back3.setOnMouseExited(e -> back3.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        startPane.setMargin(sv3, new Insets(0,0,100,0));
        startPane.setMargin(sv4, new Insets(0,0,-100,0));
        
        easy.setFont(Font.font("Onyx", 50));
        hard.setFont(Font.font("Onyx", 50));
        back.setFont(Font.font("Onyx", 40));
        back2.setFont(Font.font("Onyx", 40));
        back3.setFont(Font.font("Onyx", 40));
        
        
        
        StackPane hardPane = new StackPane();
        Scene hardGame = new Scene(hardPane,1280,720);
        GridPane hardGrid = new GridPane();
        
        
        
        Button back4 = new Button("Return To Menu");
        //hardPane.setBackground(backgroundStyle);
        hardPane.getChildren().add(iv5);
        hardPane.getChildren().add(back4);
        hardPane.setMargin(back4, new Insets(0, 1100, -700, 0));
        ImageView backHard = new ImageView(new Image("rectangleback.png"));
        hardPane.getChildren().add(backHard);
        Rectangle info3 = new Rectangle (200,600);
        info3.setStroke(Color.BLACK);
        info3.setFill(Color.WHITESMOKE);
        info3.setStrokeWidth(2);
        hardPane.setMargin(info3,new Insets(0, 0, 0, 1100));
        hardPane.getChildren().add(info3);
        Rectangle info4 = new Rectangle(200,600);
        info4.setStroke(Color.BLACK);
        info4.setFill(Color.WHITESMOKE);
        info4.setStrokeWidth(2);
        hardPane.setMargin(info4,new Insets(0,0,0,-1100));
        hardPane.getChildren().add(info4);
        //hardPane.getChildren().add(Model.easyBox);
        
        back4.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        back4.setOnMouseEntered(e -> back4.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        back4.setOnMouseExited(e -> back4.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        back4.setFont(Font.font("Onyx", 40));
        back4.setOnMouseClicked(event -> primaryStage.setScene(menu));
        
        back4.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                underlineAnimation.play();
                primaryStage.setScene(menu);
                primaryStage.setFullScreen(true);
                temp.clear();
            } else
                sliceP.play();
            underlineAnimation.play();
            primaryStage.setScene(menu);
            temp.clear();
        });
        
        StackPane hardDesc = new StackPane();
        Scene hardDes = new Scene(hardDesc, 1280, 720);
        Text instructions2 = new Text("Game Instructions:");
        instructions2.setFont(Font.font("Onyx", 55));
        instructions2.setFill(Color.DARKRED);
        Text desc2 = new Text("Welcome to HARD mode! \n Your objective is to survive. In hard mode, you have more lanes, less starting resources, \n and more titans to defend your base from! \n \n Titans will attack each lane you have, and since you have chosen HARD mode, you have 5 lanes to defend! \n To defend your lanes, you have to purchase weapons using your resources! \n \n Good luck, fighter. We're all depending on you! ");
        desc2.setFont(Font.font("Onyx",40));
        desc2.setTextAlignment(TextAlignment.CENTER);
        Button next2 = this.next2;
        next2.setFont(Font.font("Onyx",50));
        next2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        next2.setOnMouseEntered(e -> next2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        next2.setOnMouseExited(e -> next2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        hardDesc.setMargin(next2,new Insets(500, 0, 0, 0));
        


        
       
        
        hard.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                primaryStage.setScene(hardDes);
                primaryStage.setFullScreen(true);
            } else
                sliceP.play();
            primaryStage.setScene(hardDes);
        });
        Rectangle border2 = new Rectangle(1100, 600);
        border2.setStroke(Color.BLACK); // Set the border color
        border2.setFill(Color.WHITE); // Set the fill color to transparent
        border2.setStrokeWidth(2); // Set the border width
        hardDesc.setMargin(instructions2, new Insets(0,0,500,0));
        Button back5 = new Button("BACK");
        back5.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        back5.setOnMouseEntered(e -> back5.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        back5.setOnMouseExited(e -> back5.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        back5.setFont(Font.font("Onyx", 40));
        back5.setPrefSize(300, 100);
        hardDesc.setMargin(back5, new Insets(0, 1100, -700, 0));
        
        back5.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                primaryStage.setScene(startScene);
                primaryStage.setFullScreen(true);
            } else
                sliceP.play();
            primaryStage.setScene(startScene);
        });
        hardDesc.getChildren().add(iv7);
        hardDesc.getChildren().add(back5);
        hardDesc.getChildren().add(border2);
        hardDesc.getChildren().add(instructions2);
        hardDesc.getChildren().add(desc2);
        hardDesc.getChildren().add(next2);
        
        StackPane optionPane = new StackPane();
        Scene optionsScene = new Scene(optionPane,1280,720);
        options.setOnMouseClicked(event -> {
        	if(primaryStage.isFullScreen()==true){
        		
        		sliceP.play();
        	primaryStage.setScene(optionsScene);
        	primaryStage.setFullScreen(true);}
        	else
        		sliceP.play();
        	primaryStage.setScene(optionsScene);
        	
        });
        ToggleButton fullscreen = new ToggleButton("FULLSCREEN");
        fullscreen.setOnMouseClicked(event -> {
        	if(primaryStage.isFullScreen()==true){
        		primaryStage.setFullScreen(false);
        	}
        	else
        		primaryStage.setFullScreen(true);
        });
        
        fullscreen.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        fullscreen.setOnMouseEntered(e -> fullscreen.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        fullscreen.setOnMouseExited(e -> fullscreen.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        fullscreen.setFont(Font.font("Onyx",70));
        optionPane.setMargin(fullscreen, new Insets(0,700,0,0));
        
       // optionPane.setBackground(backgroundStyle);
        optionPane.getChildren().add(iv6);
        optionPane.getChildren().add(fullscreen);
        
        Button backOpt = new Button("BACK TO MENU");

        backOpt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        backOpt.setOnMouseEntered(e -> backOpt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        backOpt.setOnMouseExited(e -> backOpt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        backOpt.setFont(Font.font("Onyx",55));
        
        backOpt.setPrefSize(300, 300);
        
        backOpt.setOnMouseClicked(event ->{
        	if(primaryStage.isFullScreen()==true){
        		sliceP.play();
        		underlineAnimation.play();
        		primaryStage.setScene(menu);
        		primaryStage.setFullScreen(true);
        	}
        	else
        		sliceP.play();
        	underlineAnimation.play();
        	primaryStage.setScene(menu);
        });
        
        optionPane.setMargin(backOpt, new Insets(0, 1100, -600, 0));
        optionPane.getChildren().add(backOpt);
        
        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setOrientation(Orientation.VERTICAL);
        volumeSlider.setMaxWidth(60);
        volumeSlider.setMaxHeight(400);
        volumeSlider.setStyle("-fx-control-inner-background: red; " +
                "-fx-background-color: red, red, darkred; " +
                "-fx-background-insets: 0, 1, 2; " +
                "-fx-background-radius: 2, 1, 0; " +
                "-fx-border-color: white; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 2; " +
                "-fx-border-style: solid; " +
                "-fx-thumb: -fx-control-inner-background; " +
                "-fx-tick-mark-fill: white; " +
                "-fx-tick-label-fill: white; " +
                "-fx-tick-label-text-fill: white; " +
                "-fx-tick-label-text-fill: white;");
        optionPane.setMargin(volumeSlider, new Insets(0,0,0,500));
        Button mpPlay = new Button("PLAY");
        mpPlay.setOnMouseClicked(event -> mp.play());
        optionPane.getChildren().add(mpPlay);
        mpPlay.setPrefSize(150,50);
        mpPlay.setFont(Font.font("Onyx",50));
        mpPlay.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        mpPlay.setOnMouseEntered(e -> mpPlay.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        mpPlay.setOnMouseExited(e -> mpPlay.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        
        Button mpStop = new Button("STOP");
        mpStop.setOnMouseClicked(event -> mp.stop());
        optionPane.getChildren().add(mpStop);
        mpStop.setPrefSize(150,50);
        mpStop.setFont(Font.font("Onyx",50));
        mpStop.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        mpStop.setOnMouseEntered(e -> mpStop.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        mpStop.setOnMouseExited(e -> mpStop.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        
        optionPane.setMargin(mpPlay, new Insets(0,0,200,700));
        optionPane.setMargin(mpStop, new Insets(200,0,0,700));


        mp.volumeProperty().bind(volumeSlider.valueProperty());
        optionPane.getChildren().add(volumeSlider);

        
        next2.setOnAction(event -> {
            if (primaryStage.isFullScreen() == true){
                sliceP.play();
                try {
					battle = new Battle(1, 0, 50, 5, 125);
					temp.addAll(battle.getLanes());
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
                primaryStage.setScene(hardGame);
                primaryStage.setFullScreen(true);
            } else
                sliceP.play();
            try {
				battle = new Battle(1, 0, 50, 5, 125);
				temp.addAll(battle.getLanes());
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
            System.out.println("Hard mode selected");
            primaryStage.setScene(hardGame);
        });

        int score =0; 
        if (battle != null) {
            score = battle.getScore();
        }

         scoreText = new Text("Score: 0 ");

        hardPane.getChildren().add(scoreText);
        hardPane.setMargin(scoreText,new Insets(0,1100,500,0));
        scoreText.setFont(Font.font("Onyx",40));
         resourcesText = new Text("Resources: 0");
        hardPane.setMargin(resourcesText, new Insets(0,1100,350,0));
        hardPane.getChildren().add(resourcesText);
        resourcesText.setFont(Font.font("Onyx",40));
        int  numberOfTurns =0 ;
        if(battle!= null){
        	numberOfTurns = battle.getNumberOfTurns();
        }
       
         turnsText = new Text("Turns: 1");
        hardPane.setMargin(turnsText, new Insets(0,1100,50,0));
        hardPane.getChildren().add(turnsText);
        turnsText.setFont(Font.font("Onyx",40));
        
	     bpText = new Text("Battle Phase: " );
       
        hardPane.setMargin(bpText, new Insets(0,1100,190,0));
        hardPane.getChildren().add(bpText);
        bpText.setFont(Font.font("Onyx",40));
        Button sniperBuy = new Button("LONG RANGE SPEAR");
        Button volleyBuy = new Button("WALL SPREAD CANNON ");
        Button wallBuy = new Button("PROXIMITY TRAP");
        Button piercingBuy = new Button("ANTI-TITAN SHELL");
        
        hardPane.setMargin(sniperBuy, new Insets(0,-1100,450,0));
        hardPane.getChildren().add(sniperBuy);
        
        hardPane.setMargin(volleyBuy, new Insets(0,-1100,350,0));
        hardPane.getChildren().add(volleyBuy);
        
        hardPane.setMargin(wallBuy, new Insets(0,-1100,250,0));
        hardPane.getChildren().add(wallBuy);
        
        hardPane.setMargin(piercingBuy, new Insets(0,-1100,150,0));
        hardPane.getChildren().add(piercingBuy);
        
        //Image sniperIMG = new Image("file:///C:/Users/Ahmed/Desktop/weapon2.gif");
        //ImageView sniperIMGV = new ImageView(sniperIMG);
        
        //sniperIMGV.setPreserveRatio(true);
        //sniperIMGV.setFitHeight(150);
        //sniperIMGV.setFitWidth(150);
        

        
        
        
        //Image piercingIMG = new Image("file:///C:/Users/Ahmed/Desktop/weapon1.png");
        //ImageView piercingIMGV = new ImageView(piercingIMG);
        //hardPane.setMargin(piercingIMGV, new Insets(0,700,350,0));
        //piercingIMGV.setPreserveRatio(true);
        //piercingIMGV.setFitHeight(150);
        //piercingIMGV.setFitWidth(150);
        
        piercingBuy.setOnMouseClicked(event -> {
        	weaponCode = 1;
        	
        	hardPane.getChildren().add(laneButtonsHard);
        System.out.println("Bought");});
        
        
        //Image volleyIMG = new Image("file:///C:/Users/Ahmed/Desktop/weapon3.gif");
        //ImageView volleyIMGV = new ImageView(volleyIMG);
        //hardPane.setMargin(volleyIMGV, new Insets(0,700,200,0));
        //volleyIMGV.setPreserveRatio(true);
        //volleyIMGV.setFitHeight(150);
        //volleyIMGV.setFitWidth(150);
        
        volleyBuy.setOnMouseClicked(event -> {
        	
        	
        	try {
        	//weaponStage();
        		weaponCode = 3;
        		hardPane.getChildren().add(laneButtonsHard);}
		 catch (Exception e1) {
			e1.printStackTrace();
			
		}
        System.out.println("Bought");});
        
        
        
        //Image wallTrapIMG = new Image("file:///C:/Users/Ahmed/Desktop/weapon4.png");
        //ImageView wallTrapIMGV = new ImageView(wallTrapIMG);
        //hardPane.setMargin(wallTrapIMGV, new Insets(0,700,50,0));
        //wallTrapIMGV.setPreserveRatio(true);
        //wallTrapIMGV.setFitHeight(150);
        //wallTrapIMGV.setFitWidth(150);
        
        wallBuy.setOnMouseClicked(event -> {
        	
        	
        	try {
        		weaponCode = 4;
        		hardPane.getChildren().add(laneButtonsHard);} 
		 catch (Exception e1) {
			e1.printStackTrace();
			
		}

        System.out.println("Bought");});
        
        
        VBox hardLanes = new VBox();
        hardLanes.setMaxSize(700,700);
        
        
        VBox weaponBox = new VBox();
        weaponBox.setMaxSize(200,700);
        
        
        GridPane gridLane1 = new GridPane();
        gridLane1.setPrefSize(700, 140);
        GridPane gridLane2 = new GridPane();
        gridLane2.setPrefSize(700, 140);
        GridPane gridLane3 = new GridPane();
        gridLane3.setPrefSize(700, 140);
        GridPane gridLane4 = new GridPane();
        gridLane4.setPrefSize(700, 140);
        GridPane gridLane5 = new GridPane();
        gridLane5.setPrefSize(700, 140);
        
        GridPane[] gridLane = new GridPane[5];
        for(int i = 0; i< gridLane.length; i++){
        	gridLane[i] = new GridPane();
        	gridLane[i].setPrefSize(700,140);
        	gridLane[i].setStyle("-fx-border-color: black;-fx-background-color: transparent");
        	hardLanes.getChildren().add(gridLane[i]);

        }
        
        
        GridPane gridWeapon1 = new GridPane();
        gridWeapon1.setPrefSize(140,180);
        GridPane gridWeapon2 = new GridPane();
        gridWeapon2.setPrefSize(140,180);
        GridPane gridWeapon3 = new GridPane();
        gridWeapon3.setPrefSize(140,180);
        GridPane gridWeapon4 = new GridPane();
        gridWeapon4.setPrefSize(140,180);
        GridPane gridWeapon5 = new GridPane();
        gridWeapon5.setPrefSize(140,180);

        
        
        VBox easyLanes = new VBox();
        easyLanes.setMaxSize(700,600);
        
        VBox weaponBox2 = new VBox();
        weaponBox2.setMaxSize(200,700);
        
        
        
        GridPane easygridLane1 = new GridPane();
        easygridLane1.setPrefSize(700, 200);
        GridPane easygridLane2 = new GridPane();
        easygridLane2.setPrefSize(700, 200);
        GridPane easygridLane3 = new GridPane();
        easygridLane3.setPrefSize(700, 200);
        
        GridPane easygridWeapon1 = new GridPane();
        easygridWeapon1.setPrefSize(140,200);
        GridPane easygridWeapon2 = new GridPane();
        easygridWeapon2.setPrefSize(140,200);
        GridPane easygridWeapon3 = new GridPane();
        easygridWeapon3.setPrefSize(140,200);
        
        weaponBox2.getChildren().addAll( easygridWeapon1, easygridWeapon2, easygridWeapon3);
        easygridWeapon1.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        easygridWeapon2.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        easygridWeapon3.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        
        easyGame.setMargin(weaponBox2, new Insets(0,700,-100,0));
        easygridLane1.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        easygridLane2.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        easygridLane3.setStyle("-fx-border-color: black;-fx-background-color: transparent");







        scoreText2.setFont(Font.font("Onyx",40));
        resourcesText2.setFont(Font.font("Onyx",40));
        bpText2.setFont(Font.font("Onyx",40));
        turnsText2.setFont(Font.font("Onyx",40));
        easyGame.getChildren().addAll(scoreText2,resourcesText2, bpText2, turnsText2);
        easyGame.setMargin(scoreText2, new Insets(0,1100,500,0));
        easyGame.setMargin(resourcesText2, new Insets(0,1100,350,0));
        easyGame.setMargin(bpText2, new Insets(0,1100,200,0));
        easyGame.setMargin(turnsText2, new Insets(0,1100,50,0));

        GridPane[] gridLaneEasy = new GridPane[3];
        for(int i = 0; i< 3; i++){
	gridLaneEasy[i] = new GridPane();
	gridLaneEasy[i].setPrefSize(700,200);
	gridLaneEasy[i].setStyle("-fx-border-color: black;-fx-background-color: transparent");
	easyLanes.getChildren().add(gridLaneEasy[i]);
        }

        Button sniperBuy2 = new Button("LONG RANGE SPEAR");
        Button volleyBuy2 = new Button("WALL SPREAD CANNON ");
        Button wallBuy2 = new Button("PROXIMITY TRAP");
        Button piercingBuy2 = new Button("ANTI-TITAN SHELL");

        easyGame.setMargin(sniperBuy2, new Insets(0,-1100,500,0));
        easyGame.getChildren().add(sniperBuy);

        easyGame.setMargin(volleyBuy2, new Insets(0,-1100,350,0));
        easyGame.getChildren().add(volleyBuy);

        easyGame.setMargin(wallBuy2, new Insets(0,-1100,200,0));
        easyGame.getChildren().add(wallBuy);

        easyGame.setMargin(piercingBuy2, new Insets(0,-1100,50,0));
        easyGame.getChildren().add(piercingBuy);

        easyGame.getChildren().addAll(easyLanes);

        easyGame.setMargin(easyLanes, new Insets(0,-200,0,0));
        easyGame.getChildren().add(weaponBox2);
        Button pass2 = new Button("PASS TURN");


        Text laneInfoEasy1 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoEasy2 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoEasy3 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        easygridWeapon1.getChildren().add(laneInfoEasy1);
        easygridWeapon2.getChildren().add(laneInfoEasy2);
        easygridWeapon3.getChildren().add(laneInfoEasy3);
        StackPane gameOver = new StackPane();
        Scene gameOvers = new Scene(gameOver, 1280,720);


        easyGame.getChildren().add(pass2);
        easyGame.setMargin(pass2, new Insets(0,-1100,-500,0));


       // easyGame.getChildren().add(sniperIMGV);

        easyGame.setMargin(laneButtonsEasy2, new Insets(0,-1270,-350,0));
        laneButtonsEasy2.setMaxSize(300, 300);

        sniperBuy2.setOnAction(event -> {
	
	
	
	
	try{
		weaponCode = 2;
		easyGame.getChildren().add(laneButtonsEasy2);
	}
	
	catch(Exception e2){
		e2.printStackTrace();
	}
});

piercingBuy2.setOnAction(event -> {
	
	
	try{
		weaponCode = 1;
		easyGame.getChildren().add(laneButtonsEasy2);
	}
	
	catch(Exception e2){
		e2.printStackTrace();
	}
});

volleyBuy2.setOnAction(event -> {
	
	
	try{
		weaponCode = 3;
		easyGame.getChildren().add(laneButtonsEasy2);
	}
	
	catch(Exception e2){
		e2.printStackTrace();
	}
});

wallBuy2.setOnAction(event -> {
	
	
	try{
		weaponCode = 4;
		easyGame.getChildren().add(laneButtonsEasy2);
	}
	
	catch(Exception e2){
		e2.printStackTrace();
	}
});


Button lane1e = new Button("✯Lane 1");
Button lane2e = new Button("✯Lane 2");
Button lane3e = new Button("✯Lane 3");

ProgressBar laneHealthE1 = new ProgressBar();
ProgressBar laneHealthE2 = new ProgressBar();
ProgressBar laneHealthE3 = new ProgressBar();


laneButtonsEasy2.getChildren().addAll(lane1e,lane2e,lane3e);

lane1e.setOnAction(event -> {
	try{
		
		easyGame.getChildren().remove(laneButtonsEasy2);
battle.purchaseWeapon(weaponCode, temp.get(0));
updateInfo(scoreText2, resourcesText2, turnsText2, bpText2);
//updateInfo(scoreText2, resourcesText2, bpText2, turnsText2);
updateLaneInfoEasy(laneInfoEasy1, laneInfoEasy2, laneInfoEasy3);

laneHealthE1.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
laneHealthE2.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
laneHealthE3.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
for(int i = 0; i<5;i++){
	titanLaneSpawn(gridLaneEasy[i], temp.get(i));
}
	}

	catch(InsufficientResourcesException e){
		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item!");
	}
	catch(InvalidLaneException a){
		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
};});

lane2e.setOnAction(event -> {
	try{
		
		easyGame.getChildren().remove(laneButtonsEasy2);
battle.purchaseWeapon(weaponCode, temp.get(1));
updateInfo(scoreText2, resourcesText2, turnsText2, bpText2);
updateLaneInfoEasy(laneInfoEasy1, laneInfoEasy2, laneInfoEasy3);

laneHealthE1.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
laneHealthE2.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
laneHealthE3.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
for(int i = 0; i<5;i++){
	titanLaneSpawn(gridLaneEasy[i], temp.get(i));
}
	}

	catch(InsufficientResourcesException e){
		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item!");
	}
	catch(InvalidLaneException a){
		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
};});
lane3e.setOnAction(event -> {
	try{
		
		easyGame.getChildren().remove(laneButtonsEasy2);
battle.purchaseWeapon(weaponCode, temp.get(2));
updateLaneInfoEasy(laneInfoEasy1, laneInfoEasy2, laneInfoEasy3);

laneHealthE1.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
laneHealthE2.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
laneHealthE3.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
updateInfo(scoreText2, resourcesText2, turnsText2, bpText2);
for(int i = 0; i<5;i++){
	titanLaneSpawn(gridLaneEasy[i], temp.get(i));
}
	}

	catch(InsufficientResourcesException e){
		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item!");
	}
	catch(InvalidLaneException a){
		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
};});

emptyVbox2.setMaxSize(500, 500);
        weaponBox.getChildren().addAll(gridWeapon1,gridWeapon2,gridWeapon3,gridWeapon4,gridWeapon5);
        
        gridWeapon1.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridWeapon2.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridWeapon3.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridWeapon4.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridWeapon5.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        
        hardPane.setMargin(weaponBox, new Insets(0,700,0,0));
        
        
        
        gridLane1.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridLane2.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridLane3.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridLane4.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        gridLane5.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        hardPane.getChildren().add(hardLanes);
        hardLanes.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        hardPane.setMargin(hardLanes, new Insets(0,-200,0,0));
        hardPane.getChildren().add(weaponBox);
        
        
        
        Button pass = new Button("PASS TURN");
        hardPane.getChildren().add(pass);
        hardPane.setMargin(pass, new Insets(0,-1100,-500,0));
        Media gameovermedia = new Media(getClass().getResource("/Resources/gameovervid.mp4").toExternalForm());
        MediaPlayer gomp = new MediaPlayer(gameovermedia);
        MediaView gomv = new MediaView(gomp);
        
        
        gameOver.getChildren().add(iv8);
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Onyx",120));
        gameOverText.setStyle("-fx-text-fill: darkred;");
        //gameOver.getChildren().add(ov);
        gameOver.getChildren().add(gameOverText);
        gameOverText.setFill(Color.WHITE);
        gameOverText.setStroke(Color.BLACK);
        Button return2 = new Button("RETURN TO MENU");
        return2.setFont(Font.font("Onyx",50));
        return2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        return2.setOnMouseEntered(e -> return2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        return2.setOnMouseExited(e -> return2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        gameOver.getChildren().add(return2);
        
        gameOver.setMargin(return2, new Insets(0,0,-300,0));
        gameOver.setMargin(gameOverText, new Insets(0,0,350,0));
        
        
        return2.setOnAction(event -> {
        	if(primaryStage.isFullScreen()==true){
        		primaryStage.setScene(menu);
        		primaryStage.setFullScreen(true);
        		sliceP.play();
        		temp.clear();
        	}
        	else{
        		primaryStage.setScene(menu);
        		sliceP.play();
        		temp.clear();
        	}
        });
        
        
        
        
        
        
        Text laneInfoHard1 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoHard2 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoHard3 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoHard4 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        Text laneInfoHard5 = new Text("Wall Health: \n Danger Level: \n Weapon: \n");
        
        laneInfoHard1.setFont(Font.font("Onyx",18));
        laneInfoHard2.setFont(Font.font("Onyx",18));
        laneInfoHard3.setFont(Font.font("Onyx",18));
        laneInfoHard4.setFont(Font.font("Onyx",18));
        laneInfoHard5.setFont(Font.font("Onyx",18));
        
        laneInfoEasy1.setFont(Font.font("Onyx",18));
        laneInfoEasy2.setFont(Font.font("Onyx",18));
        laneInfoEasy3.setFont(Font.font("Onyx",18));
       
ProgressBar lane1healthh = new ProgressBar();
ProgressBar lane2healthh = new ProgressBar();
ProgressBar lane3healthh = new ProgressBar();
ProgressBar lane4healthh = new ProgressBar();
ProgressBar lane5healthh = new ProgressBar();


gridWeapon1.getChildren().add(lane1healthh);
gridWeapon1.setMargin(lane1healthh, new Insets(0,0,50,100));
gridWeapon2.getChildren().add(lane2healthh);
gridWeapon2.setMargin(lane2healthh, new Insets(0, 0, 50, 100));

gridWeapon3.getChildren().add(lane3healthh);
gridWeapon3.setMargin(lane3healthh, new Insets(0, 0, 50, 100));

gridWeapon4.getChildren().add(lane4healthh);
gridWeapon4.setMargin(lane4healthh, new Insets(0, 0, 50, 100));

gridWeapon5.getChildren().add(lane5healthh);
gridWeapon5.setMargin(lane5healthh, new Insets(0, 0, 50, 100));
Text finalScore = new Text();
        
        pass.setOnAction(event -> {
       	 if(battle.isGameOver()){
       		primaryStage.setScene(gameOvers);
       		 gomp.play();
       		 gomv.setOnMouseClicked(e ->{
       			 
       			 gomp.stop();
       			 gameOver.getChildren().remove(gomv);
       			 
       		 });


       		if(battle!= null){
       	        finalScore.setText("FINAL SCORE: " + battle.getScore() + "\n       TURNS: " + battle.getNumberOfTurns());
       	     finalScore.setFont(Font.font("Onyx",80));
       	     finalScore.setFill(Color.DARKRED);
       	     //finalScore.setStroke(Color.WHITE);
       	   
       	     gameOver.getChildren().add(finalScore);
       	  gameOver.getChildren().add(gomv);  }
       		 
       		 
       		gomp.setOnEndOfMedia(() -> gameOver.getChildren().remove(gomv));
          }
        	battle.passTurn();
        	for(int i = 0; i<5;i++){
        		titanLaneSpawn(gridLane[i], temp.get(i));
        	}
        	updateInfo(scoreText, resourcesText, turnsText, bpText);
        	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
        	
        	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
        	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
        	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
        	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
        	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());  

        	
        	
        });      
        VBox verticalGridPanes = new VBox();
        verticalGridPanes.setAlignment(Pos.CENTER);
        verticalGridPanes.setMaxSize(200, 700);
        verticalGridPanes.setStyle("-fx-border-color: black; -fx-background-color: transparent");
        hardPane.setMargin(verticalGridPanes, new Insets(0,700,0,0));

        for (int i = 0; i < 5; i++) {
            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(150, 150);
            gridPane.setStyle("-fx-border-color: black");
            verticalGridPanes.getChildren().add(gridPane);
        }

        // Create GridPanes for 3/4 of the rectangle
        HBox horizontalGridPanes = new HBox();
        horizontalGridPanes.setAlignment(Pos.CENTER);
        horizontalGridPanes.setMaxSize(700, 700);
        horizontalGridPanes.setStyle("-fx-border-color: black;-fx-background-color: transparent");
        hardPane.setMargin(horizontalGridPanes, new Insets(0,-200,0,0));
        

        for (int i = 0; i < 5; i++) {
            GridPane gridPane = new GridPane();
            gridPane.setPrefSize(750, 150);
            gridPane.setStyle("-fx-border-color: black");
            horizontalGridPanes.getChildren().add(gridPane);
        }
        sniperBuy.setOnMouseClicked(event -> {
        	
        	
        	try {
        	//weaponStage();
        	weaponCode = 2;
        	hardPane.getChildren().add(laneButtonsHard);}
        	
		 catch (Exception e1) {
			e1.printStackTrace();
			
		}
        //hardPane.getChildren().add(sniperIMGV);
        
        System.out.println("Bought");});
        
        
        Button lane1h = new Button("✯Lane 1");
        Button lane2h = new Button("✯Lane 2");
        Button lane3h = new Button("✯Lane 3");
        Button lane4h = new Button("✯Lane 4");
        Button lane5h = new Button("✯Lane 5");
        

        
        
        lane1h.setOnAction(event -> {
        	
        	try{
        		
        		hardPane.getChildren().remove(laneButtonsHard);
        		
        		battle.purchaseWeapon(weaponCode, temp.get(0));
        		//battle.passTurn();
        		updateInfo(scoreText, resourcesText, turnsText, bpText);
        		
            	for(int i = 0; i<5;i++){
            		titanLaneSpawn(gridLane[i], temp.get(i));
            	}
        		//sniperBuy.setOnMouseClicked(e -> gridWeapon1.getChildren().add(new ImageView(new Image("file:///C:/Users/Ahmed/Desktop/weapon2.gif"))));
        		updateResources(resourcesText);
        		//updateInfo(scoreText, resourcesText, turnsText, bpText);
            	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
            	
            	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
            	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
            	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
            	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
            	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());
        
        	
        	}
        
        	catch(InsufficientResourcesException e){
        		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item");
        	}
        	catch(InvalidLaneException a){
        		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
        };});
        
        lane2h.setOnAction(event -> {
        	try{
        		
        		hardPane.getChildren().remove(laneButtonsHard);
        battle.purchaseWeapon(weaponCode, temp.get(1));
        //battle.passTurn();
        updateInfo(scoreText, resourcesText, turnsText, bpText);
        //updateResources(resourcesText);
    	for(int i = 0; i<5;i++){
    		titanLaneSpawn(gridLane[i], temp.get(i));
    	}
    	
    	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
    	
    	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
    	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
    	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());
        	}
        
        	catch(InsufficientResourcesException e){
        		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item");
        	}
        	catch(InvalidLaneException a){
        		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
        };});
        lane3h.setOnAction(event -> {
        	
        	try{
        		
        		hardPane.getChildren().remove(laneButtonsHard);
        battle.purchaseWeapon(weaponCode, temp.get(2));
        //battle.passTurn();
        updateInfo(scoreText, resourcesText, turnsText, bpText);
        //updateResources(resourcesText);
    	for(int i = 0; i<5;i++){
    		titanLaneSpawn(gridLane[i], temp.get(i));
    	}
    	
    	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
    	
    	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
    	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
    	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());
        	}
        
        	catch(InsufficientResourcesException e){
        		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item");
        	}
        	catch(InvalidLaneException a){
        		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
        }
        	;});
        
        lane4h.setOnAction(event -> {
        	try{
        		
        		hardPane.getChildren().remove(laneButtonsHard);
        battle.purchaseWeapon(weaponCode, temp.get(3));

        updateInfo(scoreText, resourcesText, turnsText, bpText);
    	for(int i = 0; i<5;i++){
    		titanLaneSpawn(gridLane[i], temp.get(i));
    	}
    	
    	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
    	
    	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
    	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
    	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());
        	
        	}
        
        	catch(InsufficientResourcesException e){
        		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item");
        	}
        	catch(InvalidLaneException a){
        		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
        };});
        lane5h.setOnAction(event -> {
        	try{
        		
        		hardPane.getChildren().remove(laneButtonsHard);
        battle.purchaseWeapon(weaponCode, temp.get(4));
;
        updateInfo(scoreText, resourcesText, turnsText, bpText);

    	for(int i = 0; i<5;i++){
    		titanLaneSpawn(gridLane[i], temp.get(i));
    	}
    	
    	updateLaneInfo(laneInfoHard1, laneInfoHard2, laneInfoHard3, laneInfoHard4, laneInfoHard5);
    	
    	lane1healthh.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
    	lane2healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane3healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 
    	lane4healthh.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
    	lane5healthh.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth());
        	}
        	catch(InsufficientResourcesException e){
        		displayAlert("Not Enough Resources", "You do not have enough resources to purchase item");
        	}
        	catch(InvalidLaneException a){
        		displayAlert("Invalid Lane", "THIS LANE HAS BEEN LOST");
        };});
        

        laneButtonsHard.getChildren().addAll(lane1h,lane2h,lane3h,lane4h,lane5h);
    

        
        
        


        // Add ProgressBars to GridPanes with margins
        easygridWeapon1.getChildren().add(laneHealthE1);
        easygridWeapon1.setMargin(laneHealthE1, new Insets(0, 0, 50, 100));

        easygridWeapon2.getChildren().add(laneHealthE2);
        easygridWeapon1.setMargin(laneHealthE2, new Insets(0, 0, 50, 100));

        easygridWeapon3.getChildren().add(laneHealthE3);
        easygridWeapon1.setMargin(laneHealthE3, new Insets(0, 0, 50, 100));
        
		gomp.setOnEndOfMedia(() -> gameOver.getChildren().remove(gomv));
		gomv.setOnMouseClicked(e -> primaryStage.setScene(gameOvers));
		
 	     finalScore.setFont(Font.font("Onyx",80));
 	     finalScore.setFill(Color.DARKRED);
        pass2.setOnAction(event -> {
        	if(battle.isGameOver()){
        		primaryStage.setScene(gameOvers);
        		gomp.play();
          		 gomv.setOnMouseClicked(e ->{
           			 
           			 gomp.stop();
           			 gameOver.getChildren().remove(gomv);
           			 
           		 });

        		if(battle!= null){
        			finalScore.setText("FINAL SCORE: " + battle.getScore() + "\n       TURNS: " + battle.getNumberOfTurns());
              	     //finalScore.setStroke(Color.WHITE);
              	   
              	     gameOver.getChildren().add(finalScore);
        	        gameOver.getChildren().add(gomv);}
        	}
        	
        	battle.passTurn();
        	for(int i = 0; i<3;i++){
        		titanLaneSpawn(gridLaneEasy[i], temp.get(i));
        	}
        	updateInfo(scoreText2, resourcesText2, bpText2, turnsText2);
        	updateLaneInfoEasy(laneInfoEasy1, laneInfoEasy2, laneInfoEasy3);
        	
        	laneHealthE1.setProgress((double)temp.get(0).getLaneWall().getCurrentHealth()/temp.get(0).getLaneWall().getBaseHealth());  
        	laneHealthE2.setProgress((double)temp.get(1).getLaneWall().getCurrentHealth()/temp.get(1).getLaneWall().getBaseHealth());  
        	laneHealthE3.setProgress((double)temp.get(2).getLaneWall().getCurrentHealth()/temp.get(2).getLaneWall().getBaseHealth()); 

        });
        
        
        hardPane.getChildren().add(emptyVbox);
        emptyVbox.setMaxSize(500, 500);
        
        

        hardPane.getChildren().addAll(sniperBuy, piercingBuy, wallBuy, volleyBuy);
        easyGame.getChildren().addAll(sniperBuy2, piercingBuy2, wallBuy2, volleyBuy2);
        Tooltip sniperinfo = new Tooltip("Type: Sniper Cannon \n Price: 35 \n Damage: 35 ");
        Tooltip.install(sniperBuy, sniperinfo);
        Tooltip.install(sniperBuy2, sniperinfo);
        
        Tooltip piercinginfo = new Tooltip("Type: Piercing Cannon \n Price: 25 \n Damage: 10 ");
        Tooltip.install(piercingBuy, piercinginfo);
        Tooltip.install(piercingBuy2, piercinginfo);
        
        Tooltip wallinfo = new Tooltip("Type: Piercing Cannon \n Price: 75 \n Damage: 100 ");
        Tooltip.install(wallBuy, wallinfo);
        Tooltip.install(wallBuy2, wallinfo);
        
        Tooltip volleyinfo = new Tooltip("Type: Piercing Cannon \n Price: 100 \n Damage: 5 \n Min Range: 20 \n Max Range: 50");
        Tooltip.install(volleyBuy, volleyinfo);
        Tooltip.install(volleyBuy2, volleyinfo); 
        
        Tooltip easyInfo = new Tooltip("Starting Resources: 250\n Number of Lanes: 3");
        Tooltip hardInfo = new Tooltip("Starting Resources: 125\n Number of Lanes: 5");
        
        Tooltip.install(easy, easyInfo);
        Tooltip.install(hard, hardInfo);
        
        
        
        
        
        sniperBuy.setFont(Font.font("Onyx", 25));
        piercingBuy.setFont(Font.font("Onyx", 25));
        wallBuy.setFont(Font.font("Onyx", 25));
        volleyBuy.setFont(Font.font("Onyx", 25));
        pass.setFont(Font.font("Onyx", 30));
        
        sniperBuy2.setFont(Font.font("Onyx", 25));
        piercingBuy2.setFont(Font.font("Onyx", 25));
        wallBuy2.setFont(Font.font("Onyx", 25));
        volleyBuy2.setFont(Font.font("Onyx", 25));
        pass2.setFont(Font.font("Onyx", 30));
     
        
        
		sniperBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
		sniperBuy.setOnMouseEntered(e -> sniperBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
		sniperBuy.setOnMouseExited(e -> sniperBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        piercingBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        piercingBuy.setOnMouseEntered(e -> piercingBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        piercingBuy.setOnMouseExited(e -> piercingBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        wallBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        wallBuy.setOnMouseEntered(e -> wallBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        wallBuy.setOnMouseExited(e -> wallBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        volleyBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        volleyBuy.setOnMouseEntered(e -> volleyBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        volleyBuy.setOnMouseExited(e -> volleyBuy.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
		sniperBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
		sniperBuy2.setOnMouseEntered(e -> sniperBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
		sniperBuy2.setOnMouseExited(e -> sniperBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        piercingBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        piercingBuy2.setOnMouseEntered(e -> piercingBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        piercingBuy2.setOnMouseExited(e -> piercingBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        wallBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        wallBuy2.setOnMouseEntered(e -> wallBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        wallBuy2.setOnMouseExited(e -> wallBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        volleyBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        volleyBuy2.setOnMouseEntered(e -> volleyBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        volleyBuy2.setOnMouseExited(e -> volleyBuy2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        pass.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        pass.setOnMouseEntered(e -> pass.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        pass.setOnMouseExited(e -> pass.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        pass2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        pass2.setOnMouseEntered(e -> pass2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        pass2.setOnMouseExited(e -> pass2.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane1h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane1h.setOnMouseEntered(e -> lane1h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane1h.setOnMouseExited(e ->lane1h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane2h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane2h.setOnMouseEntered(e -> lane2h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane2h.setOnMouseExited(e -> lane2h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane3h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane3h.setOnMouseEntered(e -> lane3h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane3h.setOnMouseExited(e -> lane3h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane4h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane4h.setOnMouseEntered(e -> lane4h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane4h.setOnMouseExited(e -> lane4h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane5h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane5h.setOnMouseEntered(e -> lane5h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane5h.setOnMouseExited(e -> lane5h.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        
        lane1e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane1e.setOnMouseEntered(e -> lane1e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane1e.setOnMouseExited(e ->lane1e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane2e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane2e.setOnMouseEntered(e -> lane2e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane2e.setOnMouseExited(e -> lane2e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        lane3e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        lane3e.setOnMouseEntered(e -> lane3e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        lane3e.setOnMouseExited(e -> lane3e.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        
        lane1h.setFont(Font.font("Onyx", 30));
        lane2h.setFont(Font.font("Onyx", 30));
        lane3h.setFont(Font.font("Onyx", 30));
        lane4h.setFont(Font.font("Onyx", 30));
        lane5h.setFont(Font.font("Onyx", 30));
        
        lane1e.setFont(Font.font("Onyx", 30));
        lane2e.setFont(Font.font("Onyx", 30));
        lane3e.setFont(Font.font("Onyx", 30));
        
        
        
        
        
        
        

        
        TextArea ta1h = new TextArea();
        TextArea ta2h = new TextArea();
        TextArea ta3h = new TextArea();
        TextArea ta4h = new TextArea();
        TextArea ta5h = new TextArea();
        
        ta1h.setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");
        ta2h.setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");
        ta3h.setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");
        ta4h.setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");
        ta5h.setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");
        
        ta1h.setSize(140,180);
        ta2h.setSize(140,180);
        ta3h.setSize(140,180);
        ta4h.setSize(140,180);
        ta5h.setSize(140,180);
        
        laneButtonsHard.setMaxSize(200,200);
        

        
        
        
        
        
        textAreas.setMaxSize(200,700);
        textAreas.setSpacing(75);
        
        
        
        
        

        gridWeapon1.getChildren().add(laneInfoHard1);
        gridWeapon2.getChildren().add(laneInfoHard2);
        gridWeapon3.getChildren().add(laneInfoHard3);
        gridWeapon4.getChildren().add(laneInfoHard4);
        gridWeapon5.getChildren().add(laneInfoHard5);
        
        
        
        
        
        Text laneInfoHard[] = new Text[5];
        for(int i = 0; i< laneInfoHard.length; i++){
        	laneInfoHard[i] = new Text();
        	laneInfoHard[i].setText("Wall Health:\nLane Danger Level:\nLane Weapons:\n");

        	
        }
          hardPane.getChildren().add(textAreas);
          hardPane.setMargin(textAreas, new Insets(0,700,0,0));
          
          hardPane.setMargin(laneButtonsHard, new Insets(0,-1150,-150,0));
    

        
        Label hardWS = new Label("WEAPON SHOP:");
        hardWS.setStyle("-fx-font-weight: bold;");
        hardWS.setFont(Font.font("Onyx",20));
        
        Label easyWS = new Label("WEAPON SHOP:");
        easyWS.setStyle("-fx-font-weight: bold;");
        easyWS.setFont(Font.font("Onyx",20));
        
        hardPane.getChildren().add(hardWS);
        easyGame.getChildren().add(easyWS);
        hardPane.setMargin(hardWS, new Insets(0,-1100,570,0));
        easyGame.setMargin(easyWS, new Insets(0,-1100,570,0));
        
        Image icon = new Image("gameicon.png");
        
        
        Button backToMenu = new Button("BACK");
        StackPane titan = new StackPane();
        Scene titans = new Scene(titan,1280,720);
        
        titan.getChildren().add(new ImageView(new Image("titans.jpg")));
        
        mtt.setOnMouseClicked(event -> {sliceP.play();primaryStage.setScene(titans);});
        ImageView mtt1 = new ImageView(new Image("abnormalTitan.png"));
        ImageView mtt2 = new ImageView(new Image("armoredTitan.png"));
        ImageView mtt3 = new ImageView(new Image("colossalTitan.png"));
        ImageView mtt4 = new ImageView(new Image("pureTitan.png"));
        titan.getChildren().add(mtt1);
        titan.getChildren().add(mtt2);
        titan.getChildren().add(mtt3);
        titan.getChildren().add(mtt4);
        
        mtt1.setFitHeight(200);
        mtt1.setFitWidth(200);
        
        mtt2.setFitHeight(200);
        mtt2.setFitWidth(200);
        
        mtt3.setFitHeight(200);
        mtt3.setFitWidth(200);
        
        mtt4.setFitHeight(200);
        mtt4.setFitWidth(200);
        
        Text mtt1i = new Text("ABNORMAL TITAN \n HP: 100 \n DMG: 15 \n Speed: 10 \n Danger Level : 2");
        Text mtt2i = new Text("ARMORED TITAN \n HP: 200 \n DMG: 85 \n Speed: 10 \n Danger Level : 3");
        Text mtt3i = new Text("COLOSSAL TITAN \n HP: 1000 \n DMG: 100 \n Speed: 5 \n Danger Level : 4");
        Text mtt4i = new Text("PURE TITAN \n HP: 100 \n DMG: 15 \n Speed: 10 \n Danger Level : 1");
        
        mtt1i.setFont(Font.font("Onyx",40));
        mtt2i.setFont(Font.font("Onyx",40));
        mtt3i.setFont(Font.font("Onyx",40));
        mtt4i.setFont(Font.font("Onyx",40));
        

        
        
        titan.getChildren().add(mtt1i);
        titan.getChildren().add(mtt2i);
        titan.getChildren().add(mtt3i);
        titan.getChildren().add(mtt4i);
        
        
        
        titan.setMargin(mtt1, new Insets(0,700,200,0));
        titan.setMargin(mtt2, new Insets(0,200,200,0));
        titan.setMargin(mtt3, new Insets(0,-200,200,0));
        titan.setMargin(mtt4, new Insets(0,-700,200,0));
        
        titan.setMargin(mtt1i, new Insets(300,700,0,0));
        titan.setMargin(mtt2i, new Insets(300,200,0,0));
        titan.setMargin(mtt3i, new Insets(300,-200,0,0));
        titan.setMargin(mtt4i, new Insets(300,-700,0,0));
        
        backToMenu.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        backToMenu.setOnMouseEntered(e -> backToMenu.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        backToMenu.setOnMouseExited(e -> backToMenu.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        mtt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        mtt.setOnMouseEntered(e -> {mtt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;");ap.getChildren().add(sv8);});
        mtt.setOnMouseExited(e -> {mtt.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;");ap.getChildren().remove(sv8);});
        
        backToMenu.setFont(Font.font("Onyx",50));
        titan.getChildren().add(backToMenu);
        titan.setMargin(backToMenu, new Insets(0, 1100, -700, 0));
        
        backToMenu.setOnMouseClicked(event -> {sliceP.play();primaryStage.setScene(menu);});
        ap.setMargin(mtt, new Insets(0,0,-200,0));
        mtt.setFont(Font.font("Onyx", 50));
        
        Button AI = new Button("AI");
        AI.setOnMouseClicked(event -> AIPlayer(pass));
        hardPane.getChildren().add(AI);
        
        Button AIE = new Button("AI");
        AIE.setOnMouseClicked(event -> AIPlayer(pass2));
        easyGame.getChildren().add(AIE);
        
        AI.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        AI.setOnMouseEntered(e -> AI.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        AI.setOnMouseExited(e -> AI.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        hardPane.setMargin(AI, new Insets(0,1100,-150,0));
        
        AI.setFont(Font.font("Onyx", 50));
        
        AIE.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;-fx-text-fill: darkred;");
        AIE.setOnMouseEntered(e -> AIE.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: red;"));
        AIE.setOnMouseExited(e -> AIE.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: darkred;"));
        
        easyGame.setMargin(AIE, new Insets(0,1100,-150,0));
        
        AIE.setFont(Font.font("Onyx", 50));
        
      // primaryStage.setMaximized(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setScene(menu);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Attack On Titan: Utopia");
        primaryStage.setResizable(true);
        primaryStage.show();
        primaryStage.getIcons().add(icon);
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private int openPopup() {
    	 Stage popupStage = new Stage();
    	    popupStage.initModality(Modality.APPLICATION_MODAL); 
    	    popupStage.setTitle("Enter a Number");

    	    Label label = new Label("Enter a number:");
    	    TextField textField = new TextField();
    	    Button submitButton = new Button("Submit");
    	    submitButton.setDefaultButton(true); 

    	    AtomicReference<Integer> enteredNumber = new AtomicReference<>(0); 

    	    submitButton.setOnAction(e -> {
    	        
    	        try {
    	            int number = Integer.parseInt(textField.getText());
    	            System.out.println("Entered number: " + number);
    	            enteredNumber.set(number); 
    	            popupStage.close(); 
    	        } catch (NumberFormatException ex) {
    	         
    	            System.out.println("Invalid input: Please enter a valid number.");
    	        }
    	    });

    	    VBox layout = new VBox(10);
    	    layout.setPadding(new Insets(10));
    	    layout.getChildren().addAll(label, textField, submitButton);

    	    Scene scene = new Scene(layout, 250, 150);
    	    popupStage.setScene(scene);
    	    popupStage.showAndWait(); 

    	    return enteredNumber.get();
    }
    
    
   


public void weaponStage(){
	Stage weaponStage = new Stage();
	StackPane weaponList = new StackPane();
	Scene weaponScene = new Scene(weaponList,500,500);
	weaponStage.show();
	weaponStage.setScene(weaponScene);
	weaponStage.setAlwaysOnTop(true);
	Button test = new Button("TEST");
	weaponList.getChildren().add(test);
}



public void titanLaneSpawn(GridPane a, Lane l){
	a.getChildren().clear();
	for (Titan titan : l.getTitans()){
		if(titan instanceof PureTitan){
			Image i = new Image("pureTitan.png");
			ImageView iv = new ImageView(i);
			iv.setFitHeight(85);
			iv.setFitWidth(85);
			iv.setStyle("-fx-background-color: transparent");
			Tooltip tinfo = new Tooltip("PURE TITAN \n HP: 100 \n DMG: 15 \n Speed: 10 \n Danger Level : 1");
			Tooltip.install(iv, tinfo);
			
			//Label iv = new Label("pire");
			iv.setTranslateX(titan.getDistance()*12);
			a.getChildren().add(iv);
			
		}
		
		if(titan instanceof ColossalTitan){
			Image i = new Image("colossalTitan.png");
			ImageView iv = new ImageView(i);
			iv.setFitHeight(125);
			iv.setFitWidth(125);
			Tooltip tinfo = new Tooltip("COLOSSAL TITAN \n HP: 1000 \n DMG: 100 \n Speed: 5 \n Danger Level : 4");
			Tooltip.install(iv, tinfo);
			//Label iv = new Label("colossal");
			iv.setTranslateX(titan.getDistance()*12);
			a.getChildren().add(iv);
		}
		
		if(titan instanceof ArmoredTitan){
			Image i = new Image("armoredTitan.png");
			ImageView iv = new ImageView(i);
			iv.setFitHeight(85);
			iv.setFitWidth(85);
			Tooltip tinfo = new Tooltip("ARMORED TITAN \n HP: 200 \n DMG: 85 \n Speed: 10 \n Danger Level : 3");
			Tooltip.install(iv, tinfo);
			//Label iv = new Label("Armor");
			iv.setTranslateX(titan.getDistance()*12);
			a.getChildren().add(iv);
		}
		
		if(titan instanceof AbnormalTitan){
			Image i = new Image("abnormalTitan.png");
		ImageView iv = new ImageView(i);
		iv.setFitHeight(130);
		iv.setFitWidth(130);
		Tooltip tinfo = new Tooltip("ABNORMAL TITAN \n HP: 100 \n DMG: 20 \n Speed: 10 \n Danger Level : 2");	
		Tooltip.install(iv, tinfo);
		//Label iv = new Label("abnormal");
			iv.setTranslateX(titan.getDistance()*12);
			a.getChildren().add(iv);
		}
		
	}
}

public void updateInfo(Text t, Text e, Text x, Text y){
	t.setText("Score: " + battle.getScore());
	e.setText("Resources: " + battle.getResourcesGathered());
	x.setText("Turns: " + battle.getNumberOfTurns());
	y.setText("Battle Phase: \n"  + battle.getBattlePhase());
}
public void updateResources(Text t){
	t.setText("Resources: " + battle.getResourcesGathered());
}

private void displayAlert(String title, String message){
    Stage alertStage = new Stage();
    alertStage.setTitle(title);

    Label label = new Label(message);
    
    Button closeButton = new Button("GOT IT!");

    closeButton.setOnAction(event -> alertStage.close());

    BorderPane pane = new BorderPane();
    pane.setTop(label);
    pane.setCenter(closeButton);

    Scene scene = new Scene(pane, 500, 100);
    alertStage.setScene(scene);
    alertStage.show();
}

public void updateLaneInfo(Text t1, Text t2, Text t3, Text t4, Text t5){
	t1.setText("Wall Health:" + temp.get(0).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(0).getDangerLevel() + "\n Weapon: ");
	t2.setText("Wall Health:" + temp.get(1).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(1).getDangerLevel() + "\n Weapon: ");
	t3.setText("Wall Health:" + temp.get(2).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(2).getDangerLevel() + "\n Weapon: ");
	t4.setText("Wall Health:" + temp.get(3).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(3).getDangerLevel() + "\n Weapon: ");
	t5.setText("Wall Health:" + temp.get(4).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(4).getDangerLevel() + "\n Weapon: ");
	
	
	
	
	for(int i = 0; i < temp.size(); i++) {

	    Text laneText = new Text();

	    laneText.setText("Wall Health: " + temp.get(i).getLaneWall().getCurrentHealth() + "\n" +
	                     "Danger Level: " + temp.get(i).getDangerLevel() + "\nWeapon: ");
	    

	    for(Weapon weapon : temp.get(i).getWeapons()) {
	        if(weapon instanceof WallTrap) {
	            laneText.setText(laneText.getText() + "\nWall Trap ");
	        }
	        if(weapon instanceof SniperCannon) {
	            laneText.setText(laneText.getText() + "\nSniper Cannon ");
	        }
	        if(weapon instanceof VolleySpreadCannon) {
	            laneText.setText(laneText.getText() + "\nVolley Spread Cannon ");
	        }
	        if(weapon instanceof PiercingCannon) {
	            laneText.setText(laneText.getText() + "\nPiercing Cannon ");
	        }
	    }
	    

	    switch(i) {
	        case 0:
	            t1.setText(laneText.getText());
	            break;
	        case 1:
	            t2.setText(laneText.getText());
	            break;
	        case 2:
	            t3.setText(laneText.getText());
	            break;
	        case 3:
	            t4.setText(laneText.getText());
	            break;
	        case 4:
	        	t5.setText(laneText.getText());

	    }
	}}
	
	
	public void updateLaneInfoEasy(Text t1, Text t2, Text t3){
		t1.setText("Wall Health:" + temp.get(0).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(0).getDangerLevel() + "\n Weapon: ");
		t2.setText("Wall Health:" + temp.get(1).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(1).getDangerLevel() + "\n Weapon: ");
		t3.setText("Wall Health:" + temp.get(2).getLaneWall().getCurrentHealth()+ "\n" + "Danger Level: " + temp.get(2).getDangerLevel() + "\n Weapon: ");
		
		
		
		
		
		for(int i = 0; i < temp.size(); i++) {

		    Text laneText = new Text();

		    laneText.setText("Wall Health: " + temp.get(i).getLaneWall().getCurrentHealth() + "\n" +
		                     "Danger Level: " + temp.get(i).getDangerLevel() + "\nWeapon: ");
		    

		    for(Weapon weapon : temp.get(i).getWeapons()) {
		        if(weapon instanceof WallTrap) {
		            laneText.setText(laneText.getText() + "\nWall Trap ");
		        }
		        if(weapon instanceof SniperCannon) {
		            laneText.setText(laneText.getText() + "\nSniper Cannon ");
		        }
		        if(weapon instanceof VolleySpreadCannon) {
		            laneText.setText(laneText.getText() + "\nVolley Spread Cannon ");
		        }
		        if(weapon instanceof PiercingCannon) {
		            laneText.setText(laneText.getText() + "\nPiercing Cannon ");
		        }
		    }
		    

		    switch(i) {
		        case 0:
		            t1.setText(laneText.getText());
		            break;
		        case 1:
		            t2.setText(laneText.getText());
		            break;
		        case 2:
		            t3.setText(laneText.getText());
		            break;

		        // Add more cases if needed
		    }
		}
}
	public void AIPlayer(Button b) {
	    //while(!battle.isGameOver()){
		b.fire(); // Check if this actually triggers the button action
	    
	    // Debugging: Check if resources gathered are correct
	    System.out.println("Resources gathered: " + battle.getResourcesGathered());
	    
	    int mostDangerousLane = 0;
	    int maxDangerLevel = Integer.MIN_VALUE;
	    for (int i = 0; i < temp.size(); i++) {
	        int dangerLevel = temp.get(i).getDangerLevel();
	        if (dangerLevel > maxDangerLevel) {
	            maxDangerLevel = dangerLevel;
	            mostDangerousLane = i;
	        }
	    }

	    // Determine weapon type based on resources gathered
	    int weaponType = (battle.getResourcesGathered() >= 25 && battle.getResourcesGathered() < 75) ? 2 : 4;
	    
	    // Debugging: Check if correct weapon type is selected
	    System.out.println("Selected weapon type: " + weaponType);

	    try {
	        battle.purchaseWeapon(weaponType, temp.get(mostDangerousLane));
	        updateInfo(scoreText, resourcesText, turnsText, bpText);
	        //updateLaneInfo(t1, t2, t3, t4, t5);
	        System.out.println("Weapon purchased successfully.");
	    } catch (InvalidLaneException e) {
	       // displayAlert("INVALID LANE", "THIS LANE IS LOST");
	    } catch (InsufficientResourcesException e) {
	        //displayAlert("INSUFFICIENT RESOURCES", "YOU DO NOT HAVE SUFFICIENT RESOURCES TO PURCHASE THIS WEAPON");
	    }
	//}
	    while(battle.isGameOver()){
	    	b.fire();
	    }
	}
}