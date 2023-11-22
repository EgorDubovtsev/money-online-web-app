package org.currency.dao;

import lombok.RequiredArgsConstructor;
import org.currency.entity.Currency;
import org.currency.mapper.CurrencyRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@Repository
public class CurrencyDao {
    private CurrencyRowMapper currencyRowMapper = new CurrencyRowMapper();

    private static final String SELECT_ENABLED_CURRENCIES = "SELECT * FROM currency_service.currencies_rates where is_enabled=1 ";
    private static final String SELECT_CURRENCY_RATE = "SELECT rate FROM currency_service.currencies_rates where code=:code";
    private static final String UPDATE_CURRENCY_RATE = "UPDATE currency_service.currencies_rates set rate = :rate where code=:code";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Currency> getEnabledCurrencies() {
        return jdbcTemplate.query(SELECT_ENABLED_CURRENCIES, currencyRowMapper);
    }

    public Double getCurrencyRate(String code) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("code", code);
        return jdbcTemplate.queryForObject(SELECT_CURRENCY_RATE, parameters, Double.class);
    }

    public void updateRate(String code, Double rate) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("code", code)
                .addValue("rate", rate);

        jdbcTemplate.update(UPDATE_CURRENCY_RATE, parameters);
    }
}
