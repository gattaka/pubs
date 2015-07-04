package cz.gattserver.pubs.subwindows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tokenfield.TokenField;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.util.ImageUtils;
import cz.gattserver.web.common.ui.MultiUpload;
import cz.gattserver.web.common.window.ConfirmWindow;
import cz.gattserver.web.common.window.ErrorWindow;
import cz.gattserver.web.common.window.WebWindow;

public abstract class CreatePubWindow extends WebWindow {

	private static final long serialVersionUID = -5681365970486437642L;

	@Autowired
	private PubFacade pubFacade;

	private TokenField tags;
	private MultiUpload upload;
	private Embedded pubImage;

	protected abstract Long savePub(PubDTO pubToSave, Collection<String> tags);

	protected abstract String getStornoCreationCaption();

	public interface OnCreationListener {
		public void onCreation(Long id);
	}

	@SuppressWarnings("unchecked")
	private Collection<String> getTags() {
		return (Collection<String>) tags.getValue();
	}

	private void createUpload(VerticalLayout layout, PubDTO p) {
		if (upload != null)
			layout.removeComponent(upload);

		upload = new MultiUpload(false) {
			private static final long serialVersionUID = 8620441233254076257L;

			@Override
			protected void handleFile(InputStream in, String fileName, String mimeType, long length) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				// vytvoř miniaturu
				try {
					ImageUtils.resizeImageFile(fileName, in, bos, 256, 256);
				} catch (IOException e) {
					e.printStackTrace();
				}
				p.setImage(bos.toByteArray());
				refreshImage(pubImage, p);
			}
		};
		upload.getMultiFileUpload().setMaxFileSize(2000000);
		upload.getMultiFileUpload().setAcceptedMimeTypes(Arrays.asList(new String[] { "image/jpg", "image/jpeg" }));
		upload.getMultiFileUpload().setUploadButtonCaptions("Nahrát foto", "Nahrát foto");
		upload.setImmediate(true);
		layout.addComponent(upload);
	}

	public CreatePubWindow(PubDTO p, OnCreationListener onCreationListener) {
		super("Hospoda");

		setWidth("600px");

		BeanFieldGroup<PubDTO> beanFieldGroup = new BeanFieldGroup<PubDTO>(PubDTO.class);
		beanFieldGroup.setItemDataSource(p);

		HorizontalLayout detailsAndFotoLayout = new HorizontalLayout();
		detailsAndFotoLayout.setSpacing(true);
		detailsAndFotoLayout.setWidth("100%");
		addComponent(detailsAndFotoLayout);

		VerticalLayout detailsLayout = new VerticalLayout();
		detailsLayout.setSpacing(true);
		detailsAndFotoLayout.addComponent(detailsLayout);
		detailsAndFotoLayout.setExpandRatio(detailsLayout, 1);

		TextField nameField = new TextField("Název");
		nameField.setNullRepresentation("");
		nameField.setWidth("100%");
		beanFieldGroup.bind(nameField, "name");
		detailsLayout.addComponent(nameField);

		TextField addressField = new TextField("Adresa");
		addressField.setNullRepresentation("");
		addressField.setWidth("100%");
		beanFieldGroup.bind(addressField, "address");
		detailsLayout.addComponent(addressField);

		TextField webAddressField = new TextField("Webová adresa");
		webAddressField.setNullRepresentation("");
		webAddressField.setWidth("100%");
		beanFieldGroup.bind(webAddressField, "webAddress");
		detailsLayout.addComponent(webAddressField);

		createUpload(detailsLayout, p);

		pubImage = new Embedded(null, null);
		refreshImage(pubImage, p);
		// pubImage.setWidth("256px");
		pubImage.setImmediate(true);
		detailsAndFotoLayout.addComponent(pubImage);

		// TextArea descriptionField = new TextArea("Popis");
		// descriptionField.setWidth("100%");
		// descriptionField.setHeight("100px");
		// descriptionField.setNullRepresentation("");
		// beanFieldGroup.bind(descriptionField, "description");
		// addComponent(descriptionField);

		// inicializace možností
		List<PubTagDTO> tagsOptions = pubFacade.getPubTagsForOverview();
		BeanContainer<String, PubTagDTO> tokens = new BeanContainer<String, PubTagDTO>(PubTagDTO.class);
		tokens.setBeanIdProperty("name");
		tokens.addAll(tagsOptions);

		tags = new TokenField();
		tags.setCaption("Štítky");
		tags.setStyleName(TokenField.STYLE_TOKENFIELD);
		tags.setContainerDataSource(tokens);
		tags.setFilteringMode(FilteringMode.CONTAINS); // suggest
		tags.setTokenCaptionPropertyId("name");
		tags.setInputPrompt("pití nebo jídlo");
		tags.setRememberNewTokens(false);
		tags.isEnabled();
		addComponent(tags);

		if (p.getTags() != null)
			for (PubTagDTO tagDTO : p.getTags()) {
				tags.addToken(tagDTO.getName());
			}

		Button createBtn = new Button("Uložit", new Button.ClickListener() {
			private static final long serialVersionUID = 2071604101486581247L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					beanFieldGroup.commit();

					if (StringUtils.isBlank(nameField.getValue())) {
						getUI().addWindow(new ErrorWindow("Název nesmí být prázdný"));
						return;
					}

					if (StringUtils.isBlank(addressField.getValue())) {
						getUI().addWindow(new ErrorWindow("Adresa nesmí být prázdná"));
						return;
					}

					PubDTO pubToSave = beanFieldGroup.getItemDataSource().getBean();

					Long id = savePub(pubToSave, getTags());
					onCreationListener.onCreation(id);
					getUI().removeWindow(CreatePubWindow.this);
				} catch (CommitException e) {
					e.printStackTrace();
				}
			}
		});
		addComponent(createBtn);
		setComponentAlignment(createBtn, Alignment.BOTTOM_RIGHT);

	}

	private void refreshImage(Embedded pubImage, PubDTO p) {
		if (p.getImage() == null) {
			pubImage.setSource(new ThemeResource("img/no_foto.png"));
		} else {
			pubImage.setSource(new StreamResource(new StreamSource() {
				private static final long serialVersionUID = 414452413648278444L;

				@Override
				public InputStream getStream() {
					return new ByteArrayInputStream(p.getImage());
				}
			}, p.getName() + ".jpg"));
		}
	}

	@Override
	public void close() {
		getUI().addWindow(new ConfirmWindow(getStornoCreationCaption()) {
			private static final long serialVersionUID = -1263084559774811237L;

			@Override
			protected void onConfirm(ClickEvent event) {
				CreatePubWindow.super.close();
			}
		});
	}

}
