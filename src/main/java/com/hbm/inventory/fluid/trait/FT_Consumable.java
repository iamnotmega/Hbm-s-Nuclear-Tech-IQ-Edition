package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.i18n.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class FT_Consumable extends FluidTrait {

	public float foodLevel;
	public float saturation;
	public int consumption = 1;
	public boolean clearsEffects = false;
	private List<ConsumableEffect> effects = new ArrayList();
	private List<String> specialEffects = new ArrayList();

	public FT_Consumable setFood(float foodLevel, float saturation) {
		this.foodLevel = foodLevel;
		this.saturation = saturation;
		return this;
	}

	public FT_Consumable addEffect(int potionId, int amplifier) {
		effects.add(new ConsumableEffect(potionId, amplifier));
		return this;
	}

	public FT_Consumable addSpecialEffect(String langKey) {
		specialEffects.add(langKey);
		return this;
	}

	public FT_Consumable setConsumption(int rate) {
		this.consumption = rate;
		return this;
	}

	public FT_Consumable setClearsEffects(boolean clearsEffects) {
		this.clearsEffects = clearsEffects;
		return this;
	}

	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey("hbmfluid.trait.consumable") + "]");

		info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey("hbmfluid.trait.food") + ": " + foodLevel + " shk/mB");
		info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey("hbmfluid.trait.saturation") + ": " + saturation + " shk/mB");
		info.add(EnumChatFormatting.YELLOW + "- " + I18nUtil.resolveKey("hbmfluid.trait.consumption", consumption));

		if(clearsEffects) {
			info.add(EnumChatFormatting.YELLOW + "   - " + I18nUtil.resolveKey("hbmfluid.trait.clearsEffects"));
		}

		for(ConsumableEffect effect : effects) {
			String name = Potion.potionTypes[effect.potionId] != null ? StatCollector.translateToLocal(Potion.potionTypes[effect.potionId].getName()) : "Unknown";
			String amp = effect.amplifier > 0 ? " " + StatCollector.translateToLocal("potion.potency." + effect.amplifier).trim() : "";
			info.add(EnumChatFormatting.YELLOW + "   - " + name + amp);
		}

		for(String effect : specialEffects) {
			info.add(EnumChatFormatting.YELLOW + "   - " + I18nUtil.resolveKey(effect));
		}
	}

	public void apply(EntityLivingBase entity, double intensity) {
		if(entity == null || !entity.isEntityAlive()) return;

		if(foodLevel > 0 && entity instanceof EntityPlayer) {
			int foodToAdd = (int) (foodLevel * intensity / 20.0);
			if(foodToAdd > 0) {
				((EntityPlayer) entity).getFoodStats().addStats(foodToAdd, saturation * (float) intensity / 20.0F);
			}
		}

		applyEffects(entity);
	}

	public void applyEffects(EntityLivingBase entity) {
		if(clearsEffects) {
			entity.clearActivePotions();
			return;
		}

		if(foodLevel >= 1.0F) {
			entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 2));
		} else if(foodLevel >= 0.2F) {
			entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 1));
		} else if(foodLevel >= 0.1F) {
			entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, 0));
		}

		for(ConsumableEffect effect : effects) {
			entity.addPotionEffect(new PotionEffect(effect.potionId, 40, effect.amplifier));
		}
	}

	public static class ConsumableEffect {
		public int potionId;
		public int amplifier;

		public ConsumableEffect(int potionId, int amplifier) {
			this.potionId = potionId;
			this.amplifier = amplifier;
		}
	}

	@Override public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("foodLevel").value(foodLevel);
		writer.name("saturation").value(saturation);
		writer.name("consumption").value(consumption);
		writer.name("clearsEffects").value(clearsEffects);
		writer.name("effects").beginArray();
		for(ConsumableEffect effect : effects) {
			writer.beginArray();
			writer.value(effect.potionId).value(effect.amplifier);
			writer.endArray();
		}
		writer.endArray();
		writer.name("specialEffects").beginArray();
		for(String effect : specialEffects) {
			writer.value(effect);
		}
		writer.endArray();
	}

	@Override public void deserializeJSON(JsonObject obj) {
		this.foodLevel = obj.get("foodLevel").getAsFloat();
		this.saturation = obj.get("saturation").getAsFloat();
		if(obj.has("consumption")) this.consumption = obj.get("consumption").getAsInt();
		if(obj.has("clearsEffects")) this.clearsEffects = obj.get("clearsEffects").getAsBoolean();
		JsonArray array = obj.get("effects").getAsJsonArray();
		for(int i = 0; i < array.size(); i++) {
			JsonArray entry = array.get(i).getAsJsonArray();
			ConsumableEffect effect = new ConsumableEffect(entry.get(0).getAsInt(), entry.get(1).getAsInt());
			this.effects.add(effect);
		}
		if(obj.has("specialEffects")) {
			JsonArray special = obj.get("specialEffects").getAsJsonArray();
			for(int i = 0; i < special.size(); i++) {
				this.specialEffects.add(special.get(i).getAsString());
			}
		}
	}
}
