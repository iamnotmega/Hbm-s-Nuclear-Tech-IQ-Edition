package com.hbm.inventory.fluid.trait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.util.i18n.I18nUtil;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class FT_Drug extends FluidTrait {

	public int consumption = 1;
	public int duration;
	private List<FT_Consumable.ConsumableEffect> effects = new ArrayList();
	private List<String> specialEffects = new ArrayList();

	public FT_Drug setConsumption(int rate) {
		this.consumption = rate;
		return this;
	}

	public FT_Drug setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public FT_Drug addEffect(int potionId, int amplifier) {
		effects.add(new FT_Consumable.ConsumableEffect(potionId, amplifier));
		return this;
	}

	public FT_Drug addSpecialEffect(String langKey) {
		specialEffects.add(langKey);
		return this;
	}

	public boolean hasSpecialEffect(String key) {
		return specialEffects.contains(key);
	}

	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.AQUA + "[" + I18nUtil.resolveKey("hbmfluid.trait.drug") + "]");

		if(effects.isEmpty() && specialEffects.isEmpty()) {
			info.add(EnumChatFormatting.YELLOW + "   - " + I18nUtil.resolveKey("hbmfluid.trait.noEffects"));
			return;
		}

		for(FT_Consumable.ConsumableEffect effect : effects) {
			String name = Potion.potionTypes[effect.potionId] != null ? StatCollector.translateToLocal(Potion.potionTypes[effect.potionId].getName()) : "Unknown";
			String amp = effect.amplifier > 0 ? " " + StatCollector.translateToLocal("potion.potency." + effect.amplifier).trim() : "";
			info.add(EnumChatFormatting.YELLOW + "   - " + name + amp);
		}

		for(String effect : specialEffects) {
			info.add(EnumChatFormatting.YELLOW + "   - " + I18nUtil.resolveKey(effect));
		}

		info.add(EnumChatFormatting.YELLOW + "   " + I18nUtil.resolveKey("hbmfluid.trait.consumption", consumption));

		if(duration > 0) {
			info.add(EnumChatFormatting.YELLOW + "   " + I18nUtil.resolveKey("desc.item.syringe.whenInjected", duration));
		} else {
			info.add(EnumChatFormatting.YELLOW + "   " + I18nUtil.resolveKey("desc.item.syringe.instant"));
		}
	}

	public void apply(EntityLivingBase entity, double intensity) {
		if(entity == null || !entity.isEntityAlive()) return;

		for(String special : specialEffects) {
			if("clear_effects".equals(special)) {
				entity.clearActivePotions();
			}
			if("clear_bad_effects".equals(special)) {
				List<Integer> toRemove = new ArrayList();
				for(Object o : entity.getActivePotionEffects()) {
					PotionEffect pe = (PotionEffect) o;
					if(HbmPotion.getIsBadEffect(Potion.potionTypes[pe.getPotionID()])) {
						toRemove.add(pe.getPotionID());
					}
				}
				for(Integer id : toRemove) {
					entity.removePotionEffect(id);
				}
			}
		}

		for(FT_Consumable.ConsumableEffect effect : effects) {
			int ticks = this.duration > 0 ? (int)(20 * this.duration * intensity) : 40;
			int scaledAmp = (int)((effect.amplifier + 1) * intensity - 1);
			int effTicks = (effect.potionId == Potion.harm.id || effect.potionId == Potion.heal.id) ? 1 : Math.max(ticks, 20);
			entity.addPotionEffect(new PotionEffect(effect.potionId, effTicks, Math.max(scaledAmp, 0)));
		}
	}

	@Override public void serializeJSON(JsonWriter writer) throws IOException {
		writer.name("consumption").value(consumption);
		writer.name("duration").value(duration);
		writer.name("effects").beginArray();
		for(FT_Consumable.ConsumableEffect effect : effects) {
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
		if(obj.has("consumption")) this.consumption = obj.get("consumption").getAsInt();
		if(obj.has("duration")) this.duration = obj.get("duration").getAsInt();
		JsonArray array = obj.get("effects").getAsJsonArray();
		for(int i = 0; i < array.size(); i++) {
			JsonArray entry = array.get(i).getAsJsonArray();
			FT_Consumable.ConsumableEffect effect = new FT_Consumable.ConsumableEffect(entry.get(0).getAsInt(), entry.get(1).getAsInt());
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
