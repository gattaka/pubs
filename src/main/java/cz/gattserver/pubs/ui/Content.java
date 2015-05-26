package cz.gattserver.pubs.ui;

public class Content extends BaseLayout {

	private static final long serialVersionUID = 7303304927025814754L;

	protected MenuLayoutPage layoutPage;

	public void setContent(Content content) {
		layoutPage.setContent(content);
	}

	public Content(MenuLayoutPage layoutPage) {
		this.layoutPage = layoutPage;
		setMargin(true);
	}
}
