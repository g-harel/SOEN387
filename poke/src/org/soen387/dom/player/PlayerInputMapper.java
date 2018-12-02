package org.soen387.dom.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class PlayerInputMapper {
	private static Player getPlayer(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return PlayerFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version"),
			rs.getString("user"),
			rs.getString("pass")
		);
	}
	
	private static List<IPlayer> getPlayers(ResultSet rs) throws SQLException {
		ArrayList<IPlayer> l = new ArrayList<IPlayer>();
		while (rs.next()) {
			l.add(new PlayerProxy(rs.getLong("id")));
		}
		return l;
	}
	
	//
	
	public static List<IPlayer> findAll() throws SQLException {
		ResultSet rs = PlayerTDG.findAll();

		return PlayerInputMapper.getPlayers(rs);
	}
	
	public static Player find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Player.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {} 

		return PlayerInputMapper.getPlayer(PlayerTDG.find(id));
	}
	
	public static Player find(String user) throws SQLException, MapperException {
		return PlayerInputMapper.getPlayer(PlayerTDG.find(user));
	}
}
