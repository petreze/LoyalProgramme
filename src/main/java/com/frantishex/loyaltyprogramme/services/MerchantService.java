package com.frantishex.loyaltyprogramme.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frantishex.loyaltyprogramme.DTOs.MerchantReportDTO;
import com.frantishex.loyaltyprogramme.models.Merchant;

@Service
@Transactional
public class MerchantService {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EntityManager em;

	void createMerchant(Merchant merchant) {
		em.merge(merchant);
	}

	Merchant getById(long id) {
		return em.find(Merchant.class, id);
	}

	public List<Merchant> getAll() {
		return em.createNamedQuery("allMerchants", Merchant.class).setMaxResults(100).getResultList();
	}

	public Merchant getByName(String merchantName) {
		return em.createNamedQuery("merchantByName", Merchant.class).setParameter("merchantname", merchantName)
				.setMaxResults(100).getResultList().stream().findFirst().orElse(null);
	}

	@Autowired
	DataSource dataSource;

	public MerchantReportDTO merchantReport(final String merchantName, final String sDateFrom, final String sDateTo)
			throws SQLException {

		String sql = "SELECT m.name, sum(s.discount_amount) s1, avg(s.discount_percent) a1, sum(s.given_points) s2"
				+ " FROM Merchant m, Customer c, Sale s"
				+ " WHERE s.customer_id = c.id AND c.merchant_id = m.id AND m.name = ? AND s.date > ? AND s.date < ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			int param = 1;
			stmt.setString(param++, merchantName);
			stmt.setTimestamp(param++, java.sql.Timestamp.valueOf(LocalDateTime.parse(sDateFrom, formatter)));
			stmt.setTimestamp(param++, java.sql.Timestamp.valueOf(LocalDateTime.parse(sDateTo, formatter)));

			try (ResultSet rs = stmt.executeQuery();) {

				if (!rs.next()) {
					return null;
				}

				MerchantReportDTO merchantReport = new MerchantReportDTO();
				merchantReport.setName(rs.getString("m.name"));
				merchantReport.setDiscountAmount(rs.getBigDecimal("s1"));
				merchantReport.setDiscountPercent(rs.getBigDecimal("a1"));
				merchantReport.setGivenPoints(rs.getBigDecimal("s2"));

				return merchantReport;
			}
		}

	}

	public MerchantReportDTO merchantReport2(final String merchantName, final String sDateFrom, final String sDateTo)
			throws SQLException {

		String sql = "SELECT m.name, sum(s.discount_amount) s1, avg(s.discount_percent) a1, sum(s.given_points) s2"
				+ " FROM Merchant m, Customer c, Sale s"
				+ " WHERE s.customer_id = c.id AND c.merchant_id = m.id AND m.name = ? AND s.date > ? AND s.date < ?";

		return jdbcTemplate.queryForObject(sql, new Object[] { merchantName, sDateFrom, sDateTo },
				new RowMapper<MerchantReportDTO>() {

					@Override
					public MerchantReportDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

						MerchantReportDTO merchantReport = new MerchantReportDTO();
						merchantReport.setName(rs.getString("m.name"));
						merchantReport.setDiscountAmount(rs.getBigDecimal("s1"));
						merchantReport.setDiscountPercent(rs.getBigDecimal("a1"));
						merchantReport.setGivenPoints(rs.getBigDecimal("s2"));
						return merchantReport;
					}
				});
	}

	/*
	 * public List<Sale> reportSale(String sDateFrom, String sDateTo) throws
	 * ParseException {
	 * 
	 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	 * CriteriaQuery<Sale> cq = cb.createQuery(Sale.class); Root<Sale> entity =
	 * cq.from(Sale.class);
	 * 
	 * DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); LocalDateTime
	 * dateFrom = LocalDateTime.parse(sDateFrom, formatter); LocalDateTime
	 * dateTo = LocalDateTime.parse(sDateTo, formatter);
	 * 
	 * cq.where(cb.between(entity.get("dateCreated"), dateFrom, dateTo),
	 * cb.equal(entity.get("confirmed"), true));
	 * 
	 * cq.orderBy(cb.asc(entity.get("dateCreated")));
	 * 
	 * return
	 * getEntityManager().createQuery(cq).setMaxResults(100).getResultList(); }
	 */
}