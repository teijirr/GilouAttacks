import java.text.DecimalFormat;
import java.util.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

// The game of Gilbert Montagné
public class Game extends Application
{
	public static ImageView chibi2;
	public static ImageView gilou2;
	
	public static Timer timer;
	public static double score = 0.0;
	private static double bestScore = 0.0;
	public static GraphicsContext gc;
	public static Canvas canvas;
	
	public static Group root;
	
	private static Scene scene;
	
	public static Timeline gilouanim;
	
	public static boolean isDone=false;
	
	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setResizable(false);
		stage.setTitle("Rekt by gilou");
		stage.centerOnScreen();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() 
		{
			@Override
			public void handle(WindowEvent arg0)
			{
				Game.timer.cancel();
			}
		});
		root = new Group();
		scene = new Scene(root);
		stage.setScene(scene);
		initGame();
		initAnimations();
		stage.show();
	}

	private static void initGame()
	{

		Image back = new Image("back.png");
		ImageView back2 = new ImageView(back);
		back2.setFitHeight(750);
		back2.setFitWidth(1500);
		root.getChildren().add(back2);
		
		canvas = new Canvas(1280, 720);
		root.getChildren().add(canvas);
		
		gc = canvas.getGraphicsContext2D();

		Font font = new Font("Verdana", 30);
		gc.setFont(font);
		gc.setFill(Color.BLUE);
		gc.fillText("Your score : " + 0.0, 600, 100);
		String truncbest = new DecimalFormat("#.##").format(bestScore);
		gc.fillText("Best score : " + truncbest, 1000, 100);
		

		Image chibi = new Image("chibi.png");
		chibi2 = new ImageView(chibi);
		chibi2.setFitHeight(150);
		chibi2.setPreserveRatio(true);
		chibi2.setTranslateY(500);
		root.getChildren().add(chibi2);
		
		Image gilou = new Image("gilou.png");
		gilou2 = new ImageView(gilou);
		gilou2.setFitHeight(150);
		gilou2.setPreserveRatio(true);
		gilou2.setTranslateY(500);
		gilou2.setTranslateX(1280);
		root.getChildren().add(gilou2);	
		
		timer = new Timer();
		timer.schedule(new HitboxChecker(), 0, 50);
	}
	
	private static void initAnimations()
	{
		gilouanim = new Timeline();
		
		gilouanim.getKeyFrames().add
		(
				new KeyFrame(Duration.millis(3000), new KeyValue (gilou2.translateXProperty(), -150))
				
		);
		gilouanim.setCycleCount(Timeline.INDEFINITE);
		gilouanim.play();
		
		Timeline up = new Timeline();

		scene.setOnKeyReleased(new EventHandler<KeyEvent>()
		{

			@Override
			public void handle(KeyEvent event)
			{
				KeyCode k = event.getCode();

				if (k == KeyCode.UP || k == KeyCode.SPACE)
				{
					up.getKeyFrames().add
					(
							new KeyFrame(Duration.millis(400), new KeyValue (chibi2.translateYProperty(), 300))
					);
					up.setCycleCount(2);
					up.setAutoReverse(true);		
					up.play();	
				}
			}
		});
	}

	public static void initLostMenu()
	{
		if (bestScore==0.0 || bestScore <= score)
		{
			bestScore = score;
		}
		
		canvas = new Canvas(1280, 720);
		root.getChildren().add(canvas);
		
		gc = canvas.getGraphicsContext2D();
		
		Font font = new Font("Verdana", 30);
		gc.setFont(font);
		gc.setFill(Color.BLUE);
		String trunc = new DecimalFormat("#.##").format(score);
		String truncbest = new DecimalFormat("#.##").format(bestScore);
		gc.fillText("You lost against Gilou, your score was " + trunc, 400, 50);
		gc.fillText("Press enter to try again", 500, 150);
		gc.fillText("Best score : " + truncbest, 1000, 100);
		
		Image giloudab = new Image("giloudab.gif");
		ImageView giloudab2 = new ImageView(giloudab);
		giloudab2.setFitHeight(400);
		giloudab2.setPreserveRatio(true);
		giloudab2.setTranslateY(200);
		giloudab2.setTranslateX(400);
		root.getChildren().add(giloudab2);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>()
		{

			@Override
			public void handle(KeyEvent event)
			{
				KeyCode k = event.getCode();

				if (k == KeyCode.ENTER && isDone==true)
				{
					root.getChildren().clear();
					score=0;
					isDone=false;
					initGame();
					initAnimations();	
				}
			
			}
			
		});
	}
	
}
