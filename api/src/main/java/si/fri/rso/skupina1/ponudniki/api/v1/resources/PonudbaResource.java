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
import si.fri.rso.skupina1.ponudniki.lib.Ponudba;
import si.fri.rso.skupina1.ponudniki.services.beans.PonudbaBean;
import com.kumuluz.ee.logs.cdi.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@Log
@ApplicationScoped
@Path("/offers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PonudbaResource {

    private Logger log = Logger.getLogger(PonudbaResource.class.getName());

    @Inject
    PonudbaBean ponudbaBean;

    @Context
    protected UriInfo uriInfo;


    @Operation(description = "Pridobi vse ponudbe.", summary = "Pridobi vse ponudbe")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam ponudb",
                    content = @Content(schema = @Schema(implementation = Ponudba.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Število objektov v seznamu")}
            )})
    @GET
    public Response getPonudbe() {

        List<Ponudba> ponudba = ponudbaBean.getPonudbeFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(ponudba).build();
    }


    @Operation(description = "Pridobi podatke o ponudbi", summary = "Pridobi podatke o ponudbi")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Ponudba",
                    content = @Content(
                            schema = @Schema(implementation = Ponudba.class))
            )})
    @GET
    @Path("/{ponudbaId}")
    public Response getPonudba(@Parameter(description = "Ponudba ID.", required = true)
                                @PathParam("ponudbaId") Integer ponudbaId) {

        Ponudba ponudba= ponudbaBean.getPonudba(ponudbaId);

        if (ponudba == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(ponudba).build();
    }
    
    
}
