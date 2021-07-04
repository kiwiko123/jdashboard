package com.kiwiko.library.dataStructures.collections.sets.lru;

public class CustomizableActionLeastRecentlyUsedSet<E> extends LeastRecentlyUsedSet<E> {
    private LeastRecentlyUsedSetActions<E> actions;

    public void setActions(LeastRecentlyUsedSetActions<E> actions) {
        this.actions = actions;
    }

    @Override
    public boolean add(E e) {
        boolean result = super.add(e);
        if (actions.getOnAdd() != null) {
            actions.getOnAdd().accept(e);
        }
        return result;
    }

    @Override
    public boolean remove(Object o) {
        @SuppressWarnings("unchecked")
        E e = (E) o;
        boolean result = super.remove(o);
        if (actions.getOnRemove() != null) {
            actions.getOnRemove().accept(e);
        }
        return result;
    }

    @Override
    protected E evictLeastRecentlyUsedValue() {
        E evictedValue = super.evictLeastRecentlyUsedValue();
        if (actions.getOnEvict() != null) {
            actions.getOnEvict().accept(evictedValue);
        }
        return evictedValue;
    }
}
