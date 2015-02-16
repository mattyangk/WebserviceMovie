package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import bean.MovieBean;
import model.Model;
import model.TMDBRetriever;

public class SearchAction extends Action {
	
	
	TMDBRetriever tmdbRetriever;
	
	public SearchAction(Model model) {
		tmdbRetriever = model.getTMDBRetriever();
	}
	
	
	@Override
	public String getName() {
		
		return "search.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		String movieName = request.getParameter("movie_name");
		movieName = movieName.replace(" ", "%20");
		List<MovieBean> movieList = tmdbRetriever.getMoviesByKeyword(movieName);
		
		request.setAttribute("movieList", movieList);
		
		return "index.jsp";
		
	}


}
