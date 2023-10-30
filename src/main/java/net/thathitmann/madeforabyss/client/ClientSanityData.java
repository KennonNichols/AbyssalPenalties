package net.thathitmann.madeforabyss.client;

public class ClientSanityData {
    private static int playerSanity;

    public static void set(int playerSanity) {
        ClientSanityData.playerSanity = playerSanity;
    }

    public static int getPlayerSanity() {
        return playerSanity;
    }
}
