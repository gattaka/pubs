package cz.gattserver.pubs.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class HomeContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String CONTENT_PATH = "home";
	
	public HomeContent(LayoutPage layoutPage) {
		super(layoutPage);
		addComponent(new Label("<h1>Ahoj!</h1>", ContentMode.HTML));
		addComponent(new Label(
				"Je to trochu overkill, ale \"hec\" je \"hec\" ;) -- tady máte vaší <strong>Znalostní bázi... hospod</strong>",
				ContentMode.HTML));

		addComponent(new Label("<strong>Příští úterek zařizuje:</strong> -nameless-one-", ContentMode.HTML));
		addComponent(new Label("<strong>Příští úterek se koná:</strong> -jmeno_hospody-", ContentMode.HTML));

		Label separator = new Label();
		separator.setHeight("50px");
		addComponent(separator);
	}

}
