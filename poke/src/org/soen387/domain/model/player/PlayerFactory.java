package org.soen387.domain.model.player;

import java.sql.SQLException;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.uow.MissingMappingException;
import org.dsrg.soenea.uow.UoW;

public class PlayerFactory {
	public static Player createNew(String user, String pass) throws SQLException, MissingMappingException, MapperException {
		return PlayerFactory.createNew(PlayerTDG.maxId(), 1L, user,  pass);
	}
	
	public static Player createNew(Long id, long version, String user, String pass) throws MissingMappingException, MapperException {
		Player d = new Player(id, version, user, pass);
		UoW.getCurrent().registerNew(d);
		return d;
	}
	
	public static Player createClean(Long id, long version, String user, String pass) {
		Player d = new Player(id, version, user, pass);
		UoW.getCurrent().registerClean(d);
		return d;
	}
}
