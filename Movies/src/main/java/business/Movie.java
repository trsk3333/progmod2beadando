/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tulajdonos
 */
public class Movie {
    private String title;
    private String director;
    private LocalDate releaseDate;
    private MovieGenreEnum primaryGenre;
    private List<Integer> scores = new ArrayList<>();

    public Movie(String title, String director, LocalDate releaseDate, MovieGenreEnum primaryGenre, List<Integer> scores) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
        this.primaryGenre = primaryGenre;
        this.scores = scores;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieGenreEnum getPrimaryGenre() {
        return primaryGenre;
    }

    public void setPrimaryGenre(MovieGenreEnum primaryGenre) {
        this.primaryGenre = primaryGenre;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }
    
    
    
}
