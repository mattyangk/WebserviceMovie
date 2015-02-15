package controller;

import javax.servlet.http.HttpServletRequest;

import bean.MovieBean;
import model.Model;
import model.TMDBRetriever;

public class MovieHomeAction extends Action {
	
	TMDBRetriever tmdbRetriever;
	
	public MovieHomeAction(Model model) {
		tmdbRetriever = model.getTMDBRetriever();
	}

	@Override
	public String getName() {
		return "movieHome.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		String movieId = (String) request.getParameter("movieId");
		
		System.out.println("id :" + movieId);
		
		MovieBean movie = tmdbRetriever.getMovieById(movieId);
		
		request.setAttribute("movie", movie);
		
		return "movieHome.jsp";
	}

}
