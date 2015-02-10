package cz.gattserver.pubs.ui;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

public class BeerContent extends Content {

	private static final long serialVersionUID = -2446097146634308270L;

	public static final String CONTENT_PATH = "beer";
	
	public BeerContent(LayoutPage layoutPage) {
		super(layoutPage);
		addComponent(new Label("<h1>TODO</h1>", ContentMode.HTML));

		Label separator = new Label();
		separator.setHeight("50px");
		addComponent(separator);
	}

}
