package org.cat73.bukkitplugin.command.commandhandler;

import java.util.Collection;
import java.util.HashMap;

import org.cat73.bukkitplugin.command.command.CommandInfo;
import org.cat73.bukkitplugin.command.command.ICommand;

public abstract class SimpleCommandHandler implements ICommandHandler {
    /** 存储的命令列表 */
    private final HashMap<String, ICommand> commandList = new CommandHashMap();

    @Override
    public void registerCommand(final ICommand command) {
        // 获取命令的信息
        final CommandInfo info = ICommandHandler.getCommandInfo(command);
        final String name = info.name();

        // 加入命令列表
        this.commandList.put(name, command);
    }

    @Override
    public Collection<ICommand> getCommands() {
        return this.commandList.values();
    }

    @Override
    public ICommand getCommand(final String name) {
        return this.commandList.get(name);
    }
}
