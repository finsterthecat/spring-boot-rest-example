package com.khoubyari.example.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/example/v1/login")
@Api(tags = {"login"})
public class LoginController extends AbstractRestHandler {
    
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Login")
    public @ResponseBody String login() {
        return "All good. You DO NOT need to be authenticated to call /login";
    }

}
