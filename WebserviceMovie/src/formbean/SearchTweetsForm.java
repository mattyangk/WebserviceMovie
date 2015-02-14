package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SearchTweetsForm extends FormBean {
	private String movie;
	
	public String getMovie() {
		return movie;
	}

	public void setMovie(String search) {
		this.movie = trimAndConvert(movie, "<>\"");
	}


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		return errors;
	}
}