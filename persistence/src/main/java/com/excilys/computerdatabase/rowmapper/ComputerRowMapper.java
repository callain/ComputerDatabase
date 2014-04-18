package com.excilys.computerdatabase.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdatabase.domain.Computer;
import com.excilys.computerdatabase.extractor.ComputerExtractor;

public class ComputerRowMapper implements RowMapper<Computer>
{
	@Override
	public Computer mapRow(ResultSet rs, int line) throws SQLException
	{
		ComputerExtractor cExtractor = new ComputerExtractor();
		return cExtractor.extractData(rs);
	}
}
