/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lpro.boundary;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.lpro.entity.Categorie;
import org.lpro.entity.Sandwich;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *
 * @author Nicolas
 */
@Stateless
@Path("sandwichs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SandwichRessource {
    
    @Inject
    SandwichManager sm;
    
    /**
    @GET
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404 message = "Not Found"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")})
    public Response getSandwichs() {
//        JsonObject json = Json.createObjectBuilder()
//                .add("type", "collection")
//                .add("categories", getCategorieList())
//                .build();
//        return Response.ok(json).build();
        GenericEntity<List<Sandwich>> liste = new GenericEntity<List<Sandwich>>(this.sm.findAll()) {
        };
        return Response.ok(liste).build();
    } */
    
    @GET
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404 message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal server error")})
    public Response getSandwichs(
            @QueryParam("t") String ptype,
            @DefaultValue("0") @QueryParam("img") String img,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue("10") @QueryParam("size") int size){
        if(page <=0)
            page =1;
        if(size <0)
            size=1;
        GenericEntity<List<Sandwich>> liste = new GenericEntity<List<Sandwich>>(this.sm.findAll(page, size)) { };
        int aUneImage = Integer.parseInt(img); 
        if(ptype != null){
            liste = new GenericEntity<List<Sandwich>>(this.sm.findWithParams(0, ptype, page, size) ) { };
        }
        if(aUneImage == 1){
            liste = new GenericEntity<List<Sandwich>>(this.sm.findWithParams(aUneImage, null, page, size) ) { };
        }
        if(aUneImage ==1 && ptype !=null){
            liste = new GenericEntity<List<Sandwich>>(this.sm.findWithParams(aUneImage, ptype, page, size) ) { };
        }
        return Response.ok(liste).build();
    }
    
    @GET
    @Path("{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404 message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal server error")})
    public Response getOneSandwich(@PathParam("id") long id, @Context UriInfo uriInfo) {
        return Optional.ofNullable(sm.findById(id))
                //.map(c -> Response.ok(categorie2Json(c)).build())
                .map(c -> Response.ok(c).build())
                .orElseThrow(() -> new CategorieNotFound("Ressource non disponible "+ uriInfo.getPath()));
                //.orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    
    @POST
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401 message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")})
    public Response newSandwich(@Valid Sandwich s, @Context UriInfo uriInfo) {
        Sandwich newOne = this.sm.save(s);
        long id = newOne.getId();
        URI uri = uriInfo.getAbsolutePathBuilder().path("/"+id).build();
        return Response.created(uri).build();
    }
            
    @DELETE
    @Path("{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401 message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")})
    public Response suppression(@PathParam("id") long id) {
        this.sm.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @PUT
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 401 message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")})
    @Path("{id}")
    public Sandwich update(@PathParam("id") long id, Sandwich s) {
        s.setId(id);
        return this.sm.save(s);
    }

    private JsonObject sandwich2Json(Sandwich s) {
        JsonObject json = Json.createObjectBuilder()
                .add("type", "resource")
                .add("sandwich", buildJson(s))
                .build();
        return json;
    }
    
    private JsonArray getSandwichList(int page, int size) {
        JsonArrayBuilder jab = Json.createArrayBuilder();
        this.sm.findAll(page, size).forEach((s) -> {
            jab.add(buildJson(s));
            });
        return jab.build();
    }
    
    private JsonObject buildJson(Sandwich s) {
        return Json.createObjectBuilder()
                .add("id",s.getId())
                .add("nom", s.getNom())
                .add("desc", s.getDescription())
                .add("type_pain", s.getType_pain())
                .add("img", s.getImg())
                .build();
    }
    
    
}
