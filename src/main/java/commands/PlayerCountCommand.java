package commands;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import utils.selfdata.ChannelIdData;
import utils.tools.GTools;
import utils.users.GTMUser;
import utils.users.Rank;

import static utils.tools.GTools.sendThenDelete;
import static utils.tools.GTools.updateOnlinePlayers;

public class PlayerCountCommand extends Command {

    public PlayerCountCommand() {
        super("playercount", "Manage GTM player count display", Rank.ADMIN, Type.DISCORD_ONLY);
    }

    @Override
    public void onCommandUse(Message message, Member member, GTMUser gtmUser, MessageChannel channel, String[] args) {
        // If there are no command arguments send sub command help list
        if (args.length == 0) {
            // Send msg then delete after defined time in utils.config
            sendThenDelete(channel, getPlayerCountHelpMsg());
        }

        // Suggestions SetChannel Command
        else if (args[0].equalsIgnoreCase("setchannel")) {

            VoiceChannel playerCountChannel = GTools.jda.getVoiceChannelById(Long.parseLong(args[1]));

            // If its a valid voice channel id
            if (playerCountChannel != null) {

                // Set as player count channel
                ChannelIdData.get().setPlayerCountChannelId(Long.parseLong(args[1]));

                // Updates online players
                updateOnlinePlayers();

                // Send success msg
                sendThenDelete(channel, "**<`"+playerCountChannel.getIdLong()+"`>"+
                        " has been set as the Player Count channel!**");

            }

            // If channel id is invalid
            else {
                sendThenDelete(channel, "**Invalid voice channel Id! Channel not set.**");
            }

        }

        // If none of the sub arguments match
        else {
            // Send msg then delete after defined time in utils.config
            sendThenDelete(channel, getPlayerCountHelpMsg());
        }
    }

    private static Message getPlayerCountHelpMsg() {
        return new MessageBuilder()
                .append("> **Please enter a valid command argument:**\n")
                .append("> `/PlayerCount SetChannel <ID>` - *Displays GTM's player count at the given channel*\n")
                .build();
    }

}
