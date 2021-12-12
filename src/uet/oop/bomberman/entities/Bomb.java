package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.shape.Rectangle;
import javafx.scene.media.Media;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bomb extends Entity {
    private int bombLevel = 4;

    // Danh sach cac flames hien ra khi bomb no
    private List<Flame> flames = new ArrayList<>();
    private boolean done = false;
    private boolean exploded = false;
    private int explosionCountDown = 15;
    private int tickingCountDown = 90;
    private boolean playSound = false;


    // parameter x, y for coordinator, image la anh tuong ung
    public Bomb(int x, int y, Image img) {
        super(x, y, img);
        rec = new Rectangle(x, y, 1, 1);
        setFlames();
    }

    public Bomb(int x, int y, Image img, int bombLevel) {
        super(x, y, img);
        this.bombLevel = bombLevel;
        rec = new Rectangle(x, y, 1, 1);
        setFlames();
    }

    public List<Flame> getFlames() {
        return flames;
    }

    public void setTickingCountDown(int tickingCountDown) {
        this.tickingCountDown = tickingCountDown;
    }

    public int getTickingCountDown() {
        return tickingCountDown;
    }

    public void setBombLevel(int bombLevel) {
        this.bombLevel = bombLevel;
    }

    public int getBombLevel() {
        return bombLevel;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    // Ham nay de update các image
    @Override
    public void update() {
        if (!isExploded()) {
            tickingImg();
        }
        else {
            BombermanGame.map[y][x] = 'g';
            explodingImg();
        }
    }

    public void tickingImg() {
        if (tickingCountDown <= 0) {
            if (!isPlaySound()) {
                setPlaySound(true);
            }
            setExploded(true);
        } else {
            this.img = Sprite.bombTickingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, tickingCountDown).getFxImage();
            tickingCountDown--;
        }
    }

    public void explodingImg() {
        if (explosionCountDown == 0) {
            setDone(true);
            this.img = null;
        } else {
            this.img = Sprite
                    .bombExplodeSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                            Sprite.bomb_exploded2, explosionCountDown)
                    .getFxImage();
            explosionCountDown--;
        }
    }

    /** Other entities collision with flame */
    public void handleFlameCollision(List<Entity> entities, List<Entity> staticObjects,
                                     List<Entity> damagedEntities) {
        // damage bricks
        for (Entity o : staticObjects) {
            if (o instanceof Brick) {
                for (Flame flame : flames) {
                    int oX = (int) o.getX(), fX = (int) flame.getX(), x = (int) this.getX();
                    int oY = (int) o.getY(), fY = (int) flame.getY(), y = (int) this.getY();
                    String pos = flame.getPos();

                    if (!pos.equals("left_most") && !pos.equals("down_most") &&
                            !pos.equals("right_most") && !pos.equals("top_most")) {
                        if (oX == fX && x == oX && oY - fY == 1 ||
                                oX == fX && x == oX && oY - fY == -1 ||
                                oX - fX == -1 && oY == fY && y == oY ||
                                oX - fX == 1 && oY == fY && y == oY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    } else {
                        if (oX == fX && oY == fY) {
                            ((Brick) o).setDamaged(true);
                            damagedEntities.add(o);
                        }
                    }
                }
            }
        }
    }

    public void setFlames() {
        String[] pos = {"left", "down", "right", "top", "left_most", "down_most", "right_most", "top_most", "center"};
        int[] iX = {-1, 0, 1, 0}; // left down right top
        int[] iY = {0, 1, 0, -1};

        BombermanGame.printMap();

        flames.add(new Flame(x, y, null, "center"));
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= bombLevel; j++) {
                int row = x + j * iX[i];
                int col = y + j * iY[i];
                // check xem row col co trong map k
                if (row > 0 && row < BombermanGame.WIDTH - 1 && col > 0 && col < BombermanGame.HEIGHT- 1) {
                    char flag = BombermanGame.map[col][row];
                    if (flag == 'b') {
                        System.out.println("(" + col + " " + row + ")");
                        System.out.println();
                        BombermanGame.map[col][row] = 'g';
                    }

                    if (j == bombLevel) {
                        flames.add(new Flame(x + iX[i] * bombLevel, y + iY[i] * bombLevel, null, pos[i + 4]));
                    }
                    else {
                        flames.add(new Flame(x + iX[i] * j, y + iY[i] * j, null, pos[i]));
                    }
                }

            }
        }
    }


    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
//        Media media = new Media("D:\\Nam_hai\\Semester_1\\OOP\\bomberman-starter-starter-2\\src\\bombExplosion.mp3");
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
        this.playSound = playSound;
    }

    public void playSound() {

    }

}
