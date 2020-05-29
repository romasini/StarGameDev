package ru.romasini.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.romasini.base.BonusType;
import ru.romasini.base.Ship;
import ru.romasini.math.Rect;
import ru.romasini.pool.BulletPool;
import ru.romasini.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float SIZE = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float VELOCITY = 0.5f;
    private static final int INVALID_POINTER = -1;
    private static final int HEALTH_POINTS = 1;
    private static final float BULLET_HEIGHT = 0.015f;
    private static final int BULLET_DAMAGE = 1;

    private int leftPointer, rightPointer;
    private boolean pressedLeft, pressedRight;
    private BonusType bonusType;
    private float bonusTimer;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.velStart.set(VELOCITY, 0);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletVelocity = new Vector2(0, VELOCITY);
        this.bulletHeight = BULLET_HEIGHT;
        this.bulletDamage = BULLET_DAMAGE;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.mp3"));
        this.reloadInterval = 0.25f;
        startNewGame();
    }

    public void startNewGame(){
        this.healthPoints = HEALTH_POINTS;

        this.leftPointer = INVALID_POINTER;
        this.rightPointer = INVALID_POINTER;
        this.pressedLeft = false;
        this.pressedRight = false;
        stop();

        this.reloadTimer = this.reloadInterval;
        this.pos.x = 0;

        flushDestroy();

        this.frame = 0;

        setBonusType(BonusType.NORMAL);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        autoShoot(delta);

        if(getLeft() < worldBounds.getLeft()){
            stop();
            setLeft(worldBounds.getLeft());
        }
        if(getRight() > worldBounds.getRight()){
            stop();
            setRight(worldBounds.getRight());
        }

        if(bonusType != BonusType.NORMAL){
            bonusTimer += delta;
            if (bonusTimer >= bonusType.getDuration()){
                setBonusType(BonusType.NORMAL);
            }
        }

    }

    @Override
    public void autoShoot(float delta) {
        if(bonusType != BonusType.MANY_BULLETS) {
            super.autoShoot(delta);
        }else {
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval){
                reloadTimer = 0f;
                manyShoot();
            }
        }
    }

    private void manyShoot(){
        bulletVelocity.set(0, VELOCITY);
        bulletVelocity.rotate(30);
        for (int i = 0; i < 5; i++) {
            Bullet bullet = bulletPool.obtain();
            bullet.set(this,
                    bulletRegion,
                    bulletPos,
                    bulletVelocity,
                    bulletHeight,
                    worldBounds,
                    bulletDamage
            );
            bulletVelocity.rotate(-15);
        }
        shootSound.play();
    }

    @Override
    public void damage(int damage) {
        if(bonusType != BonusType.GHOST) {
            super.damage(damage);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        setBatchColor(batch);
        super.draw(batch);
        setDefaultBatchColor(batch);
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
        this.bonusTimer = 0f;

        if(bonusType == BonusType.BOOST_DAMAGE){
            this.bulletDamage = BULLET_DAMAGE * bonusType.getValue();
            this.bulletHeight = BULLET_HEIGHT * bonusType.getValue();
        }else {
            this.bulletDamage = BULLET_DAMAGE;
            this.bulletHeight = BULLET_HEIGHT;
        }

        if(bonusType == BonusType.HEALTH){
            this.healthPoints += bonusType.getValue();
            if(this.healthPoints> HEALTH_POINTS){
                this.healthPoints = HEALTH_POINTS;
            }
        }

        if(bonusType != BonusType.MANY_BULLETS){
            this.bulletVelocity.set(0, VELOCITY);
        }

    }

    public BonusType getBonusType() {
        return bonusType;
    }

    private void setBatchColor(SpriteBatch batch){
        switch (bonusType){
            case MANY_BULLETS:
            case BOOST_DAMAGE:
            case ALL_DESTROY:
            case HEALTH:{
                batch.setColor(bonusType.getColor());
                break;
            }
            case GHOST:{
                batch.setColor(bonusType.getColor().r, bonusType.getColor().g, bonusType.getColor().b, 0.5f);
                break;
            }
            default:setDefaultBatchColor(batch);
        }
    }

    private void setDefaultBatchColor(SpriteBatch batch){
        batch.setColor(1f,1f, 1f, 1f );
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    public boolean isBulletCollision(Bullet bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft()  > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    public boolean isBonusCollision(Bonus bonus){
        return !(bonus.getRight() < getLeft()
                || bonus.getLeft()  > getRight()
                || bonus.getBottom() > pos.y
                || bonus.getTop() < getBottom()
        );
    }

    public void dispose(){
        shootSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
            if(rightPointer == INVALID_POINTER){
                moveLeft();
            }

        }else{
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            if(leftPointer == INVALID_POINTER){
                moveRight();
            }

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
                if(!pressedRight) {
                    moveLeft();
                }
            break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                if(!pressedLeft) {
                    moveRight();
                }
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

}
