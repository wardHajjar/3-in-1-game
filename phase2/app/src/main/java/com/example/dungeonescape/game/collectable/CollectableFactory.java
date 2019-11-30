package com.example.dungeonescape.game.collectable;

public class CollectableFactory {

    public Collectable getCollectable(String collectableType, int x, int y, int size) {

        if (collectableType == null) {
            return null;
        }

        if (collectableType.equalsIgnoreCase("coin")) {
            return new Coin(x, y, size);

        } else if (collectableType.equalsIgnoreCase("gem")) {
            return new Gem(x, y, size);

        } else if (collectableType.equalsIgnoreCase("potion")) {
            return new Potion(x, y, size);
        }

        return null;
    }
}
