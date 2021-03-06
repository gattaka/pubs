package cz.gattserver.pubs.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class LoginContent extends BaseLayout {

	private static final long serialVersionUID = -3949087798185114415L;

	public static final String PATH = "login";
	
	public LoginContent() {
		setSpacing(true);
		setMargin(true);

		addStyleName("layout-box");
		setWidth("1000px");

		addComponent(new Label(
				"<form name='f' autocomplete='on' action='j_spring_security_check' method='POST'>" + "<table>" + "<tr>"
						+ "<td>Jméno:</td>" + "<td><input type='text' name='j_username' value=''></td>" + "</tr>"
						+ "<tr>" + "<td>Heslo:</td>" + "<td><input type='password' name='j_password' /></td>" + "</tr>"
						+ "<tr>" + "<td>Zapamatovat:</td>"
						+ "<td><input type='checkbox' name='_spring_security_remember_me' /></td>" + "</tr>" + "<tr>"
						+ "<td><input name='submit' type='submit' value='Přihlásit' /></td>" + "</tr>" + "</table>"
						+ "</form>", ContentMode.HTML));

	}
}
