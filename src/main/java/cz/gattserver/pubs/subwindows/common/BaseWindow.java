package cz.gattserver.pubs.subwindows.common;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import cz.gattserver.pubs.SpringContextHelper;

public class BaseWindow extends Window {

	private static final long serialVersionUID = -9184044674542039306L;

	private VerticalLayout layout = new VerticalLayout();

	public BaseWindow(String name) {
		super(name);
		SpringContextHelper.inject(this);

		setContent(layout);

		layout.setSpacing(true);
		layout.setMargin(true);

		addAction(new Window.CloseShortcut(this, KeyCode.ESCAPE));

		center();

		addAttachListener(new AttachListener() {
			private static final long serialVersionUID = -2969249056636674086L;

			@Override
			public void attach(AttachEvent event) {
				focus();
			}
		});

	}

	protected void addComponent(Component component) {
		layout.addComponent(component);
	}

	protected void setComponentAlignment(Component component, Alignment alignment) {
		layout.setComponentAlignment(component, alignment);
	}
}
