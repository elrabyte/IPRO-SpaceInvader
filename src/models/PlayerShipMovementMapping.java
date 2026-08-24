
package models;

import interfaces.IPlayerEntity;
import interfaces.IProjectile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Set;

public class PlayerShipMovementMapping {

    public int MoveUp = KeyEvent.VK_W;
    public int MoveDown = KeyEvent.VK_S;
    public int MoveLeft = KeyEvent.VK_A;
    public int MoveRight = KeyEvent.VK_D;
    public int Shoot = KeyEvent.VK_SPACE;

    public PlayerShipMovementMapping(int moveUp, int moveDown, int moveLeft, int moveRight, int shoot) {
        this.MoveUp = moveUp;
        this.MoveDown = moveDown;
        this.MoveLeft = moveLeft;
        this.MoveRight = moveRight;
        this.Shoot = shoot;
    }
}
