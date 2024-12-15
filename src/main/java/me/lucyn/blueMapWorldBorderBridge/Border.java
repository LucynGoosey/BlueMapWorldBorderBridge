package me.lucyn.blueMapWorldBorderBridge;

public class Border {

    public int x1;
    public int x2;
    public int z1;
    public int z2;

    public Border(int borderSize, int centerX, int centerZ) {
        setBorder(borderSize, centerX, centerZ);
    }

    public void setBorder(int borderSize, int centerX, int centerZ) {
        x1 = centerX - (borderSize / 2);
        x2 = centerX + (borderSize / 2);
        z1 = centerZ - (borderSize / 2);
        z2 = centerZ + (borderSize / 2);
    }




}