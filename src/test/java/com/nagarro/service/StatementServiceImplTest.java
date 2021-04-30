package com.nagarro.service;


import com.nagarro.dao.StatementDao;
import com.nagarro.exception.AccessPrivilegeException;
import com.nagarro.model.StatementRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class StatementServiceImplTest {

    @Mock
    private StatementDao statementDao;

    @InjectMocks
    private StatementServiceImpl statementService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getStatement_shouldSuccess_whenAdminUserInvoked() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getName()).thenReturn("admin");
        SecurityContextHolder.setContext(securityContext);

        Mockito.when(statementDao.getStatements(Mockito.any())).thenReturn(null);

        statementService.getStatements(null);

    }

    @Test
    public void getStatement_shouldSuccess_whenOtherUserInvoked() {
        try {
            Authentication authentication = Mockito.mock(Authentication.class);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            Mockito.when(authentication.getName()).thenReturn("user");
            SecurityContextHolder.setContext(securityContext);

            Mockito.when(statementDao.getStatements(new StatementRequest())).thenReturn(null);

            statementService.getStatements(new StatementRequest());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test (expected = AccessPrivilegeException.class)
    public void getStatement_shouldThrowAccessPrivilegeException_whenOtherUserInvokedWithAccountId() {
        try {
            Authentication authentication = Mockito.mock(Authentication.class);
            SecurityContext securityContext = Mockito.mock(SecurityContext.class);
            Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
            Mockito.when(authentication.getName()).thenReturn("user");
            SecurityContextHolder.setContext(securityContext);

            Mockito.when(statementDao.getStatements(Mockito.any())).thenReturn(null);

            StatementRequest testRequest = new StatementRequest();
            testRequest.setAccountId("testId");
            statementService.getStatements(testRequest);
        } catch (Exception e) {
            Assert.fail();
        }

    }
}
