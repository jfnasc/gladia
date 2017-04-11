package com.example.rest;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.example.service.ExampleService;

@Path("/members")
@RequestScoped
public class MemberResourceRESTService {

	@EJB
	ExampleService exampleService;

	@GET
	public String listAllMembers() {
		return "ejb call: " + exampleService.whoAmI();
	}
}
