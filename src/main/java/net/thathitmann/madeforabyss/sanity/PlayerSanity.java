package net.thathitmann.madeforabyss.sanity;

import net.minecraft.nbt.CompoundTag;

public class PlayerSanity {
    private SanityMod sanityMod = SanityMod.NEUTRAL;
    private int sanity = 1000;
    private static final int MAX_SANITY = 1000;


    public static int getMaxSanity() {return MAX_SANITY;}
    public int getSanity() {return sanity;}
    public void addSanity(int addedSanity) {
        sanity = Math.min(sanity + addedSanity, MAX_SANITY);
        sanityMod = SanityMod.GAINING;
    }
    public void removeSanity(int removedSanity) {
        sanity = Math.max(sanity - removedSanity, 0);
        sanityMod = SanityMod.LOSING;
    }
    public void setSanityModToNeutral() {
        sanityMod = SanityMod.NEUTRAL;
    }

    public SanityMod getSanityMod() {return sanityMod;}

    public void copyFrom(PlayerSanity source) {this.sanity = source.sanity;}

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("madeforabyss:sanity", sanity);
    }

    public void loadNBTData(CompoundTag tag) {
        sanity = tag.getInt("madeforabyss:sanity");
    }

}
