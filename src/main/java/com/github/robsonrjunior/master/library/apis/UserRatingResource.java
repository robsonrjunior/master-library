package com.github.robsonrjunior.master.library.apis;

import com.github.robsonrjunior.master.library.dto.RatingRequest;
import com.github.robsonrjunior.master.library.dto.RatingUpdateRequest;
import com.github.robsonrjunior.master.library.model.UserRating;
import com.github.robsonrjunior.master.library.service.UserRatingService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
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

@Path("ratings")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRatingResource {

    @Inject
    private UserRatingService service;

    @GET
    public List<UserRating> list(
        @QueryParam("userId") Long userId,
        @QueryParam("mediaId") Long mediaId
    ) {
        if (userId != null) {
            return service.listByUser(userId);
        }
        if (mediaId != null) {
            return service.listByMedia(mediaId);
        }
        throw new BadRequestException("Provide either 'userId' or 'mediaId' query parameter");
    }

    @POST
    public Response create(@Valid RatingRequest request) {
        UserRating rating = service.rate(
            request.getUserId(),
            request.getMediaId(),
            request.getScore(),
            request.getComment()
        );
        return Response.status(Response.Status.CREATED).entity(rating).build();
    }

    @PUT
    @Path("{id}")
    public UserRating update(@PathParam("id") Long id, @Valid RatingUpdateRequest request) {
        return service.update(id, request.getScore(), request.getComment());
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
