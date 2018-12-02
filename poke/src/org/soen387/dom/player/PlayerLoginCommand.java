package org.soen387.dom.player;

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
		
		if (user == null || user.equals("") || pass == null || pass.equals("")) throw new CommandException("missing user and/or pass");

		try {
			this.currentPlayer = PlayerInputMapper.find(user);			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommandException("no player", e);
		}
		
		if (!this.currentPlayer.getPass().equals(pass)) throw new CommandException("no player");
	}

	public void setUp() throws CommandException {}

	public void tearDown() throws CommandError {}
}
