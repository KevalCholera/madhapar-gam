//package com.madhapar.Util;
//
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.media.ExifInterface;
//import android.util.Log;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//
////import org.apache.http.HttpEntity;
////import org.apache.http.entity.mime.HttpMultipartMode;
////import org.apache.http.entity.mime.MultipartEntityBuilder;
////import org.apache.http.entity.mime.content.ContentBody;
////import org.apache.http.entity.mime.content.InputStreamBody;
//import org.apache.http.HttpEntity;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.reflect.Method;
//import java.util.Map;
//
//public class VolleyMultipartRequest extends Request<String> {
//
//	// private MultipartEntity entity = new MultipartEntity();
//
//	MultipartEntityBuilder entity = MultipartEntityBuilder.create();
//	HttpEntity httpentity;
//	String FILE_PART_NAME = "file";
//
//	private final Response.Listener<String> mListener;
//	private Response.ErrorListener mErrorListener;
//	// private final File mFilePart;
//	private final Map<String, String> mStringPart;
//	private Map<String, File> mByteparams;
//
//	public VolleyMultipartRequest(String url, Response.ErrorListener errorListener,
//								  Response.Listener<String> listener, Map<String, File> byteparams,
//								  Map<String, String> mStringPart, boolean isImage) {
//		super(java.lang.reflect.Method.POST, url, errorListener);
//
//		mErrorListener = errorListener;
//		mByteparams = byteparams;
//		mListener = listener;
//		/*
//		 * mFilePart = file; FILE_PART_NAME = file_key;
//		 */
//
//
//		this.mStringPart = mStringPart;
//		entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        try {
//            buildMultipartEntity(isImage);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        for(String  key : mStringPart.keySet()) {
//			Log.e("params", key + "=>" + mStringPart.get(key));
//		}
//	}
//
//	public void addStringBody(String param, String value) {
//
//		Log.e("Multipart Param",param +"=>"+value);
//		mStringPart.put(param, value);
//	}
//
//	private void buildMultipartEntity(boolean isImage) throws FileNotFoundException {
//		// entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
//		for (String key : mByteparams.keySet()) {
//
//            if(isImage) {
//                Matrix matrix = null;
//                ExifInterface exif1;
//                try {
//                    exif1 = new ExifInterface(mByteparams.get(key).getAbsolutePath());
//
//                    int orientation = exif1.getAttributeInt(
//                            ExifInterface.TAG_ORIENTATION, 1);
//                    matrix = new Matrix();
//                    if (orientation == 6) {
//                        matrix.postRotate(90);
//                    } else if (orientation == 3) {
//                        matrix.postRotate(180);
//                    } else if (orientation == 8) {
//                        matrix.postRotate(270);
//                    }
//
//
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//
//                BitmapFactory.Options option = new BitmapFactory.Options();
//                option.inSampleSize = 2;
//                Bitmap bmp = BitmapFactory.decodeFile(mByteparams.get(key)
//                        .getAbsolutePath(), option);
//
//                bmp = Bitmap.createBitmap(bmp, 0, 0,
//                        bmp.getWidth(), bmp.getHeight(), matrix,
//                        true); // rotating bitmap
//
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bmp.compress(CompressFormat.JPEG, 60, bos);
//                InputStream in = new ByteArrayInputStream(bos.toByteArray());
//                ContentBody foto = new InputStreamBody(in, "filename.jpg");
//                //ContentBody foto = new InputStreamBody(in, filename)
//                entity.addPart(key, foto);
//            }else{
//
//                Log.e("In File Upload", "In File Upload");
//                // Write File code here
//
//                FileInputStream fileInputStream=null;
//
////                File file = new File("C:\\testing.txt");
//
//                byte[] bFile = new byte[(int) mByteparams.get("materalfile").length()];
//
//                try {
//                    //convert file into array of bytes
//                    fileInputStream = new FileInputStream(mByteparams.get("materalfile"));
//                    fileInputStream.read(bFile);
//                    fileInputStream.close();
//
//                    for (int i = 0; i < bFile.length; i++) {
//                        System.out.print((char)bFile[i]);
//                    }
//
//                    System.out.println("Done");
//
//                    InputStream in = new ByteArrayInputStream(bFile);
//                    ContentBody foto = new InputStreamBody(in, mStringPart.get("file_name"));
//                    //ContentBody foto = new InputStreamBody(in, filename)
//                    entity.addPart(key, foto);
//
//                }catch(Exception e){
//                    e.printStackTrace();
//                }
//
//
//                /*FileInputStream fis = new FileInputStream(mByteparams.get("materalfile"));
//                //System.out.println(file.exists() + "!!");
//                //InputStream in = resource.openStream();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                byte[] buf = new byte[1024];
//                try {
//                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
//                        bos.write(buf, 0, readNum); //no doubt here is 0
//                        //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
//                        System.out.println("read " + readNum + " bytes,");
//                    }
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                    //Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                byte[] bytes = bos.toByteArray();
//
//                //below is the different part
//                File someFile = new File(mStringPart.get("file_name"));
//
//                try {
//                    FileOutputStream fos = new FileOutputStream(someFile);
//                    fos.write(bytes);
//                    fos.flush();
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }*/
//            }
//		}
//
//		for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
//			entity.addTextBody(entry.getKey(), entry.getValue());
//		}
//	}
//
//	@Override
//	public String getBodyContentType() {
//		return httpentity.getContentType().getValue();
//	}
//
//	@Override
//	public byte[] getBody() throws AuthFailureError {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			httpentity = entity.build();
//
//			httpentity.writeTo(bos);
//
//		} catch (IOException e) {
//			VolleyLog.e("IOException writing to ByteArrayOutputStream");
//		}
//		return bos.toByteArray();
//	}
//
//	@Override
//	protected Response<String> parseNetworkResponse(NetworkResponse response) {
//		return Response.success(new String(response.data), getCacheEntry());
//	}
//
//	@Override
//	protected void deliverResponse(String response) {
//		mListener.onResponse(response);
//	}
//
//
//	@Override
//	public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
//		// TODO Auto-generated method stub
//		retryPolicy = new DefaultRetryPolicy(30000, 0,
//				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//		return super.setRetryPolicy(retryPolicy);
//	}
//
//
//
//	@Override
//	public void deliverError(VolleyError error) {
//		// TODO Auto-generated method stub
//
//		Log.e("Deliver Error", "True");
//
//		mErrorListener.onErrorResponse(error);
//		super.parseNetworkError(error);
//	}
//
//
//	@Override
//	protected VolleyError parseNetworkError(VolleyError volleyError) {
//		if (volleyError.networkResponse != null
//				&& volleyError.networkResponse.data != null) {
//			Log.e("status Code", " code "
//					+ volleyError.networkResponse.statusCode);
//
//			try {
//				JSONObject jsonobject = new JSONObject(new String(
//						volleyError.networkResponse.data));
//
//				VolleyError error = new VolleyError(
//						jsonobject.optString("message"));
//				volleyError = error;
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		} else {
//
//			VolleyError error = new VolleyError(VolleySetup.ErrorMessage);
//			volleyError = error;
//		}
//
//		return volleyError;
//	}
//
//
//}