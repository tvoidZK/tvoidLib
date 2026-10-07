package net.tvoid.lib.helper;

public class SmeltingTicks {
    public static int furnaceItems(double items) {
        return (int) Math.round(items * 200) + 1;
    }
    public static int upgradedFurnaceItems(double items) {
        return (int) Math.round(items * 100) + 1;
    }
}