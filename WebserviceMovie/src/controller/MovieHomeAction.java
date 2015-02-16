package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;


import bean.DisplayBean;
import bean.MovieBean;
import bean.MovieTweetBean;
import bean.TweetBean;
import model.Model;
import model.TMDBRetriever;
import model.Twitter;
import model.TwitterRetriever;
import model.testFlicker2;

public class MovieHomeAction extends Action {
	
	TMDBRetriever tmdbRetriever;
	TwitterRetriever tweetRetriever;
	
	public MovieHomeAction(Model model) {
		tmdbRetriever = model.getTMDBRetriever();
		tweetRetriever = model.getTwitterRetriever();
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
		
		//MovieTweetBean[] tweets = tweetRetriever.getTweetByMovieName(request,movie.getTitle());
		List<DisplayBean> flickers=new testFlicker2(movie.getTitle()).getALL();
		request.setAttribute("display",flickers);
		request.setAttribute("movie", movie);
		
		return "movieHome.jsp";
	}

}
