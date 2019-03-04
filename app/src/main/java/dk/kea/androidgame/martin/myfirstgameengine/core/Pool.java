package dk.kea.androidgame.martin.myfirstgameengine.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Pool<T>
{
    private List<T> items = new ArrayList<>();

    /**
     * Adds a new item to the list of items.
     *
     * @return T item type
     */
    protected abstract T newItem();

    public T obtains()
    {
        int size = items.size();
        if (size == 0)
        {
            return newItem();
        }
        return items.remove(size - 1);
    }

    public void free(T item)
    {
        items.add(item);
    }
}
