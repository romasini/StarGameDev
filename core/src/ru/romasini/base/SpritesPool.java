package ru.romasini.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool <T extends Sprite>{
    private final List<T> activeObjects = new ArrayList<T>();
    private final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain(){//получать
        T object;
        if(freeObjects.isEmpty()){
            object = newObject();
        }else{
            object = freeObjects.remove(0);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta){
        for (T object : activeObjects){
            if(object.isDestroyed()){
                continue;
            }
            object.update(delta);
        }
    }

    public void dispose(){
        activeObjects.clear();
        freeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void drawActiveSprites(SpriteBatch batch){
        for (T object : activeObjects){
            if(object.isDestroyed()){
                continue;
            }
            object.draw(batch);
        }
    }

    private void free(T object){
        object.flushDestroy();
        if(activeObjects.remove(object))
            freeObjects.add(object);
    }

    public void freeAllDestroyed(){
        for (int i = 0; i < activeObjects.size(); i++){
            T object = activeObjects.get(i);
            if(object.isDestroyed()){
                free(object);
                i--;
            }
        }
    }

    public void freeAllActiveObjects(){
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

}
