package cz.gattserver.web.common.window;

import com.vaadin.server.ThemeResource;

public class InfoWindow extends MessageWindow {

	private static final long serialVersionUID = -4793025663820815400L;

	public InfoWindow(String labelCaption) {
		super("Info", labelCaption, new ThemeResource("img/tags/info_16.png"));
	}

}
