package org.lpro.boundary;

import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.entity.Commande;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("commandes")
public class CommandeRepresentation {

    @Inject
    CommandeRessource commandeRessource;
    @Context
    UriInfo uriInfo;

    @GET
    @Path("/{commandeId}")
    // RÈcuperer une commande = idcommande + token dans le header ou dans l'url
    public Response getOneCommande(@PathParam("commandeId") String commandeId, 
            @DefaultValue("") @QueryParam("token") String tokenParam,
            @DefaultValue("") @HeaderParam("X-lbs-token") String tokenHeader) {
        // on cherche la commande
        Commande cmde = this.commandeRessource.findById(commandeId);
        // la commande n'existe pas
        if(cmde == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // a-t-on un token ?     
        if (tokenParam.isEmpty() && tokenHeader.isEmpty()){
            // Pas de token =>fait 
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        // token pr√©sent, valide ?
        String token = (tokenParam.isEmpty()) ? tokenHeader : tokenParam;
        Boolean isTokenValide = cmde.getToken().equals(token);
        if(!isTokenValide){
            return Response.status(Response.Status.FORBIDDEN).build();
        }else{
             return Response.ok(buildCommandeObject(cmde)).build();
        }
    }

    @POST
    public Response addCommande(@Valid Commande commande) {
        Commande newCommande = this.commandeRessource.save(commande);
        URI uri = uriInfo.getAbsolutePathBuilder().path(newCommande.getId()).build();
        return Response.created(uri)
                .entity(newCommande)
                .build();
    }

    private JsonObject buildCommandeObject(Commande c) {
        return Json.createObjectBuilder()
                .add("commande", buildJsonForCommande(c))
                .build();
    }

    private JsonObject buildJsonForCommande(Commande c) {
        return Json.createObjectBuilder()
                .add("id", c.getId())
                .add("nom_client", c.getNom())
                .add("mail_client", c.getMail())
                .add("livraison", buildJsonForLivraison(c))
                .add("token", c.getToken())
                .build();
    }

    private JsonObject buildJsonForLivraison(Commande c) {
        return Json.createObjectBuilder()
                .add("date", c.getDateLivraison())
                .add("heure", c.getHeureLivraison())
                .build();
    }
}
