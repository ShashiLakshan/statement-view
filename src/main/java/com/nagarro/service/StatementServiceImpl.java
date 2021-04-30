package com.nagarro.service;

import com.nagarro.dao.StatementDao;
import com.nagarro.exception.AccessPrivilegeException;
import com.nagarro.model.StatementRequest;
import com.nagarro.model.StatementResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class StatementServiceImpl implements StatementService {

    private StatementDao statementDao;

    @Autowired
    public StatementServiceImpl(StatementDao statementDao) {
        this.statementDao = statementDao;
    }

    @Override
    public StatementResponse getStatements(StatementRequest statementRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StatementResponse statementResponse = new StatementResponse();

        if ("admin".equals(auth.getName())) {
            statementResponse.setStatements(statementDao.getStatements(statementRequest));
        } else {
            if (StringUtils.isEmpty(statementRequest.getAccountId())
                    && StringUtils.isEmpty(statementRequest.getFromAmt())
                    && StringUtils.isEmpty(statementRequest.getToAmt())
                    && StringUtils.isEmpty(statementRequest.getFromDate())
                    && StringUtils.isEmpty(statementRequest.getEndDate())) {
                statementResponse.setStatements(statementDao.getStatements(null));
            } else {
                throw new AccessPrivilegeException("Less user access privileges");
            }

        }

        return statementResponse;
    }
}
