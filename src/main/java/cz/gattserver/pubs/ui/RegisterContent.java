package cz.gattserver.pubs.ui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;

import cz.gattserver.pubs.facades.UserFacade;
import cz.gattserver.web.common.window.ErrorWindow;
import cz.gattserver.web.common.window.InfoWindow;

public class RegisterContent extends Content {

	private static final long serialVersionUID = -3949087798185114415L;

	public static final String PATH = "hidden_registration_page";

	@Autowired
	private UserFacade userFacade;

	public RegisterContent(MenuLayoutPage layoutPage) {
		super(layoutPage);
		setSpacing(true);
		setMargin(true);

		TextField nameField = new TextField("Uživatelské jméno");
		PasswordField passwordField = new PasswordField("Heslo");
		PasswordField passwordField2 = new PasswordField("Heslo podruhé");

		addComponent(nameField);
		addComponent(passwordField);
		addComponent(passwordField2);

		Button saveBtn = new Button("Uložit", new Button.ClickListener() {
			private static final long serialVersionUID = -3883343949727536661L;

			@Override
			public void buttonClick(ClickEvent event) {

				if (StringUtils.isBlank(nameField.getValue())) {
					getUI().addWindow(new ErrorWindow("Jméno nesmí být prázdné"));
					return;
				}

				if (userFacade.getUser(nameField.getValue()) != null) {
					getUI().addWindow(new ErrorWindow("Uživatel s tímto jménem už existuje"));
					return;
				}

				if (StringUtils.isBlank(passwordField.getValue())) {
					getUI().addWindow(new ErrorWindow("Heslo nesmí být prázdné"));
					return;
				}

				if (passwordField.getValue().equals(passwordField2.getValue()) == false) {
					getUI().addWindow(new ErrorWindow("Hesla se neshodují"));
					return;
				}

				userFacade.registrateNewUser("", nameField.getValue(), passwordField.getValue());

				getUI().addWindow(new InfoWindow("Registrace se zdařila") {
					private static final long serialVersionUID = 7178922282787936031L;

					{
						setModal(true);
					}

					@Override
					public void close() {
						layoutPage.getWebRequest().redirectToPage(DispatcherPage.PATH);
						super.close();
					}
				});

			}
		});
		addComponent(saveBtn);

	}
}
