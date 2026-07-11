package com.github.robsonrjunior.master.library.apis;

import com.github.robsonrjunior.master.library.model.Series;
import com.github.robsonrjunior.master.library.service.SeriesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("series")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SeriesResource {

    @Inject
    private SeriesService service;

    @GET
    public List<Series> list(@QueryParam("title") String title) {
        return (title == null || title.isBlank()) ? service.list() : service.search(title);
    }

    @GET
    @Path("{id}")
    public Series get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @POST
    public Response create(@Valid Series series) {
        return Response.status(Response.Status.CREATED).entity(service.create(series)).build();
    }

    @PUT
    @Path("{id}")
    public Series update(@PathParam("id") Long id, @Valid Series series) {
        return service.update(id, series);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
