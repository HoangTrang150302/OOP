package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.shape.Rectangle;

public class Flame extends Entity {

    private int explosionCountDown = 120;
    private String pos;
    private boolean done = false;

    // parameter x, y for coordinator, image is the picture corresponding to Flame
    public Flame(int x, int y, Image img) {
        super(x, y, img);
        rec = new Rectangle(x, y, 1, 1);
    }

    public Flame(int x, int y, Image img, String pos) {
        super(x, y, img);
        rec = new Rectangle(x, y, 1, 1);
        this.pos = pos;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isDone() {
        return done;
    }

    public int getExplosionCountDown() {
        return explosionCountDown;
    }

    public void setExplosionCountDown(int explosionCountDown) {
        this.explosionCountDown = explosionCountDown;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPos() {
        return pos;
    }

    public void explodingImg() {
        if (explosionCountDown == 0 || explosionCountDown >= 30) {
            this.img = null;
            if (explosionCountDown >= 30) {
                explosionCountDown--;
            } else {
                setDone(true);
            }
        } else {
             //System.out.println(pos);
            switch (pos) {
                case "left":
                case "right":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                                    Sprite.explosion_horizontal2, explosionCountDown)
                            .getFxImage();
                    break;
                case "top":
                case "down":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                                    Sprite.explosion_vertical2, explosionCountDown)
                            .getFxImage();
                    break;
                case "left_most":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_left_last,
                                    Sprite.explosion_horizontal_left_last1,
                                    Sprite.explosion_horizontal_left_last2, explosionCountDown)
                            .getFxImage();
                    break;
                case "down_most":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_down_last,
                                    Sprite.explosion_vertical_down_last1,
                                    Sprite.explosion_vertical_down_last2, explosionCountDown)
                            .getFxImage();
                    break;
                case "right_most":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_horizontal_right_last,
                                    Sprite.explosion_horizontal_right_last1,
                                    Sprite.explosion_horizontal_right_last2, explosionCountDown)
                            .getFxImage();
                    break;
                case "top_most":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.explosion_vertical_top_last,
                                    Sprite.explosion_vertical_top_last1,
                                    Sprite.explosion_vertical_top_last2, explosionCountDown)
                            .getFxImage();
                    break;
                case "center":
                    this.img = Sprite
                            .bombExplodeSprite(Sprite.bomb_exploded,
                                    Sprite.bomb_exploded1,
                                    Sprite.bomb_exploded2, explosionCountDown)
                            .getFxImage();
                    break;
            }

            explosionCountDown--;
        }
    }

    @Override
    public void update() {
        explodingImg();
    }

}
