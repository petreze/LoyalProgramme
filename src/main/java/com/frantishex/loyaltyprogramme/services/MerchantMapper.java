package com.frantishex.loyaltyprogramme.services;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.frantishex.loyaltyprogramme.DTOs.MerchantReportDTO;

public class MerchantMapper implements RowMapper<MerchantReportDTO>{
	
	@Override
	public MerchantReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	
    	MerchantReportDTO merchantReport = new MerchantReportDTO();
    	merchantReport.setName(rs.getString("m.name"));
		merchantReport.setDiscountAmount(rs.getBigDecimal("s1"));
		merchantReport.setDiscountPercent(rs.getBigDecimal("a1"));
		merchantReport.setGivenPoints(rs.getBigDecimal("s2"));
        return merchantReport;
    }
}
