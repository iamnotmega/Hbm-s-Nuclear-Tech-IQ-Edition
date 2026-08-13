package com.hbm.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class Flag1984 {
	private static final HashSet<String> players = new HashSet<>();

	private static boolean flag = true;

	public static boolean is1984() {
		return flag;
	}

	public static void detectFreedom() {
		if (new Random().nextInt(100) == 0) {
			flag = false;
		} else {
			String user = Minecraft.getMinecraft().getSession().getUsername();
			if (players.contains(user.toLowerCase(Locale.ENGLISH))) flag = false;
		}
	}

	static {
		// The 'fectum
		players.add("siepert");
		players.add("tam69420");
		players.add("yanosiq");
		players.add("iristhepianist");
	}
}
