package cz.gattserver.pubs.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Layout;

import cz.gattserver.web.common.WebRequest;

/**
 * Předek pro všechny stránky, které mají možnost v sobě obměňovat obsah
 */
public abstract class LayoutPage extends BaseLayout {

	private static final long serialVersionUID = -2049981787052398061L;

	protected WebRequest webRequest;

	private Layout currentContent;

	public WebRequest getWebRequest() {
		return webRequest;
	}

	public LayoutPage(WebRequest request) {
		this.webRequest = request;
	}

	public void setContent(Layout content) {
		if (currentContent != null)
			removeComponent(currentContent);
		if (content != null) {
			currentContent = content;
			addComponent(currentContent);
			setComponentAlignment(currentContent, Alignment.TOP_LEFT);
			setExpandRatio(currentContent, 1);
		}
	}

}
