package cz.gattserver.pubs.ui;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.PubsUI;
import cz.gattserver.pubs.SpringContextHelper;

public class Content extends VerticalLayout {

	private static final long serialVersionUID = 7303304927025814754L;

	protected LayoutPage layoutPage;

	public void setContent(Content content) {
		layoutPage.setContent(content);
	}

	public Content(LayoutPage layoutPage) {
		SpringContextHelper.inject(this);
		this.layoutPage = layoutPage;
		setMargin(true);
		setSpacing(true);
	}

	public PubsUI getPubsUI() {
		return (PubsUI) UI.getCurrent();
	}
}
