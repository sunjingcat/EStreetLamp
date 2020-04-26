package com.zz.lib.commonlib.widget;

import com.contrarywind.adapter.WheelAdapter;

public class ArrayWheelAdapter<T> implements WheelAdapter {
    private T items[];

    public ArrayWheelAdapter(T items[]) {
        this.items = items;
    }

    @Override
    public int getItemsCount() {
        return items.length;
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < items.length) {
            T item = items[index];
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }


}
