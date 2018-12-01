package org.soen387.player;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.command.CommandError;
import org.dsrg.soenea.domain.command.CommandException;
import org.dsrg.soenea.domain.command.impl.Command;
import org.dsrg.soenea.domain.helper.Helper;

public class PlayerLoginCommand extends Command {
	public Player currentPlayer = null; 
	
	public PlayerLoginCommand(Helper helper) {
		super(helper);
	}

	public void process() throws CommandException {
		String user = this.helper.getString("user");
		String pass = this.helper.getString("pass");
		
		if (user == null || pass == null) throw new CommandException("missing user and/or pass");

		try {
			this.currentPlayer = PlayerInputMapper.find(user);			
		} catch (Exception e) {
			throw new CommandException("no player", e);
		}
		
		if (!this.currentPlayer.getPass().equals(pass)) throw new CommandException("no player");
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
