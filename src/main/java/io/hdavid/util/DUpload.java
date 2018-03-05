package io.hdavid.util;

import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class DUpload extends Upload {
	private String fileName;
	private String mimeType;
	private byte[] bytes;
	private ByteArrayOutputStream baos;
	public final String originalCaption;
	
	public String getFileName() { return fileName; }
	public String getMimeType() { return mimeType; }
	public byte[] getBytes() { return bytes; }
	
	private void setNewCaption(String filename) {
		setCaption(originalCaption+ "["+filename+"]");
	}
	public DUpload(String caption) {
		this(caption, (file, mime, content) -> { });
	}
	public DUpload(String caption, final UploadFinishedListener ufl) {
		super();
		
		setImmediateMode(true);
		setCaption(caption);
		originalCaption = caption == null ? "" : caption;
		addStyleName(ValoTheme.BUTTON_SMALL);
		
		setReceiver(new Receiver() {
			@Override
			public OutputStream receiveUpload(String filename, String mimeType) {
				DUpload.this.fileName = filename;
				DUpload.this.mimeType = mimeType;
				DUpload.this.baos = new ByteArrayOutputStream();
				return baos;
			}
		});
		
		setReceiver((fileName, mimeType) -> {
				DUpload.this.fileName = fileName;
				DUpload.this.mimeType = mimeType;
				DUpload.this.baos = new ByteArrayOutputStream();
				return baos;
			} );
		 
		addSucceededListener(e -> { 
			bytes = baos.toByteArray();
			ufl.uploadFinished(fileName, mimeType, bytes);
			setNewCaption(fileName);
			try {
				baos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		addFailedListener(e -> { 
			fileName = null;
			mimeType = null;
			bytes = null;
			
			ufl.uploadFinished(null, null, null);
			setNewCaption("error");
			try {
				baos.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
	}
	
	public interface UploadFinishedListener {
		void uploadFinished(String filename, String mime, byte[] content);
	}
}
