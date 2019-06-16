import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.lang.model.type.NullType;
import java.util.HashMap;

import static java.lang.Math.abs;


public class Move extends Application {

    private static final int COLUMNS  =   6;
    private static final int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 113;
    private static final int HEIGHT   = 130;

    Image heropics=new Image("muybridge_run_cycle_by_cacodaemonia.png");
    ImageView hero=new ImageView(heropics);
    Image bg=new Image("Bez.jpg");
    Rectangle rectangle=new Rectangle(1200,400,200,20);
    ImageView bgg=new ImageView(bg);
    AnimationTimer timere;

    int x_przesuwbgg=0;
    int x_przesuwobiekt=0;
    int x_przesuw=0;
    int y_przesuw=0;
    boolean skok=true;


    final Animation animation = new SpriteAnimation(
            hero,
            Duration.millis(150),
            COUNT, COLUMNS,
            OFFSET_X, OFFSET_Y,
            WIDTH, HEIGHT
    );
    Pane rootl=new Pane();
    private HashMap<KeyCode,Boolean> keys = new HashMap<>();
    Point2D playerVelocity = new Point2D(0,0);

    private String przycisk="Null";
    javafx.scene.control.Label label1 = new javafx.scene.control.Label(" Press "+przycisk);
    javafx.scene.control.Label label2 = new javafx.scene.control.Label(" x = "+x_przesuw+" y = "+ y_przesuw);


    private void update() {
//        label2.setText(" x = " + x_przesuw + " y = " + y_przesuw);
       // label2.setText(" xr = " + rectangle.getTranslateX() + " xh = " + hero.getTranslateX());
        label2.setText(" xr = " + rectangle.getY() + " xh = " + hero.getTranslateY()+"  ="+(hero.getTranslateY()+rectangle.getY()));
       // System.out.println(rectangle.getTranslateX());
      //  System.out.println(hero.getTranslateX());


            if (hero.getTranslateX() < 550 && hero.getTranslateX() >= 0) {
                if (isPressed(KeyCode.UP)) {
                    // animation.setCycleCount(Animation.INDEFINITE);

                    System.out.println("up");
                }
                if (isPressed(KeyCode.LEFT)) {

                    przycisk = "LEFT";
                    label1.setText("Press = " + przycisk);

                    hero.setScaleX(-1);
                    animation.play();
                    moveX(-7);


                }
                if (isPressed(KeyCode.RIGHT)) {
                    hero.setScaleX(1);
                    przycisk = "Right";
                    label1.setText("Press = " + przycisk);
                    animation.play();
                    moveX(7);
                }

            } else if (hero.getTranslateX() > 550) {

                if (isPressed(KeyCode.LEFT)) {

                    przycisk = "LEFT";
                    label1.setText("Press = " + przycisk);
                    hero.setScaleX(-1);
                    animation.play();
                    moveX(-7);

                }
                if (isPressed(KeyCode.RIGHT)) {

                    przycisk = "RIGHT";
                    label1.setText("Press = " + przycisk);
                    hero.setScaleX(1);
                    animation.play();
                    moveXbgg(-7);
                    moverect(-7);

                }

            } else if (hero.getTranslateX() < 0) {

                if (isPressed(KeyCode.RIGHT)) {

                    przycisk = "Right";
                    label1.setText("Press = " + przycisk);
                    hero.setScaleX(1);
                    animation.play();
                    moveX(7);

                }

                if (isPressed(KeyCode.LEFT)) {

                    przycisk = "LEFT";
                    label1.setText("Press = " + przycisk);
                    hero.setScaleX(-1);
                    animation.play();
                    moveXbgg(7);
                    moverect(7);
                }

            }

            if (isPressed(KeyCode.SPACE) && skok != false) {


                przycisk = "SPACE";
                label1.setText("Press = " + przycisk);
                animation.play();

                moveY(20);


                timere.start();


                skok = false;

            }

            if (y_przesuw < -200) {

                timere.stop();

                moveY(-10);

                timere.start();

            }

            if (y_przesuw > 0) {
                // System.out.println("CHyba cie "+y_przesuw);
                skok = true;
                timere.stop();
                y_przesuw -= 20;
            }




    }
    private void moverect(int i) {
         x_przesuwobiekt+=i;
        rectangle.setTranslateX(x_przesuwobiekt);
    }

    private void moveXbgg(int i) {
        x_przesuwbgg+=i;
        bgg.setTranslateX(x_przesuwbgg);
    }

    private void moveY(int i) {


        timere = new AnimationTimer() {
            @Override
            public void handle(long now) {

                y_przesuw -= i;
                hero.setTranslateY(y_przesuw);


            }
        };

    }

    private void moveX(int i) {
        x_przesuw+=i;
        hero.setTranslateX(x_przesuw);

    }
    private void moveXleft(int i) {
        x_przesuw-=i;
        hero.setTranslateX(x_przesuw);

    }

    private boolean isPressed(KeyCode key){
        return keys.getOrDefault(key,false);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {


        label1.setTranslateX(500);
        label1.setMinSize(200,100);
        label1.setFont(Font.font("arial.ttf",50));

        label2.setTranslateX(500);
        label2.setTranslateY(100);
        label2.setTextFill(Color.web("#0076a3"));

        label2.setMinSize(200,100);
        label2.setFont(Font.font("arial.ttf",50));

        bgg.setX(-1200);

        hero.setY(325);
        hero.setX(300);

        hero.setViewport(new Rectangle2D(OFFSET_X,OFFSET_Y,WIDTH,HEIGHT));


        rootl.getChildren().add(bgg);

        rootl.getChildren().add(hero);
        rootl.getChildren().add(label1);
        rootl.getChildren().add(label2);
        rootl.getChildren().add(rectangle);


        Scene scene=new Scene(rootl,1200,500);
        scene.setOnKeyPressed(event-> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> {
            keys.put(event.getCode(), false);
            hero.setViewport(new Rectangle2D(OFFSET_X,OFFSET_Y,WIDTH,HEIGHT));
            animation.stop();

            przycisk="Null";
            label1.setText("Press = "+przycisk);


        });


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String [] args){
        launch(args);
    }
}
