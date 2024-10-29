package com.brandon3055.draconicevolution.client.render.tile;

import com.brandon3055.brandonscore.client.utills.ShaderHelper;
import com.brandon3055.draconicevolution.client.handler.ResourceHandler;
import com.brandon3055.draconicevolution.common.lib.References;
import com.brandon3055.draconicevolution.common.tileentities.multiblocktiles.reactor.TileReactorCore;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;


/**
 * Created by Brandon on 16/6/2015.
 */
public class RenderTileReactorCore extends TileEntitySpecialRenderer {
    public static IModelCustom reactorModelNoShader;
    public static IModelCustom reactorModelShader;

    static int coreID = -1,coreNoShaderID = -1, coreProgram = -1, shieldProgram =-1;

    public RenderTileReactorCore() {
        reactorModelNoShader = AdvancedModelLoader.loadModel(new ResourceLocation(References.MODID.toLowerCase(), "models/reactor_core_no_shader.obj"));
        reactorModelShader = AdvancedModelLoader.loadModel(new ResourceLocation(References.MODID.toLowerCase(), "models/reactor_core.obj"));
        coreID=GL11.glGenLists(1);
        GL11.glNewList(coreID, GL11.GL_COMPILE);
        reactorModelShader.renderAll();
        GL11.glEndList();
        coreNoShaderID=GL11.glGenLists(1);
        GL11.glNewList(coreNoShaderID, GL11.GL_COMPILE);
        reactorModelNoShader.renderAll();
        GL11.glEndList();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        TileReactorCore tile = (TileReactorCore) tileEntity;
//		tile.renderList.clear();

        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y+0.5, z+0.5 );

        renderReactorCore(tile, partialTick);
//		for (MultiblockHelper.TileOffset offset : tile.stabilizerLocations){
//			if (!(offset.getTileEntity(tileEntity) instanceof TileReactorStabilizer)) continue;
//			GL11.glPushMatrix();
//
//			TileReactorStabilizer stabilizer = (TileReactorStabilizer)offset.getTileEntity(tileEntity);
//			GL11.glTranslated(-offset.offsetX, -offset.offsetY, -offset.offsetZ);
//			//RenderTileReactorStabilizer.renderCore(stabilizer, partialTick);
//			//RenderTileReactorStabilizer.renderEffects(stabilizer, partialTick);
//
//			GL11.glPopMatrix();
//		}

        GL11.glPopMatrix();
    }


    public void renderReactorCore(TileReactorCore tile, float partialTick) {
        float rotation = (tile.renderRotation * 0.2F) + (partialTick * (tile.renderSpeed * 0.2F));
        //Pre Render
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
        float lastBrightnessY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 200F, 200F);

        float timer = tile.tick /20F + 100;
        float size = (float) Math.max(0.5F, tile.getCoreDiameter());
        float t = (float) (tile.reactionTemperature / tile.maxReactTemperature);
        float intensity = t <= 0.2 ? (float) map(t, 0, 0.2, 0, 0.5) : t <= 0.75 ? (float) map(t, 0.2, 0.75, 0.5, 1.2) : (float) map(t, 0.75, 1, 1.2, 1.5);
        renderCore(timer, intensity, rotation, size);

        float shieldPower = (float) (tile.maxFieldCharge > 0 ? tile.fieldCharge / tile.maxFieldCharge : 0);
        renderShield(timer, shieldPower, rotation, size * 1.03F);

        //Post Render
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        GL11.glPopMatrix();
    }
    void renderCore(float timer, float intensity, float rotation, double size){

        ResourceHandler.bindResource("textures/models/reactorCore.png");
        GL11.glColor4d(1, 1, 1, 1);
        GL11.glRotatef(rotation, 0.5F, 1F, 0.5F);
        if(!useShader)size/=2;//IDK why
        GL11.glScaled(size, size, size);

        if (useShader) {
            if (coreProgram == -1) {
                coreProgram = ShaderHelper.createProgram("/assets/draconicevolution/shader/reactorCore.vert", "/assets/draconicevolution/shader/reactorCore.frag");
            }
            final float animation = timer;
            final float intensity1= intensity;
            ShaderHelper.useShader(coreProgram, new ShaderHelper.ShaderCallback() {
                public void call(int shader) {
                    ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(shader, "time"), animation);
                    ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(shader, "intensity"), intensity1);
                }
            });
        GL11.glCallList(coreID);
        ShaderHelper.releaseShader();
        }else GL11.glCallList(coreNoShaderID);
    }
    static boolean useShader=true;
    void renderShield(float timer, float shieldPower, float rotation, double size){


        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
        ResourceHandler.bindResource("textures/models/reactorShieldPlate.png");
        GL11.glRotatef(2343, 0.5F, 1F, 0.5F);
        GL11.glRotatef(-rotation * 2, 0.5F, 1F, 0.5F);
        GL11.glScaled(1.03F, 1.03F, 1.03F);

        if (useShader) {
            if (shieldProgram == -1) {
                shieldProgram = ShaderHelper.createProgram(null, "/assets/draconicevolution/shader/reactorShield.frag");
            }
            final float fTimer = timer;
            final float fShieldPower= shieldPower;
            ShaderHelper.useShader(shieldProgram, new ShaderHelper.ShaderCallback() {
                public void call(int shader) {
                    ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(shader, "time"), fTimer);
                    ARBShaderObjects.glUniform1fARB(ARBShaderObjects.glGetUniformLocationARB(shader, "intensity"), fShieldPower);
                }
            });
            GL11.glCallList(coreID);
            ShaderHelper.releaseShader();
        }else GL11.glCallList(coreNoShaderID);

        GL11.glDisable(GL11.GL_BLEND);
    }
    public static double map(double valueIn, double inMin, double inMax, double outMin, double outMax) {
        return (valueIn - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
}
