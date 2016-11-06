package com.madhapar.Util;

import android.util.Log;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by smartsense on 23/10/16.
 */
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;


public class MultiPartRequest extends StringRequest {

    /* To hold the parameter name and the File to upload */
    private Map<String, File> fileUploads = new HashMap<String, File>();

    /* To hold the parameter name and the string content to upload */
    private Map<String, String> stringUploads = new HashMap<String, String>();

    private Map<String, String> headers = new HashMap<String, String>();
    private File file;

    /**
     * Creates a new request.
     *
     * @param method        the HTTP method to use
     * @param url           URL to fetch the JSON from
     * @param jsonRequest   A {@link JSONObject} to post with the request. Null is allowed and
     *                      indicates no parameters will be posted along with request.
     * @param listener      Listener to receive the JSON response
     * @param errorListener Error listener, or null to ignore errors.
     */
    public MultiPartRequest(int method, String url, JSONObject jsonRequest,
                            Listener<String> listener, ErrorListener errorListener) {

        super(method, url, listener,
                errorListener);

    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MultipartEntity multipartEntity = new MultipartEntity();
        multipartEntity.addPart("userProfilePicture", new FileBody(file));

        try {
            bos.write(multipartEntity.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }


    public MultiPartRequest(String url, JSONObject jsonRequest, Listener<String> listener,
                            ErrorListener errorListener, File file) {
        this(jsonRequest == null ? Method.PUT : Method.POST, url, jsonRequest,
                listener, errorListener);
        this.file = file;
    }


    public void addFileUpload(String param, File file) {
        fileUploads.put(param, file);
    }

    public void addStringUpload(String param, String content) {
        stringUploads.put(param, content);
    }

    public Map<String, File> getFileUploads() {

        return fileUploads;
    }

    public Map<String, String> getStringUploads() {
        return stringUploads;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        return headers;
    }

    public void setHeader(String title, String content) {
        headers.put(title, content);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {

            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new String(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }
}
