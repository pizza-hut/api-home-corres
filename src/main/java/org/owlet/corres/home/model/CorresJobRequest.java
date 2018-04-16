package org.owlet.corres.home.model;

import java.net.URL;

public class CorresJobRequest {

	private String sourceSystem;
	private String externalKey;
	private String corresId;
	private String siteId;
	private String dataFileContent;
	private String externalJobRef;
	private URL callbackUrl;
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getExternalKey() {
		return externalKey;
	}
	public void setExternalKey(String externalKey) {
		this.externalKey = externalKey;
	}
	public String getCorresId() {
		return corresId;
	}
	public void setCorresId(String corresId) {
		this.corresId = corresId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getDataFileContent() {
		return dataFileContent;
	}
	public void setDataFileContent(String dataFileContent) {
		this.dataFileContent = dataFileContent;
	}
	public URL getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(URL callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getExternalJobRef() {
		return externalJobRef;
	}
	public void setExternalJobRef(String externalJobRef) {
		this.externalJobRef = externalJobRef;
	}

}
