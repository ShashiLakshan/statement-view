package com.nagarro.dao;

import com.nagarro.model.Statement;
import com.nagarro.model.StatementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StatementDaoImpl implements StatementDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StatementDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Statement> getStatements(StatementRequest statementRequest) {

        Map parameters = new HashMap<String, Object>();
        String query = getQuery(statementRequest, parameters);


        List<Statement> statements = jdbcTemplate.query(query, parameters, new RowMapper<Statement>() {

            @Override
            public Statement mapRow(ResultSet rs, int rowNum) throws SQLException {
                Statement statement = new Statement();
                statement.setAccountNo(rs.getString("account_number"));
                statement.setAccountType(rs.getString("account_type"));
                statement.setDate(rs.getString("datefield"));
                statement.setAmount(rs.getString("amount"));
                return statement;
            }
        });

        return statements;
    }

    private String getQuery(StatementRequest statementRequest, Map parameters) {
        StringBuilder queryBuilder = new StringBuilder();

        queryBuilder.append("SELECT a.account_number, a.account_type, s.datefield, s.amount" +
                " FROM STATEMENT s INNER JOIN ACCOUNT a ON a.id = s.account_id ");


        if (!StringUtils.isEmpty(statementRequest)) {

            queryBuilder.append(" where 1=1 ");

            if (!StringUtils.isEmpty(statementRequest.getAccountId())) {
                queryBuilder.append(" and a.account_number = :accountNo ");
                parameters.put("accountNo", statementRequest.getAccountId());
            }

            if (!StringUtils.isEmpty(statementRequest.getFromAmt())) {
                queryBuilder.append(" AND Format(s.amount, 'Currency') >= Format(:fromAmt, 'Currency')");
                parameters.put("fromAmt", statementRequest.getFromAmt().toString());
            }

            if (!StringUtils.isEmpty(statementRequest.getToAmt())) {
                queryBuilder.append(" AND Format(s.amount, 'Currency') <= Format(:toAmt, 'Currency')");
                parameters.put("toAmt", statementRequest.getToAmt().toString());
            }
        }
        return queryBuilder.toString();
    }
}
