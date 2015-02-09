

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import bean.CustomerBean;
import formbean.LoginForm;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested favorite.  If there was no favorite originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his favorites.
 */
public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory
			.getInstance(LoginForm.class);

	private CustomerDAO customerDAO;


	public LoginAction(Model model) {
		customerDAO = model.getCustomerDAO();
		
	}

	public String getName() {
		return "index.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			HttpSession session = request.getSession();

			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "index.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "index.jsp";
			}

			// Look up the user
			// System.out.println(form.getUsername());
			// System.out.println(form.getPassword());
			CustomerBean customer = customerDAO.getCustomerByUsername(form
					.getUsername());


			if (customer == null) {
				errors.add("User Name or password is not correct");
				return "index.jsp";
			}

			// Check the password
			if (customer != null) {
				if (customer.getPassword().equals(form.getPassword())) {
					//System.out.println("adding customer to session" + customer.getUsername());
					session.setAttribute("customer", customer);
					return "viewAccount.do";
				} else {
					errors.add("User Name or password is not correct");
					return "index.jsp";
				}
			}

			

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "index.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "index.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "index.jsp";
		}
		
		return "index.jsp";
	}
}
