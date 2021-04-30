package com.nagarro.controller;

import com.nagarro.model.StatementRequest;
import com.nagarro.model.StatementResponse;
import com.nagarro.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("statement")
public class StatementController {

    @Autowired
    StatementService statementService;

    @PostMapping
    public ResponseEntity<StatementResponse> getStatements(@Valid @RequestBody StatementRequest statementRequest) {

        return new ResponseEntity<>(statementService.getStatements(statementRequest), HttpStatus.OK);
    }

}
