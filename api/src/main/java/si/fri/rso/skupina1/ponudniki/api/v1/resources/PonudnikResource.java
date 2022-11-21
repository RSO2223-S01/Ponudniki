package si.fri.rso.skupina1.ponudniki.api.v1.resources;


import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.skupina1.ponudniki.lib.Ponudnik;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudnikBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/ponudniki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PonudnikResource {

    private Logger log = Logger.getLogger(PonudnikResource.class.getName());

    @Inject
    PonudnikBean ponudnikBean;

    @Context
    protected UriInfo uriInfo;


    @Operation(description = "Pridobi vse ponudnike.", summary = "Pridobi vse ponudnike")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ponudnikov",
                    content = @Content(schema = @Schema(implementation = Ponudnik.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število objektov v seznamu")}
            )})
    @GET
    public Response getPonudniki() {

        List<Ponudnik> ponudnik = ponudnikBean.getPonudnikiFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(ponudnik).build();
    }

    
    @Operation(description = "Pridobi podatke o ponudniku", summary = "Pridobi podatke o ponudniku")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ponudnik",
                    content = @Content(
                            schema = @Schema(implementation = Ponudnik.class))
            )})
    @GET
    @Path("/{ponudnikId}")
    public Response getPonudnik(@Parameter(description = "Podnudnik ID.", required = true)
                                     @PathParam("ponudnikId") Integer ponudnikId) {

        Ponudnik ponudnik = ponudnikBean.getPonudnik(ponudnikId);

        if (ponudnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(ponudnik).build();
    }

    
    
    @Operation(description = "Dodaj ponudnika.", summary = "Dodaj ponudnika")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Ponudnik successfuly added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error.")
    })
    @POST
    public Response createPonudnik(@RequestBody(
            description = "DTO objekt s ponudnikom.",
            required = true, content = @Content(
            schema = @Schema(implementation = Ponudnik.class))) Ponudnik ponudnik) {

        if ((ponudnik.getIme() == null || ponudnik.getMesto() == null || ponudnik.getPostnaSt() == null) || ponudnik.getUlica() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            ponudnik = ponudnikBean.createPonudnik(ponudnik);
        }

        return Response.status(Response.Status.CONFLICT).entity(ponudnik).build();

    }


    @Operation(description = "Posodobi ponudnika.", summary = "Posodobi ponudnika")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Ponudnik successfully updated."
            )
    })
    @PUT
    @Path("{ponudnikId}")
    public Response putPonudnik(@Parameter(description = "Ponudnik ID.", required = true)
                                     @PathParam("ponudnikId") Integer ponudnikId,
                                     @RequestBody(
                                             description = "DTO objekt s ponudnikom",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Ponudnik.class)))
                                             Ponudnik ponudnik){

        ponudnik = ponudnikBean.putPonudnik(ponudnikId, ponudnik);

        if (ponudnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Izbriši ponudnika.", summary = "Izbriši ponudnika")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Ponudnik successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{ponudnikId}")
    public Response deletePonudnik(@Parameter(description = "Ponudnik ID.", required = true)
                                        @PathParam("ponudnikId") Integer ponudnikId){

        boolean deleted = ponudnikBean.deletePonudnik(ponudnikId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
