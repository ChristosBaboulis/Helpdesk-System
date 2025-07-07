package com.example.helpdesk.resource;

import com.example.helpdesk.auth.AuthRequest;
import com.example.helpdesk.auth.AuthResponse;
import com.example.helpdesk.auth.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(AuthRequest request) {
        return authService.authenticate(request.getUsername(), request.getPassword())
                .map(token -> Response.ok(new AuthResponse(token)).build())
                .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}