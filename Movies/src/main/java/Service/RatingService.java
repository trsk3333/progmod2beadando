/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import business.Movie;
import java.util.List;

/**
 *
 * @author Tulajdonos
 */
public class RatingService {
    public static void rateMovie(Movie movie, int score){
        movie.getScores().add(score);
    }
    public static int averageOfScores(List<Integer> scores){
        int sum = 0;
        for(int score : scores){
           sum += score; 
        }
        return sum/scores.size();
    }
}
