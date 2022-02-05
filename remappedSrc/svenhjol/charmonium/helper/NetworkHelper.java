package svenhjol.charmonium.helper;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.PacketByteBuf;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @version 1.0.0-charm
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class NetworkHelper {
    @Nullable
    public static PacketByteBuf encodeNbt(NbtCompound nbt) {
        PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            NbtIo.writeCompressed(nbt, out);
            String serialized = Base64.getEncoder().encodeToString(out.toByteArray());
            buffer.writeString(serialized);
            return buffer;
        } catch (IOException e) {
            LogHelper.warn(NetworkHelper.class, "Failed to compress nbt");
        }

        return null;
    }

    @Nullable
    public static NbtCompound decodeNbt(PacketByteBuf data) {
        try {
            byte[] byteData = Base64.getDecoder().decode(data.readString());
            return NbtIo.readCompressed(new ByteArrayInputStream(byteData));
        } catch (IOException e) {
            LogHelper.warn(NetworkHelper.class, "Failed to decompress nbt");
        }

        return null;
    }
}
