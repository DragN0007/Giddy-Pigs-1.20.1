package com.dragn0007.giddypigs.event;

import com.dragn0007.giddypigs.GiddyGuineaPigs;
import com.dragn0007.giddypigs.blocks.GGPBlocks;
import com.dragn0007.giddypigs.blocks.pixel_placement.util.PixelPlacerEntityRenderer;
import com.dragn0007.giddypigs.entities.GuineaPig;
import com.dragn0007.giddypigs.entities.GuineaPigRender;
import com.dragn0007.giddypigs.entities.util.EntityTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = GiddyGuineaPigs.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class GiddyGuineaPigsEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityTypes.GUINEA_PIG_ENTITY.get(), GuineaPig.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypes.GUINEA_PIG_ENTITY.get(), GuineaPigRender::new);

        ResourceLocation resourceLocation = new ResourceLocation(GiddyGuineaPigs.MODID, "null");
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.PIXEL_PLACER_CONTAINER.get(), RenderType.cutout());

        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.WATER_BOTTLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.FOOD_DISH_BLACK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.FOOD_DISH_BROWN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.FOOD_DISH_WHITE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.ENCLOSURE_PANE_CORNER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GGPBlocks.ENCLOSURE_PANE.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void entityRendererEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(GGPBlocks.PIXEL_PLACER_ENTITY.get(), PixelPlacerEntityRenderer::new);
    }
}