package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Item extends Entity {
    private boolean isCollision = false;

    public Item(int x, int y, Image img) {
        super(x, y, img);
        rec = new Rectangle(x, y, 0.98, 0.98);
    }


    //Collision Bomber

    public boolean collision(Entity entities) {
        if (this.rec.intersects(entities.rec.getLayoutBounds())) {
            return true;
        }
        return false;
    }

    @Override
    public void update() {

    }
}
