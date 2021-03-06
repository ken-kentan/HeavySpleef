/*
 * This file is part of HeavySpleef.
 * Copyright (c) 2014-2016 Matthias Werning
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
package de.xaniox.heavyspleef.commands;

import de.xaniox.heavyspleef.commands.base.Command;
import de.xaniox.heavyspleef.commands.base.CommandContext;
import de.xaniox.heavyspleef.core.HeavySpleef;
import de.xaniox.heavyspleef.core.Permissions;
import de.xaniox.heavyspleef.core.i18n.I18N;
import de.xaniox.heavyspleef.core.i18n.I18NManager;
import de.xaniox.heavyspleef.core.i18n.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

public class CommandReload {

	private final I18N i18n = I18NManager.getGlobal();
	
	@Command(name = "reload", permission = Permissions.PERMISSION_RELOAD,
			descref = Messages.Help.Description.RELOAD, usage = "/spleef reload")
	public void onReloadCommand(CommandContext context, HeavySpleef heavySpleef) {
		CommandSender sender = context.getSender();
		if (sender instanceof Player) {
			sender = heavySpleef.getSpleefPlayer(sender);
		}
		
		long timeBefore = System.currentTimeMillis();
		heavySpleef.reload();
		long timeDif = System.currentTimeMillis() - timeBefore;
		
		PluginDescriptionFile pdf = heavySpleef.getPlugin().getDescription();
		
		sender.sendMessage(i18n.getVarString(Messages.Command.PLUGIN_RELOADED)
				.setVariable("time-dif-ms", String.valueOf(timeDif))
				.setVariable("time-dif-s", String.valueOf(timeDif / 1000D))
				.setVariable("version", pdf.getVersion())
				.toString());
	}
	
}