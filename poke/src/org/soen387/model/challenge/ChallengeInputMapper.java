package org.soen387.model.challenge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.ObjectRemovedException;
import org.dsrg.soenea.domain.mapper.DomainObjectNotFoundException;
import org.dsrg.soenea.domain.mapper.IdentityMap;

public class ChallengeInputMapper {
	private static Challenge getChallenge(ResultSet rs) throws SQLException, MapperException {
		if (!rs.next()) throw new MapperException("doesn't exist");

		return ChallengeFactory.createClean(
			rs.getLong("id"),
			rs.getLong("version"),
			rs.getLong("challenger"),
			rs.getLong("challengee"),
			rs.getInt("status"),
			rs.getLong("deck_id")
		);
	}

	private static List<IChallenge> getChallenges(ResultSet rs) throws SQLException {
		ArrayList<IChallenge> l = new ArrayList<IChallenge>();
		while (rs.next()) {
			l.add(new ChallengeProxy(rs.getLong("id")));
		}
		return l;
	}

	//

	public static List<IChallenge> findAll() throws SQLException {
		ResultSet rs = ChallengeTDG.findAll();

		return ChallengeInputMapper.getChallenges(rs);
	}

	public static Challenge find(Long id) throws SQLException, MapperException {
		try {
			return IdentityMap.get(id, Challenge.class);
		} catch (DomainObjectNotFoundException e) {
		} catch (ObjectRemovedException e) {}

		return ChallengeInputMapper.getChallenge(ChallengeTDG.find(id));
	}
}
