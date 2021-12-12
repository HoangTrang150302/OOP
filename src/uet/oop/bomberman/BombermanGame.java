package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>(); // contains moving items
    private List<Entity> stillObjects = new ArrayList<>(); // contain still items
    private List<Entity> flames = new ArrayList<>(); // contain flames items
    public static List<Entity> staticObjects = new ArrayList<>(); // contains Items and Bricks
    public static List<Entity> damagedEntities = new ArrayList<>();
    public static char map[][];

    public static KeyInput keyInput = new KeyInput();


    /** Khoi tao game. */
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    // JavaFx -> Application -> launch -> start
    // a window -> stage
    @Override
    public void start(Stage stage) {

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);


        //Setting the title to Stage.
        stage.setTitle("Bomberman");
        // Attach the scene object to the stage
        stage.setScene(scene);
        //Display the contents of the scene
        stage.show();

        // loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        // col(31) - row(13)
       Entity bomb = new Bomb(29,11,Sprite.bomb.getFxImage());

       // Add vào list
        entities.add(bomb);
        flames.addAll(((Bomb) bomb).getFlames());
    }

    public static void printMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void createMap() {
        try {
            Scanner scf = new Scanner(new BufferedReader(new FileReader("res/levels/Level" + 1 + ".txt")));

            int lv = scf.nextInt();
            int row = scf.nextInt();
            int col = scf.nextInt();
            map = new char[row][col];
            scf.nextLine();

            // Xu ly thong tin trong file
            for (int i = 0; i < row; i++) {
                String s1 = scf.nextLine();
                System.out.println(s1);
                for (int j = 0; j < col; j++) {
                    Entity object;
                    char key = s1.charAt(j);
                    switch (key) {
                        case '#': {
                            object = new Wall(j, i, Sprite.wall.getFxImage());
                            stillObjects.add(object);
                            map[i][j] = 'w';
                            break;
                        }
                        case '*': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            object = new Brick(j, i, Sprite.brick.getFxImage());
                            staticObjects.add(object);
                            map[i][j] = 'b';
                            break;
                        }
                        case 'p': {
                            stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                            object = new Bomber(j, i, Sprite.player_right.getFxImage());
                            entities.add(object);
                            map[i][j] = 'p';
                            break;
                        }
                        default: {
                            object = new Grass(j, i, Sprite.grass.getFxImage());
                            stillObjects.add(object);
                            map[i][j] = 'g';
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Tao ra map từ các entity tĩnh. */
//    public void createMap() {
//        map = new String[WIDTH][HEIGHT];
//        for (int i = 0; i < WIDTH; i++) {
//            for (int j = 0; j < HEIGHT; j++) {
//                Entity object;
//                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                    map[i][j] = "wall";
//                    stillObjects.add(object);
//                }
//                else if (i % 2 == 0 && j % 2 == 0) {
//                    object = new Wall(i, j, Sprite.wall.getFxImage());
//                    map[i][j] = "wall";
//                    stillObjects.add(object);
//                }
//                else if (i % 3 == 0 && j % 2 == 0) {
//                    object = new Brick(i, j, Sprite.brick.getFxImage());
//                    map[i][j] = "brick";
//                    staticObjects.add(object);
//                }
//                else {
//                    object = new Grass(i, j, Sprite.grass.getFxImage());
//                    map[i][j] = "grass";
//                    stillObjects.add(object);
//                }
//            }
//        }
//    }

    public void update() {
        updateDamagedObjects();
        for (Entity entity : entities) {
            entity.update();
        }
        for (Entity flame : flames) {
            flame.update();
        }
        for (Entity stillObject : stillObjects) {
            stillObject.update();
        }
        for (Entity staticObject : staticObjects) {
            staticObject.update();
        }
    }

    public void render() {
        // Clears a portion of the canvas with a transparent color value.
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity stillObject : stillObjects) {
            stillObject.render(gc);
        }
        for (Entity entity : entities) {
            entity.render(gc);
        }
        for (Entity flame : flames) {
            flame.render(gc);
        }
        for (Entity g : staticObjects) {
            g.render(gc);
        }
    }

    public void updateDamagedObjects() {
        // update bricks and enemies
        for (int i = 0; i < entities.size(); i++) {
            checkForDamagedEntities(entities.get(i));
            for (int j = 0; j < damagedEntities.size(); j++) {
                updateStaticObjectsAndEnemies(damagedEntities.get(j));
            }

        }
        // get the explosion done (remove the flames)
        for (int i = 0; i < flames.size(); i++) {
            if (flames.get(i) instanceof Flame) {
                if (((Flame) flames.get(i)).isDone()) {
                    flames.remove(flames.get(i));
                }
            }
        }
    }

    public void checkForDamagedEntities(Entity o) {
        // remove bomb from the entites
        if (o instanceof Bomb) {
            if (((Bomb) o).isExploded()) {

                // enable   bomber go through the place used to be for the bomb
                map[(int) o.getY()][(int) o.getX()] = 'g';

                // check if the bomb damange any objects
                ((Bomb) o).handleFlameCollision(entities, staticObjects, damagedEntities);
                System.out.println(entities.remove(o));
            }
        }
    }

    public void updateStaticObjectsAndEnemies(Entity br) {
        if (br instanceof Brick) {
            if (((Brick) br).isDone()) {

                Brick brick = (Brick) br;

                // replace the tile with the grass
                damagedEntities.remove(br);
                staticObjects.remove(br);
                Entity entity = new Grass((int) br.getX(), (int) br.getY(), Sprite.grass.getFxImage());
                if (brick.getItem() != null) {
                    staticObjects.add(entity);
                } else {
                    stillObjects.add(entity);
                }
            } else {
                br.update();
            }
        }
    }
}
