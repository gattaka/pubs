package cz.gattserver.pubs.subwindows;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;

import cz.gattserver.pubs.facades.CommentFacade;
import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.web.common.window.ConfirmWindow;
import cz.gattserver.web.common.window.ErrorWindow;
import cz.gattserver.web.common.window.WebWindow;

public class CreatePubCommentWindow extends WebWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private CommentFacade commentFacade;

	public CreatePubCommentWindow(final PubDTO p) {
		super("Vytvořit komentář k hospodě: " + p.getName());

		BeanFieldGroup<CommentDTO> beanFieldGroup = new BeanFieldGroup<CommentDTO>(CommentDTO.class);
		beanFieldGroup.setItemDataSource(new CommentDTO());

		final TextArea commentField = new TextArea("Komentář");
		commentField.setNullRepresentation("");
		commentField.setWidth("400px");
		commentField.setHeight("100px");
		beanFieldGroup.bind(commentField, "text");
		addComponent(commentField);

		Button createBtn = new Button("Přidat", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					beanFieldGroup.commit();

					if (StringUtils.isBlank(commentField.getValue())) {
						getUI().addWindow(new ErrorWindow("Komentář nesmí být prázdný"));
						return;
					}

					Long id = commentFacade.createComment(p, beanFieldGroup.getItemDataSource().getBean());
					onCreation(id);
					getUI().removeWindow(CreatePubCommentWindow.this);
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
		addComponent(createBtn);
		setComponentAlignment(createBtn, Alignment.BOTTOM_RIGHT);

	}

	protected void onCreation(Long id) {
	}

	@Override
	public void close() {
		getUI().addWindow(new ConfirmWindow("Opravdu zrušit přidání nového komentáře?") {
			private static final long serialVersionUID = -1263084559774811237L;

			@Override
			protected void onConfirm(ClickEvent event) {
				CreatePubCommentWindow.super.close();
			}
		});
	}

}
