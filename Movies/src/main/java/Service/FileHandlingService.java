/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import business.Log;
import business.Movie;
import business.MovieGenreEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Tulajdonos
 */
public class FileHandlingService {
    
    public static List<Movie> ImportMoviesFromLocalFile(String path){
        try{
            List<Movie> movieList = new ArrayList<>();
            String jsonString = "";
            File jsonFile = new File(path);
            Scanner scn = new Scanner(jsonFile);
            
            while(scn.hasNextLine()){
                jsonString += scn.nextLine();
            }
            
            JSONObject obj = new JSONObject(jsonString);
            JSONArray movies = obj.getJSONArray("movies");
            for (int i = 0; i < movies.length(); i++)
            {
                String title = movies.getJSONObject(i).getString("Title");
                String director = movies.getJSONObject(i).getString("Director");
                LocalDate releaseDate = LocalDate.parse(movies.getJSONObject(i).getString("ReleaseDate"));
                MovieGenreEnum primaryGenre = MovieGenreEnum.valueOf(movies.getJSONObject(i).getString("PrimaryGenre"));
                JSONArray ratings = movies.getJSONObject(i).getJSONArray("Scores");
                List<Integer> scores = new ArrayList<>();
                for(int k = 0; k<ratings.length(); k++){
                    scores.add(ratings.getInt(k));
                }
                
                movieList.add(new Movie(title, director, releaseDate, primaryGenre, scores));
            }
            return movieList;
        }
        catch(IllegalArgumentException iae){
            Log el = new Log();
            el.handleException(iae);
            System.err.println(iae.toString());
        }
        catch(FileNotFoundException fnfe){
            Log el = new Log();
            el.handleException(fnfe);
            System.err.println(fnfe.toString());
        }
        return null;
    }
    public static void exportMoviesToLocalFile(String path, List<Movie> movieList){
        JSONObject jObj = new JSONObject();
        JSONArray jArray = new JSONArray();
        for(int i = 0; i < movieList.size(); i++){
            JSONObject jMovie = new JSONObject();
            jMovie.put("Title", movieList.get(i).getTitle());
            jMovie.put("Director", movieList.get(i).getDirector());
            jMovie.put("ReleaseDate", movieList.get(i).getReleaseDate().toString());
            jMovie.put("PrimaryGenre", movieList.get(i).getPrimaryGenre().toString());
            JSONArray scores = new JSONArray();
            for(int score : movieList.get(i).getScores()){
                scores.put(score);
            }
            jMovie.put("Scores", scores);
            
            jArray.put(jMovie);
        }
        jObj.put("movies", jArray);
        
        try{
            FileWriter fileWriter = new FileWriter(path);
            System.out.println(jObj.toString());
            fileWriter.write(jObj.toString());
            fileWriter.close();
        } catch (IOException ex) {
            Log el = new Log();
            el.handleException(ex);
            System.err.println(ex.toString());
        }
    }
    
}
