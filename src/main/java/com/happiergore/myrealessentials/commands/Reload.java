package com.happiergore.myrealessentials.commands;

import com.happiergore.myrealessentials.main;
import com.happiergore.myrealessentials.commands.Arguments.ArgEnum;
import com.happiergore.myrealessentials.commands.Arguments.ArgumentType;
import static com.happiergore.myrealessentials.helper.TextUtils.parseColor;
import java.util.HashSet;
import org.bukkit.command.CommandSender;

/**
 *
 * @author HappierGore
 */
public class Reload extends Commands {

    public Reload() {
        super(new CommandType(new HashSet<ArgumentType>() {
            {
                add(new ArgumentType(ArgEnum.string).setName("reload").setPosition(1).setHint("&cUse: reload"));
            }
        }));
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        main.getPlugin(main.class).reloadConfig();
        main.configYML = main.getPlugin(main.class).getConfig();
        sender.sendMessage(parseColor("&aMy real essentials has been reloaded"));
    }
}
