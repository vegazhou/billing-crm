package com.kt.spalgorithm.model.payload;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public class StoragePayload {
    private boolean storageOverage;
    private int storageCapacity;

    public StoragePayload deepClone() {
        StoragePayload clone = new StoragePayload();
        clone.storageOverage = storageOverage;
        clone.storageCapacity = storageCapacity;
        return clone;
    }

    public boolean isStorageOverage() {
        return storageOverage;
    }

    public void setStorageOverage(boolean storageOverage) {
        this.storageOverage = storageOverage;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }
}
