package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.shape.Rectangle;

public abstract class Entity {

    // x va y la hai toa do don vi
    protected int x;
    protected int y;

    public Rectangle rec;

    // ten hinh anh tuong ung
    protected Image img;

    //Khởi tạo đối tượng
    public Entity(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    // Chuyển từ tọa độ đơn vị sang tọa độ trong canvas (classic.png)
    //Tọa độ X tính từ góc trái trên trong Canvas
    //Tọa độ Y tính từ góc trái trên trong Canvas
    public void render(GraphicsContext gc) {
        if (this.x >= 0 && this.y >= 0) {
            gc.drawImage(img, x * Sprite.SCALED_SIZE, y * Sprite.SCALED_SIZE);
        }
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public boolean moveLeft() {
        return true;
    }

    public boolean moveRight() {
        return true;
    }

    public boolean moveUp() {
        return true;
    }

    public boolean moveDown() {
        return true;
    }

    // x, y is upper left point
    public void setLocation(int x, int y) {
        rec.setX(x);
        rec.setY(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void update();
}

