package com.nagarro.service;

import com.nagarro.model.StatementRequest;
import com.nagarro.model.StatementResponse;

public interface StatementService {

    StatementResponse getStatements(StatementRequest statementRequest);
}
