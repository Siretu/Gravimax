package main;

import java.io.*;

import objects.GameObject;
import objects.Goal;
import objects.Player;
import objects.Room;
import objects.SpikeBall;
import objects.Spikes;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class MapReader {
	protected static final int BLOCK_COLORLESS = -1;
	protected static final int BLOCK_BLUE = 1;
	protected static final int BLOCK_RED = 2;
	protected static final int BLOCK_GREEN = 3;
	protected static final int BLOCK_YELLOW = 4;
	protected static final int BLOCK_BLACK = 5;
	
	private GameObject[] objects;
	private Room room;
	
	public MapReader(String filename) {
		initiateRoom(filename);
	}
	
	public void initiateRoom(String filename){
		System.out.println("Opening file");
		String mapLoc = "data/maps/";
		filename = mapLoc.concat(filename);
		readFile(filename);
		if(room == null) {
			room = new Room();
		}
		room.setObjects(objects);
	}
	
	private void readFile(String filename) {
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(filename));
			handleText(file);
		} catch (IOException e) {
			System.err.printf("%s%n in MapReader", e);
		} finally {
			try {
				if (file != null) {
					file.close();
				}
			} catch (IOException e) {
				System.err.printf("%s%n in MapReader", e);
			}
		}
		System.out.println("Done making room");
	}
	
	/**
	 * Handles the reading of the file. Assumes the file is in the correct format.
	 * @param in
	 * @throws IOException
	 */
	private void handleText(BufferedReader in) throws IOException {
		String line;
		int size = Integer.parseInt(in.readLine());
		int curIndex = 5;
		objects = new GameObject[size+4];
		
		objects[1] = new GameObject(new Rectangle(50,750,700,50),Color.blue,BLOCK_BLUE);
		objects[2] = new GameObject(new Rectangle(0,50,50,700),Color.red,BLOCK_RED);
		objects[3] = new GameObject(new Rectangle(50,0,700,50),Color.green,BLOCK_GREEN);
		objects[4] = new GameObject(new Rectangle(750,50,50,700),Color.yellow,BLOCK_YELLOW);
		
		while ((line = in.readLine()) != null) {
			int indexOfComment = line.indexOf("//");
			if(indexOfComment == 0) {
				continue;
			}
			if(indexOfComment != -1) {
				System.out.println("Line starting with comment");
				line = line.substring(0, indexOfComment).trim();
			} else {
				System.out.println("Line without comment");
			}
			
			String[] contents = line.split(":");
			System.out.println(contents[0]);
			if(contents[0].equals("map")) {
				objects[curIndex] = makeMapObject(contents);
				System.out.println("Made map object");
				curIndex++;
			} else if(contents[0].equals("player")) {
				objects[0] = new Player(new Rectangle(Integer.parseInt(contents[1]),Integer.parseInt(contents[2]),Integer.parseInt(contents[3]),Integer.parseInt(contents[4])));
				System.out.println("Made player");
			} else if(contents[0].equals("room")) {
				makeCustomRoom(contents);
				System.out.println("Made custom room");
			} else if(contents[0].equals("goal")){
				objects[curIndex] = new Goal(new Rectangle(Integer.parseInt(contents[1]),Integer.parseInt(contents[2]),50,50), Color.red);
				curIndex++;
			} else if(contents[0].equals("spikes")){
				objects[curIndex] = new Spikes(new Rectangle(Integer.parseInt(contents[1]),Integer.parseInt(contents[2]),80,40),Color.red);
				curIndex++;
			} else if(contents[0].equals("spikeball")){
				objects[curIndex] = new SpikeBall(new Rectangle(Integer.parseInt(contents[1]),Integer.parseInt(contents[2]),50,50),Color.red);
				curIndex++;
			}
		}
	}
	
	private void makeCustomRoom(String[] contents) {
		room = new Room(findColor(contents[1]), Float.parseFloat(contents[2]), Float.parseFloat(contents[3]));
	}

	private Color findColor(String str) {
		if (str.indexOf(",") != -1) {
			String[] contents = str.split(",");
			return new Color(Integer.parseInt(contents[0]),Integer.parseInt(contents[1]),Integer.parseInt(contents[2]));
		} else {
			switch(str) {
			case "black": return Color.black;
			case "yellow": return Color.yellow;
			case "blue": return Color.blue;
			case "red": return Color.red;
			case "green": return Color.green;
			case "pink": return Color.pink;
			case "cyan": return Color.cyan;
			case "orange": return Color.orange;
			case "magenta": return Color.magenta;
			case "white": return Color.white;
			case "gray": return Color.gray;
			case "darkGray": return Color.darkGray;
			case "lightGray": return Color.lightGray;
			default: return null;
			}
		}
	}
	
	private int findID(String str) {
		switch(str) {
		case "blue": return 1;
		case "red": return 2;
		case "green": return 3;
		case "yellow": return 4;
		case "black": return 5;
		default: return -1;
		}
	}
	
	private GameObject makeMapObject(String[] contents) {
		return new GameObject(new Rectangle(Integer.parseInt(contents[1]),Integer.parseInt(contents[2]),Integer.parseInt(contents[3]),Integer.parseInt(contents[4])),findColor(contents[5]),findID(contents[5]));
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void changeFile(String filename) {
		objects = null;
		room = null;
		
		readFile(filename);
		if(room == null) {
			room = new Room();
		}
		room.setObjects(objects);
	}
}