package com.example.dungeonescape.game.collectable;

public class CollectableFactory {

    public Collectable getCollectable(String collectableType, int x, int y) {

        if (collectableType == null) {
            return null;
        }

        if (collectableType.equalsIgnoreCase("coin")) {
            return new Coin(x, y);

        } else if (collectableType.equalsIgnoreCase("gem")) {
            return new Gem(x, y);

        } else if (collectableType.equalsIgnoreCase("potion")) {
            return new Potion(x, y);
        }

        return null;
    }
}
