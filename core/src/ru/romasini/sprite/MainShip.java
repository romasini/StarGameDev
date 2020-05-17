package ru.romasini.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.Sprite;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float SIZE = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float VELOCITY = 0.5f;
    private static final int INVALID_POINTER = -1;
    private static final float SHOOT_INTERVAL = 0.2f;
    private final Vector2 vel, velStart;
    private int leftPointer, rightPointer;
    private boolean pressedLeft, pressedRight;
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletVelocity;
    private Sound shootSound;
    private float shootTimer;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound shootSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.velStart = new Vector2(VELOCITY, 0);
        this.vel = new Vector2();
        this.leftPointer = INVALID_POINTER;
        this.rightPointer = INVALID_POINTER;
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletVelocity = new Vector2(0, VELOCITY);
        this.shootSound = shootSound;
        this.shootTimer = 0f;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(vel, delta);
        if(getLeft() < worldBounds.getLeft()){
            stop();
            setLeft(worldBounds.getLeft());
        }
        if(getRight() > worldBounds.getRight()){
            stop();
            setRight(worldBounds.getRight());
        }

        shootTimer += delta;
        if (shootTimer > SHOOT_INTERVAL){
            shootTimer = 0f;
            shoot();
        }

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
        this.worldBounds = worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            moveLeft();

        }else{
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            }else {
                stop();
            }
        }else if(pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            }else {
                stop();
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
            break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
            break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight)
                    moveRight();
                else
                    stop();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft)
                    moveLeft();
                else
                    stop();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }

    private void moveRight(){
        vel.set(velStart);
    }

    private void moveLeft(){
        vel.set(velStart).rotate(180);
    }

    private void stop(){
        vel.setZero();
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                bulletVelocity,
                0.01f,
                worldBounds,
                1
                );
        shootSound.play();
    }
}
