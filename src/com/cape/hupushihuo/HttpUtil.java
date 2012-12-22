package com.cape.hupushihuo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class HttpUtil {
	HttpEntity entity = null;
	InputStream inputStream = null;
	final HttpClient client = new DefaultHttpClient();
	FileOutputStream fos = null;
	// FlushedInputStream fls = null;
	Drawable drawable = null;
	HttpResponse response = null;
	HttpGet getRequest = null;

	public InputStream httpConn(final String url) {

		try {
			getRequest = new HttpGet(url);
			response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			entity = response.getEntity();

			if (entity != null) {
				// fos = new FileOutputStream(imageName);
				inputStream = entity.getContent();
				// return BitmapFactory.decodeStream(inputStream);
				// Bug on slow connections, fixed in future release.
				// int rt;
				// fls = new FlushedInputStream(inputStream);
				// byte[] b = new byte[1024];
				// while ((rt = fls.read(b)) != -1) {
				// fos.write(b, 0, rt);
				// fos.flush();
				// }
				// FileInputStream fis = new FileInputStream(file)
				// drawable = Drawable.createFromStream(fls, "src");
				// inputStream.close();
				// fls.close();
				// fos.flush();
				// fos.close();
				// inputStream.close();
				// fls.close();
				return inputStream;
			}
		} catch (IOException e) {
			getRequest.abort();
		} catch (IllegalStateException e) {
			getRequest.abort();
		}
		return null;

	}

	// static HttpRequestRetryHandler httpRequestRetryHandler = new
	// HttpRequestRetryHandler() {
	//
	// public boolean retryRequest(IOException exception, int executionCount,
	// HttpContext context) {
	// // TODO Auto-generated method stub
	// // 自定义的恢复策略
	//
	// if (executionCount >= 3) {
	// // Do not retry if over max retry count
	// return false;
	// }
	// if (exception instanceof NoHttpResponseException) {
	// // Retry if the server dropped connection on us
	// return true;
	// }
	// if (exception instanceof SSLHandshakeException) {
	// // Do not retry on SSL handshake exception
	// return false;
	// }
	// HttpRequest request = (HttpRequest) context
	// .getAttribute(ExecutionContext.HTTP_REQUEST);
	// Log.e("request", request.toString());
	// boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
	// if (!idempotent) {
	// // Retry if the request is considered idempotent
	// return true;
	// }
	// return false;
	// }
	// };

	// private static ResponseHandler<String> strResponseHandler = new
	// ResponseHandler<String>() {
	// // 自定义响应处理
	// public String handleResponse(HttpResponse response)
	// throws ClientProtocolException, IOException {
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// String charset = EntityUtils.getContentCharSet(entity) == null ?
	// CHARSET_UTF8
	// : EntityUtils.getContentCharSet(entity);
	// return new String(EntityUtils.toByteArray(entity), charset);
	// } else {
	// return null;
	// }
	// }
	// };

	/*
	 * An InputStream that skips the exact number of bytes provided, unless it
	 * reaches EOF.
	 */
	// private class FlushedInputStream extends FilterInputStream {
	// public FlushedInputStream(InputStream inputStream) {
	// super(inputStream);
	// }
	//
	// @Override
	// public long skip(long n) throws IOException {
	// long totalBytesSkipped = 0L;
	// while (totalBytesSkipped < n) {
	// long bytesSkipped = in.skip(n - totalBytesSkipped);
	// if (bytesSkipped == 0L) {
	// int b = read();
	// if (b < 0) {
	// break; // we reached EOF
	// } else {
	// bytesSkipped = 1; // we read one byte
	// }
	// }
	// totalBytesSkipped += bytesSkipped;
	// }
	// return totalBytesSkipped;
	// }
	// }
}
