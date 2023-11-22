package org.currency.mapper;

import org.currency.entity.Currency;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyRowMapper implements RowMapper<Currency> {
    private static final String ROW_CODE = "code";
    private static final String ROW_RATE = "rate";

    @Override
    public Currency mapRow(ResultSet resultSet, int i) throws SQLException {
        Currency currency = new Currency();
        currency.setCode(resultSet.getString(ROW_CODE));
        currency.setRate(resultSet.getDouble(ROW_RATE));
        return currency;
    }
}
