package com.fadegor05.cubeapply

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction.Lifecycle
import net.fabricmc.fabric.impl.event.lifecycle.LifecycleEventsImpl
import net.fabricmc.fabric.mixin.resource.loader.LifecycledResourceManagerImplMixin
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.resource.LifecycledResourceManager
import net.minecraft.resource.LifecycledResourceManagerImpl
import net.minecraft.resource.ResourcePackManager
import net.minecraft.resource.ResourcePackProfile
import java.io.File
import java.nio.file.Path

class CubeApply : ModInitializer {

    val cubePath: Path = FabricLoader.getInstance().configDir.resolve("cubeapplyinit")

    override fun onInitialize() {
        println("CubeApply mod initialized!")
        ClientLifecycleEvents.CLIENT_STARTED.register { client: MinecraftClient ->
            val file = File(cubePath.toString())
            if (!file.exists()) {
                val resourcePackManager = client.resourcePackManager
                activateResourcePacks(resourcePackManager)
                client.options.refreshResourcePacks(resourcePackManager)
                file.createNewFile()
            }
        }
    }

    private fun activateResourcePacks(resourcePackManager: ResourcePackManager) {
        val exclude = listOf("high_contrast", "programmer_art", "black_icons", "classic_icons", "supplementaries:darker_ropes", "create:legacy_copper", "supplementaries:darker_ropes")
        for (profile: String in resourcePackManager.names) {
            if (!resourcePackManager.enabledNames.contains(profile) && !exclude.contains(profile)) {
                resourcePackManager.enable(profile)
            }
        }
    }
}
