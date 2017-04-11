package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedQuad implements net.minecraftforge.client.model.pipeline.IVertexProducer
{
    /**
     * Joined 4 vertex records, each has 7 fields (x, y, z, shadeColor, u, v, <unused>), see
     * FaceBakery.storeVertexData()
     */
    @Override public void pipe(net.minecraftforge.client.model.pipeline.IVertexConsumer consumer) { net.minecraftforge.client.model.pipeline.LightUtil.putBakedQuad(consumer, this); }
    protected final int[] vertexData;
    protected final int tintIndex;
    protected final EnumFacing face;
    private static final String __OBFID = "CL_00002512";

    public BakedQuad(int[] p_i46232_1_, int p_i46232_2_, EnumFacing p_i46232_3_)
    {
        this.vertexData = p_i46232_1_;
        this.tintIndex = p_i46232_2_;
        this.face = p_i46232_3_;
    }

    public int[] getVertexData()
    {
        return this.vertexData;
    }

    public boolean hasTintIndex()
    {
        return this.tintIndex != -1;
    }

    public int getTintIndex()
    {
        return this.tintIndex;
    }

    public EnumFacing getFace()
    {
        return this.face;
    }
}