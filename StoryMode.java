/*
NOTE: Before running, make sure that the media 
path for audio is correct. 

It should look like this:

Media media = new Media("file://WHATEVER-THE-SOURCE-IS");

^ It is important that "file://" is before the source.

 */

/*
Note: the Morse Code statement calls can be found
in the makeSetUp() method, which is located close
to the bottom of the code.
-------------------------------------------------
Music by Eric Matyas
www.soundimage.org
 */

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import java.util.regex.PatternSyntaxException;

public class StoryMode extends Application {

    @Override
    public void start(Stage stage) {

        //Media Player for the audio
        Media media = new Media("file:///Users/leslie/Desktop/MorseCode/src/com/citrisky/Dark-Things-2.mp3");
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);
        player.setStartTime(Duration.seconds(0));
        player.setStopTime(Duration.seconds(49));
        player.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView view = new MediaView(player);

        //"Toggle Sound" text
        Text ts = new Text("(toggle sound)");
        ts.setFont(Font.font(null, FontWeight.BOLD, 13));
        ts.setFill(Color.WHITESMOKE);
        ts.setX(580);
        ts.setY(25);

        //Buttons to toggle sound
        //Play Button
        Button b = new Button(">");
        b.setFont(Font.font(null, FontWeight.BOLD, 13));
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-background-color: black; ");
        b.setLayoutX(580);
        b.setLayoutY(30);
        b.setOnAction(event -> {
            player.play();
        });

        //Pause Button
        Button b2 = new Button("I I");
        b2.setFont(Font.font(null, FontWeight.BOLD, 13));
        b2.setTextFill(Color.WHITE);
        b2.setStyle("-fx-background-color: black; ");
        b2.setLayoutX(620);
        b2.setLayoutY(30);
        b2.setOnAction(event -> {
            player.pause();
        });

        //Button to End Game
        Button end = new Button("end game");
        end.setFont(Font.font("Courier", FontWeight.BOLD, 15));
        end.setTextFill(Color.GREEN);
        end.setStyle("-fx-background-color: black; ");
        end.setLayoutX(330);
        end.setLayoutY(570);
        end.setOnAction(event -> {
            endScreen();
        });

        //TextField, used in later methods
        TextField textField = new TextField();


        /*
        Note: This program has many text elements,
        so that I could pair each of them with their
        own fade-in effect.
        To make it easier to track which fade-in effect
        is paired with which "Text" element, I decided
        to create more of them.
         */

        //Text for later
        Text w = new Text("\nWhat will you say?");
        w.setVisible(false);
        w.setFont(Font.font("Courier", FontWeight.NORMAL, 16));
        w.setFill(Color.BEIGE);
        w.setX(50);
        w.setY(50);

        //Setting the position of the text field
        textField.setLayoutX(50);
        textField.setLayoutY(100);
        textField.setMinWidth(250);
        textField.setVisible(false);

        //Prompt text
        Text text = new Text("\nPlease click on the text\nwhen you are ready to continue");
        text.setFont(Font.font("Courier", FontWeight.THIN, 20));
        text.setFill(Color.LIGHTBLUE);
        text.setX(50);
        text.setY(50);

        //Backstory text
        Text bs = new Text("Backstory text");
        bs.setFont(Font.font("Courier", FontWeight.NORMAL, 18));
        bs.setFill(Color.LIGHTBLUE);
        bs.setX(50);
        bs.setY(50);
        bs.setVisible(false);

        //Text (later use) to display morse code
        Text text1 = new Text("1");
        text1.setFont(Font.font("Courier", FontWeight.THIN, 16));
        text1.setFill(Color.LIGHTSTEELBLUE);
        text1.setX(50);
        text1.setY(200);
        text1.setVisible(false);

        //Text (later use) to say "Translating..."
        Text text2 = new Text("2");
        text2.setFont(Font.font("Courier", FontWeight.THIN, 15));
        text2.setFill(Color.BEIGE);
        text2.setX(50);
        text2.setY(300);
        text2.setVisible(false);

        //Text (later use) to supply translation of Morse Code
        Text text3 = new Text("3");
        text3.setFont(Font.font("Courier", FontWeight.THIN, 20));
        text3.setFill(Color.MAROON);
        text3.setX(50);
        text3.setY(350);
        text3.setVisible(false);

        //Transition for text
        FadeTransition f = new FadeTransition();
        f.setDuration(Duration.seconds(3));
        f.setNode(text);
        f.setFromValue(0.5);
        f.setToValue(1.0);
        f.setAutoReverse(true);
        f.setCycleCount(6);
        f.play();

        text.setOnMouseClicked(event -> {
            instructionsFadeOut(f);
            instructions(bs, w, textField, text1, text2, text3);
        });


        /*
        Creates a new Group object, which tells the program which elements to display:

        text - the prompt text at the beginning
        bs - the backstory text
        w - "What will you say?" text
        text1 - User input translated into Morse Code
        text2 - "translating..." text
        text3 - Morse Code translated back into English
        view - the MediaView which plays background audio
        b - the button to play audio
        b2 - the button to pause audio
        end - the button to end game
        ts - the "toggle sound" text
        textField - the textField for user input
         */

