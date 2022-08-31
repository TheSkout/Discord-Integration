package di.dilogin.minecraft.ext.luckperms;

import java.util.Optional;

import di.dilogin.controller.DILoginController;
import di.dilogin.dao.DIUserDao;
import di.dilogin.entity.DIUser;
import di.dilogin.minecraft.ext.luckperms.LuckPermsController;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Class for handling role add to member events.
 */
public class GuildMemberRoleEvent extends ListenerAdapter {

	/**
	 * User manager in the database.
	 */
	private final DIUserDao dao = DILoginController.getDIUserDao();

	/**
	 * Main event body.
	 * @param event It is the object that includes the event information.
	 */
	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		Optional<DIUser> optDIUser = dao.get(event.getUser().getIdLong());
		if (!optDIUser.isPresent())
			return;

		DIUser user = optDIUser.get();

		if (!user.getPlayerBukkit().isPresent())
			return;

		for (Role role : event.getRoles()) {
			for (String group : LuckPermsController.getMinecraftRoleFromDiscordRole(role.getId())) {
					if (user.getPlayerBukkit().isPresent() && !LuckPermsController.isUserInGroup(user.getPlayerBukkit().get(), group))
						LuckPermsController.addGroup(user.getPlayerBukkit().get(), group,
								"Get " + role + " role in discord.");
			}
		}
	}

	/**
	 * Main event body.
	 * @param event It is the object that includes the event information.
	 */
	@Override
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
		Optional<DIUser> optDIUser = dao.get(event.getUser().getIdLong());
		if (!optDIUser.isPresent())
			return;

		DIUser user = optDIUser.get();

		if (!user.getPlayerBukkit().isPresent())
			return;

		for (Role role : event.getRoles()) {
			for (String group : LuckPermsController.getMinecraftRoleFromDiscordRole(role.getId())) {
					if (user.getPlayerBukkit().isPresent() && LuckPermsController.isUserInGroup(user.getPlayerBukkit().get(), group))
						LuckPermsController.removeGroup(user.getPlayerBukkit().get(), group,
								"Removed " + role + " role in discord.");
			}
		}
	}
}