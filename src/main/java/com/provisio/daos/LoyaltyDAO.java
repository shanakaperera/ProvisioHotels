package com.provisio.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.provisio.models.Loyalty;

import com.provisio.utils.ConnectionUtil;

// DAO to calculate loyalty points (150 pts per night)
public class LoyaltyDAO  {

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Loyalty loyaltyPoints(Integer userId) {

		Loyalty loyalty = null;

		try {

			String sql = "SELECT sum(nights)*150 as 'nights' FROM  reservations where user_id = ?";
			
			con = ConnectionUtil.getConnection();

			ps = con.prepareStatement(sql);
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				loyalty = new Loyalty();
				loyalty.setLoyaltyPoints(rs.getInt("nights"));
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loyalty;
	}
}
