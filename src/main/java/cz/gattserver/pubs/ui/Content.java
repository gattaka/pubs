package cz.gattserver.pubs.ui;

public class Content extends BaseLayout {

	private static final long serialVersionUID = 7303304927025814754L;

	protected LayoutPage layoutPage;

	public void setContent(Content content) {
		layoutPage.setContent(content);
	}

	public Content(LayoutPage layoutPage) {
		this.layoutPage = layoutPage;
		setMargin(true);
	}
}
