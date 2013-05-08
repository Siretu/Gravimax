package objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import main.Game;
import main.Stopwatch;
import main.states.SelectLevelState;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Player extends GameObject {
	public Player(Shape s){
		super(s,Color.pink);
		canJump = true;
	}
	
	@Override
	public void onInit(Room r) {
		flag.add(OBJECT_FLAG_GRAVITY);
	}
	
	@Override
	protected void onCollide(GameObject obj, int gravityDir, int colDir, Room room) {
		if(obj.flag.has(OBJECT_FLAG_GOAL)){
			completeLevel(room);
		} else if(obj.flag.has(OBJECT_FLAG_HOSTILE)){
			die(room);
		}
		if(!obj.flag.has(OBJECT_FLAG_GHOST)){
			if(gravityDir == colDir) {
				this.canJump = true;
			}
			
			switch(colDir) {
			case UP: 
				this.correctCollisionUp(obj); 
				this.y_speed = 0;
				break;
			case DOWN:
				this.correctCollisionDown(obj);
				this.y_speed = 0;
				break;
			case LEFT: 
				this.correctCollisionLeft(obj); 
				this.x_speed = 0;
				break;
			case RIGHT: 
				this.correctCollisionRight(obj); 
				this.x_speed = 0;
				break;
			}
	
			if(obj.getColorID() != -1) {
				if(obj.getColorID() == BLOCK_BLACK) {
					room.setGravityDir(colDir);
				} else {
					if(obj.getColorID() != room.getGravityColor()) {
						room.rotateGravity(obj.getColorID());
					} else {
						room.setGravityDir(DOWN);
					}
				}
			}
		}
	}
	
	protected void die(Room room) {
		try{
			Sound fx = new Sound("data/sounds/death.wav");
			fx.play(1f,0.2f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		room.game.enterState(1, new FadeOutTransition(Color.red), new FadeInTransition(Color.red));
	}

	protected void completeLevel(Room room){
		room.timer.stop();
		try {
			Sound fx = new Sound("data/sounds/changelevel.wav");
			fx.play(1f,0.2f);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		int l = Integer.parseInt(((Game)room.game).getLevel());
		BufferedReader file = null;
		BufferedWriter bw = null;
		FileWriter fw = null;
		String highscore = null;
		String currentTry = "" + (int)room.timer.milliseconds();
		String lastTry = currentTry;
		try {
			File score = new File("data/maps/level"+l+".score");
			if (!score.exists()) {
				score.createNewFile();
				highscore = currentTry;
			} else {
				file = new BufferedReader(new FileReader("data/maps/"+score.getName()));
				highscore = file.readLine();
				file.close();
				if(Integer.parseInt(highscore) > (int) room.timer.milliseconds()){
					highscore = currentTry;
				}
			}
			
			fw = new FileWriter(score.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(highscore);
			bw.newLine();
			bw.write(lastTry);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try{
				if(bw != null){
					bw.close();
				}
				if(file != null){
					file.close();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		Level[] levels = ((SelectLevelState)room.game.getState(4)).levels;
		levels[Integer.parseInt(((Game)room.game).getLevel())-1].setLastTry(Stopwatch.toString(Integer.parseInt(lastTry)));
		levels[Integer.parseInt(((Game)room.game).getLevel())-1].setHighscore(Stopwatch.toString(Integer.parseInt(highscore)));
		
		String level = "" + (l + 1);
		File f = new File("data/maps/level"+level+".map");
		if(f.exists()){
			((Game)room.game).setLevel(level);
			room.game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		} else {
			room.game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
		}
	}

}
