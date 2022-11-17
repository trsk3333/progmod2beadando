/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import business.Movie;
import java.util.List;

/**
 *
 * @author Tamás
 */
public class RatingService{
    /**
     * Hozzáadja a {@link score} -t a megadott {@link Movie} példányhoz
     * @param movie az értékelni kívánt film
     * @param score az értékelés
     */
    public static void rateMovie(Movie movie, int score){
        movie.getScores().add(score);
    }
    /**
     * Kiszámolja az pontszámok átlagát egészre kerekítve.
     * @param scores a pontszámokat tartalmazó {@link List}
     * @return pontszámok átlaga
     */
    public static int averageOfScores(List<Integer> scores){
        int sum = 0;
        for(int score : scores){
           sum += score; 
        }
        return sum/scores.size();
    }
}
