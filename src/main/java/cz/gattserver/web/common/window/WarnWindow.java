package cz.gattserver.web.common.window;

import com.vaadin.server.ThemeResource;

public class WarnWindow extends MessageWindow {

	private static final long serialVersionUID = -4793025663820815400L;

	public WarnWindow(String labelCaption) {
		super("Varování", labelCaption, new ThemeResource(
				"img/tags/warning_16.png"));
	}

}
