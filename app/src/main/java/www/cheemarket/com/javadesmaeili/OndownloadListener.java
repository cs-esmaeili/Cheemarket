package www.cheemarket.com.javadesmaeili;

import org.json.JSONException;

public  interface OndownloadListener {

    public void Oncompelet(String input) throws JSONException;

    public void onProgressDownload(int percent);
}