package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.MovieBean;
import model.Model;
import model.TMDBRetriever;

public class GetMoviesAction extends Action{
	
	TMDBRetriever tmdbRetriever;

	@Override
	public String getName() {
		return "index.do";
	}
	
	public GetMoviesAction(Model model) {
		tmdbRetriever = model.getTMDBRetriever();
	}

	@Override
	public String perform(HttpServletRequest request) {
		
//		List<MovieBean> movieList;
//		try {
//			movieList = tmdbRetriever.getPopularMovies();
//			request.setAttribute("movieList", movieList);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
				
		return "index.jsp";
	}

}
