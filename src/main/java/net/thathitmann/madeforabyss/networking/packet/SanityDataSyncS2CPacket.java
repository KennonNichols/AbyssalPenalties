package net.thathitmann.madeforabyss.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.thathitmann.madeforabyss.client.ClientSanityData;

import java.util.function.Supplier;

public class SanityDataSyncS2CPacket {
    private final int sanity;

    public SanityDataSyncS2CPacket(int sanity) {
        this.sanity = sanity;
    }
    public SanityDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.sanity = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(sanity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientSanityData.set(sanity);
        });
        return true;
    }




}
