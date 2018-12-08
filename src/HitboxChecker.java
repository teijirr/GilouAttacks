import java.text.DecimalFormat;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javafx.application.Platform;

public class HitboxChecker extends TimerTask
{


	@Override
	public void run()
	{
		String trunc = new DecimalFormat("#.##").format(Game.score);
		Game.gc.clearRect(0, 0, 900, 720);
		Game.score = Game.score+0.1;
		Game.gc.fillText("Your score : " + trunc, 600, 100);
		
		if (Game.gilou2.getTranslateX() < -100)
		{
			int randomNum = ThreadLocalRandom.current().nextInt(1, 5);
			Game.gilouanim.setRate(randomNum);
		}
		
		if (Game.gilou2.getTranslateX() < 100 && Game.gilou2.getTranslateX() >= 0 && Game.chibi2.getTranslateY()>450)
		{
			Game.timer.cancel();
			
            Platform.runLater(() -> {Game.root.getChildren().clear();});
            
            Platform.runLater(() -> { Game.initLostMenu();});
            Game.isDone=true;
		}
		
		
		
	}


}
