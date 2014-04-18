package com.excilys.computerdatabase.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.excilys.computerdatabase.domain.Company;
import com.excilys.computerdatabase.domain.Computer;

public class ComputerExtractor implements ResultSetExtractor<Computer>
{
	@Override
	public Computer extractData(ResultSet rs) throws SQLException, DataAccessException
	{
		Computer c = new Computer();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		if (rs.getTimestamp(3) != null)
			c.setIntroduced(new DateTime(rs.getTimestamp("introduced")));
		else
			c.setIntroduced(null);
		if (rs.getTimestamp(4) != null)
			c.setDiscontinued(new DateTime(rs.getTimestamp("discontinued")));
		else
			c.setDiscontinued(null);
		Company company = new Company();
		company.setId(rs.getInt(5));
		company.setName(rs.getString(6));
		c.setCompany(company);

		return c;
	}
}