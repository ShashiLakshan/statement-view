package com.nagarro.dao;

import com.nagarro.model.Statement;
import com.nagarro.model.StatementRequest;

import java.util.List;

public interface StatementDao {

    List<Statement> getStatements(StatementRequest statementRequest);
}