        Group root = new Group(text, bs, w, text1, text2, text3, view, b, b2, end, ts, textField);

        //Creating a scene object
        Scene scene = new Scene(root, 700, 600, Color.BLACK);

        //Setting camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        //Setting title to the Stage
        stage.setTitle("Morse Code -.-.-.- Story Mode");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();


    }

    public void instructionsFadeOut(FadeTransition f) {
        f.stop();
        f.setDuration(Duration.seconds(0.5));
        f.setFromValue(1.0);
        f.setToValue(0);
        f.setCycleCount(1);
        f.play();

    }

    public void instructions(Text text, Text w, TextField tf, Text one, Text two, Text three) {
        System.out.println("Printing backstory text...");
        //A new fade transition for this text
        FadeTransition f2 = new FadeTransition();
        f2.setDuration(Duration.seconds(3));
        f2.setNode(text);
        f2.setFromValue(0);
        f2.setToValue(1.0);
        f2.setAutoReverse(false);
        f2.setCycleCount(1);
        f2.play();


        text.setVisible(true);
        text.setFont(Font.font("Courier", FontWeight.NORMAL, 18));
        text.setText("\nIt is the middle of WWII. \n" +
                "You, a secret agent, have just uncovered an enemy\n" +
                "scheme to release nuclear weapons on U.S. soil.\n" +
                "\nIf these weapons hit U.S. soil, millions of lives\n" +
                "will be lost. \n" +
                "You realize that you need to get a message to the\n" +
                "U.S. military straight away through your only\n" +
                "method available - morse code...");


        text.setOnMouseClicked(event -> {
            f2.stop();
            f2.setDuration(Duration.seconds(0.5));
            f2.setFromValue(1.0);
            f2.setToValue(0);
            f2.setCycleCount(1);
            f2.play();

            makeSetUp(tf, w, one, two, three);

        });
    }

    public static void makeSetUp(TextField tf, Text t, Text one, Text two, Text three) {
        System.out.println("Asking for user input");

        //A new fade transition for the textField
        FadeTransition f3 = new FadeTransition();
        f3.setDuration(Duration.seconds(3));
        f3.setNode(tf);
        f3.setFromValue(0);
        f3.setToValue(1.0);
        f3.setAutoReverse(false);
        f3.setCycleCount(1);
        f3.play();

        tf.setVisible(true);

        t.setVisible(true);
        t.setFont(Font.font("Courier", FontWeight.THIN, 16));
        t.setText("\nYou have the opportunity to send the\nmilitary a message. What will you say?\n\n\n\n(note: only upper/lower case letters of the\n" +
                "English alphabet and numbers allowed...)");

        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    t.setText("\nRetype and hit enter to send something different...");
                    t.setFill(Color.GRAY);

                    System.out.println("Translated input to Morse Code");

                    String userInput = tf.getText();

                    //New Fade Transition for displaying Morse Code
                    FadeTransition f4 = new FadeTransition();
                    f4.setDuration(Duration.seconds(3));
                    f4.setNode(one);
                    f4.setFromValue(0);
                    f4.setToValue(1.0);
                    f4.setAutoReverse(false);
                    f4.setCycleCount(1);
                    f4.play();

                    one.setVisible(true);
                    one.setText("The Morse Code: \n" +
                            translateEnglish(userInput));

                    //New Fade Transition for displaying translated Morse Code
                    FadeTransition f5 = new FadeTransition();
                    f5.setDuration(Duration.seconds(1));
                    f5.setNode(two);
                    f5.setFromValue(0.5);
                    f5.setToValue(1.0);
                    f5.setAutoReverse(true);
                    f5.setCycleCount(Animation.INDEFINITE);
                    f5.play();

                    two.setVisible(true);
                    two.setText("translating...");

                    System.out.println("Translated Morse Code back into English");
                    //New Fade Transition for displaying translated Morse Code
                    FadeTransition f6 = new FadeTransition();
                    f6.setDuration(Duration.seconds(9));
                    f6.setNode(three);
                    f6.setFromValue(0);
                    f6.setToValue(1.0);
                    f6.setAutoReverse(false);
                    f6.setCycleCount(1);
                    f6.play();


                    three.setVisible(true);
                    three.setText("What the U.S. Military is seeing: \n" +
                            translateMorse(translateEnglish(userInput)));
                }
            }
        });
    }

    public static void endScreen() {
        System.out.println("\nMusic by Eric Matyas\n" +
                "www.soundimage.org");
        Platform.exit();
    }

    public static void main(String args[]) {
        launch(args);
    }

    //MorseTree methods
    public static String translateEnglish(String input) throws PatternSyntaxException {
        MorseTree t = new MorseTree();
        String result = "";
        String[] morse = input.split("\\s+");
        for (int i = 0; i < morse.length; i++) {
            String s = morse[i];
            result += t.translateToMorse(s);

            if (i < morse.length - 1) {
                result += " / ";
            }
        }
        return result;
    }

    public static String translateMorse(String input) throws PatternSyntaxException {
        MorseTree t = new MorseTree();
        String result = "";
        String[] morse = input.split("\\s+");

        for (int i = 0; i < morse.length; i++) {
            String s = morse[i];

            result += t.translateToEnglish(s);
        }
        return result;
    }


}
