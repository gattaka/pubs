package cz.gattserver.pubs.ui;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.PubsUI;
import cz.gattserver.web.common.ui.SpringContextHelper;

public class BaseLayout extends VerticalLayout {

	private static final long serialVersionUID = 3174843052985816547L;

	public BaseLayout() {
		SpringContextHelper.inject(this);
		setSpacing(true);
	}

	public PubsUI getPubsUI() {
		return (PubsUI) UI.getCurrent();
	}

}
