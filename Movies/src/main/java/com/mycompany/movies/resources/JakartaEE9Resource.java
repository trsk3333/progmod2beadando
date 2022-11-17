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
import business.WrongRequestException;
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
    /**
     * A tárolt filmek lekérdezését kiszolgáló függvény.
     * @return HTTP Response mely a filmeket json formátumban továbbítja a kliens felé
     */
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
    /**
     * Egy adott film pontszámaiból kiszámolt átlag lekérdezését kiszolgáló függvény.
     * @param index a film sorszáma
     * @return a film pontszámainak átlaga
     */
    @GET
    @Path("updateScore")
    public Response updateScores(@QueryParam("index") int index){
        try{
            List<Movie> movieList = ImportMoviesFromLocalFile(pathToMoviesFile);
            if(index > movieList.size()){
                throw new WrongRequestException("The movie was not found!");
            }
            return Response.ok(averageOfScores(movieList.get(index).getScores())).build();
        }catch(WrongRequestException wre){
            return Response.ok(wre.getMessage()).build();
        }
        catch(Exception e){
            Log el = new Log();
            el.handleException(e);
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }
    /**
     * Egy adott film értékelését kiszolgáló függvény.
     * @param input az értékelni kívánt film sorszámát és értékelését tartalmazó objektum
     */
    @POST
    @Path("rateMovie")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response rateMovie(final PostParamBean input){
        try{
            if(!(input.score>=1 && input.score<=5)){
                throw new WrongRequestException("The score should be between 1 and 5!");
            }
            List<Movie> movieList = ImportMoviesFromLocalFile(pathToMoviesFile);
            if(input.index > movieList.size()){
                throw new WrongRequestException("The movie was not found!");
            }
            RatingService.rateMovie(movieList.get(input.index), input.score);
            exportMoviesToLocalFile(pathToMoviesFile, movieList);

            return Response.ok("The movie was rated successfully!").build();
        }catch(WrongRequestException wre){
            return Response.ok(wre.getMessage()).build();
        }
        catch(Exception e){
            Log el = new Log();
            el.handleException(e);
            System.err.println(e.toString());
            return Response.serverError().build();
        }
    }
}
