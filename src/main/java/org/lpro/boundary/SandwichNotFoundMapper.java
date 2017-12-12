/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpro.boundary;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nicolas
 */
@Provider
public class SandwichNotFoundMapper  implements ExceptionMapper<SandwichNotFound> {
    
    @Override
    public Response toResponse(SandwichNotFound exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorJson(exception))
                .build();
    }

    private JsonObject errorJson(SandwichNotFound exception) {
        return Json.createObjectBuilder()
                .add("error", Response.Status.NOT_FOUND.getStatusCode())
                .add("message", exception.getMessage())
                .build();
    }
    
}
