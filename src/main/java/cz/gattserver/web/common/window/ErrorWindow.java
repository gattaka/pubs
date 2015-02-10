package cz.gattserver.web.common.window;

import com.vaadin.server.ThemeResource;

public class ErrorWindow extends MessageWindow {

	private static final long serialVersionUID = -4793025663820815400L;

	public ErrorWindow(String labelCaption) {
		super("Problém", labelCaption,
				new ThemeResource("img/tags/delete_16.png"));
	}

}
