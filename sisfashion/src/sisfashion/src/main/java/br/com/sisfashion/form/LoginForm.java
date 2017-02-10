package br.com.sisfashion.form;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
