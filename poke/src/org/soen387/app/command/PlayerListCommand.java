package org.soen387.app.command;

import java.util.List;

import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;
import org.soen387.model.player.IPlayer;
import org.soen387.model.player.PlayerInputMapper;

public class PlayerListCommand extends Command {
	public List<IPlayer> players;
	
	public PlayerListCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		try {
			this.players = PlayerInputMapper.findAll();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("can't read");
		}
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
