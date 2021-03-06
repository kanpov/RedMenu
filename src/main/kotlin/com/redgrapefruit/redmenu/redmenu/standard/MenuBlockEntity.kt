package com.redgrapefruit.redmenu.redmenu.standard

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.text.Text
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

/**
 * A menu [BlockEntity] acting as a [DefaultedInventory] implementation and managing an inventory.
 *
 * This class can be easily extended to add more NBT-serialized properties, custom functionality, ticking etc.
 */
abstract class MenuBlockEntity protected constructor(type: BlockEntityType<*>, pos: BlockPos, state: BlockState, size: Int)
    : BlockEntity(type, pos, state), DefaultedInventory, NamedScreenHandlerFactory {

    // Embedded inventory represented through a DefaultedList
    protected val inventory: DefaultedList<ItemStack> = DefaultedList.ofSize(size, ItemStack.EMPTY)

    override fun getDisplayName(): Text = Text.translatable(cachedState.block.translationKey)

    override fun markDirty() = Unit

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        Inventories.readNbt(nbt, inventory)
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        Inventories.writeNbt(nbt, inventory)
    }
}
