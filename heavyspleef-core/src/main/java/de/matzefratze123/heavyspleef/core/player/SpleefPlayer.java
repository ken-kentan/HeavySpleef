/*
 * This file is part of HeavySpleef.
 * Copyright (c) 2014-2015 matzefratze123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.matzefratze123.heavyspleef.core.player;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import de.matzefratze123.heavyspleef.core.HeavySpleef;
import de.matzefratze123.heavyspleef.core.Permissions;

public class SpleefPlayer {
	
	/* Only keep a weak reference to avoid memory leaks.
	 * Reference should be actually hold by Bukkit itself */
	private WeakReference<Player> bukkitPlayerRef;
	private String name;
	private boolean online;
	private final HeavySpleef heavySpleef;
	
	private Map<Object, PlayerStateHolder> playerStates;
	
	public SpleefPlayer(Player bukkitPlayer, HeavySpleef heavySpleef) {
		this.bukkitPlayerRef = new WeakReference<Player>(bukkitPlayer);
		this.online = bukkitPlayer.isOnline();
		this.playerStates = Maps.newHashMap();
		this.name = bukkitPlayer.getName();
		this.heavySpleef = heavySpleef;
	}
	
	public Player getBukkitPlayer() {
		return bukkitPlayerRef.get();
	}
	
	public boolean isOnline() {
		return online && bukkitPlayerRef.get() != null;
	}
	
	protected void setOnline(boolean online) {
		this.online = online;
	}
	
	public String getDisplayName() {
		return (isVip() ? heavySpleef.getVipPrefix() : "") + getName();
	}
	
	public String getName() {
		return bukkitPlayerRef.get() != null ? getBukkitPlayer().getName() : name;
	}
	
	public UUID getUniqueId() {
		validateOnline();
		return getBukkitPlayer().getUniqueId();
	}
	
	public boolean hasPermission(String permission) {
		validateOnline();
		return getBukkitPlayer().hasPermission(permission);
	}
	
	public boolean isVip() {
		return hasPermission(Permissions.PERMISSION_VIP);
	}
	
	public void sendMessage(String message) {
		validateOnline();
		getBukkitPlayer().sendMessage(heavySpleef.getSpleefPrefix() + message);
	}
	
	public void teleport(Location location) {
		validateOnline();
		getBukkitPlayer().teleport(location);
	}
	
	public void savePlayerState(Object key) {
		validateOnline();
		
		PlayerStateHolder holder = PlayerStateHolder.create(getBukkitPlayer());
		playerStates.put(key, holder);
	}
	
	public PlayerStateHolder getPlayerState(Object key) {
		return playerStates.get(key);
	}
	
	private void validateOnline() {
		Validate.isTrue(isOnline(), "Player is not online");
	}
	
}
