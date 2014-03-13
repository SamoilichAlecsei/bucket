package com.itmg.bucket.localization;

import com.itmg.bucket.handler.BaseResponseHandler;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LocalizationBundleHandler extends BaseResponseHandler implements ResponseHandler<LocalizationBundle>{

	public LocalizationBundleHandler(String jsonArguments) {
		super(jsonArguments);
	}
	
	public LocalizationBundleHandler() {
		super();
	}
	
	  @Override
	    public  LocalizationBundle handleResponse(final HttpResponse response) throws IOException {
	        verifyResponse(response);
	        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
	        try {
				JSONObject json = new JSONObject(result);
				return new LocalizationBundle(getCustomDataName(), readJsonContent(json));
			} catch (JSONException e) {
				System.out.println(String.format("Failed to parse JSON response for country=%s", getCustomDataName()));
			}     
	        return null;
	  }
	  
	  private Map<String, String> readJsonContent(JSONObject json) throws JSONException {
			Iterator<String> it = json.keys();
			Map<String, String> data = new HashMap<String, String>();
			while (it.hasNext()) {
				String key = it.next();
				String value = (String) json.get(key);
				data.put(key, value);
			}
			return data;
		}
}
