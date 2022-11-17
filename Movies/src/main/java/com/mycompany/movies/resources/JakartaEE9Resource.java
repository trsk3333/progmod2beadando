package com.mycompany.movies.resources;

import Service.RatingService;
import static Service.RatingService.averageOfScores;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import static Service.FileHandlingService.ImportMoviesFromLocalFile;
import static Service.FileHandlingService.exportMoviesToLocalFile;
import business.Log;
import business.Movie;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("jakartaee9")
public class JakartaEE9Resource {
    
    private String pathToMoviesFile = "C:\\Workspace\\progmod2\\MovieRating\\Movies\\movies\\movies.json";
    
    @GET
    @Path("test")
    public Response ping(){
        return Response
                .ok("ok")
                .build();
    }
    @GET
    @Path("getMovies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(){
        try{
            List<Movie> movieList = ImportMoviesFromLocalFile(pathToMoviesFile);
            JSONObject jObj = new JSONObject();
            JSONArray jArray = new JSONArray();
            for(int i = 0; i < movieList.size(); i++){
                JSONObject jMovie = new JSONObject();
                jMovie.put("Title", movieList.get(i).getTitle());
                jMovie.put("Director", movieList.get(i).getDirector());
                jMovie.put("ReleaseDate", movieList.get(i).getReleaseDate().toString());
                jMovie.put("PrimaryGenre", movieList.get(i).getPrimaryGenre().toString());
                jMovie.put("Scores", averageOfScores(movieList.get(i).getScores()));
                
                jArray.put(jMovie);
            }
            jObj.put("movies", jArray);
            return Response.ok(jObj.toString()).build();
        }catch(Exception e){
            Log el = new Log();
            el.handleException(e);
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }
    @GET
    @Path("updateScore")
    public Response updateScores(@QueryParam("index") int index){
        try{
            List<Movie> movieList = ImportMoviesFromLocalFile(pathToMoviesFile);
            return Response.ok(averageOfScores(movieList.get(index).getScores())).build();
        }catch(Exception e){
            Log el = new Log();
            el.handleException(e);
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }
    @POST
    @Path("rateMovie")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rateMovie(final PostParamBean input){
        try{
            List<Movie> movieList = ImportMoviesFromLocalFile(pathToMoviesFile);
            RatingService.rateMovie(movieList.get(input.index), input.score);
            exportMoviesToLocalFile(pathToMoviesFile, movieList);

            return Response.ok("The movie was rated successfully!").build();
        }catch(Exception e){
            Log el = new Log();
            el.handleException(e);
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }
}
