package com.hcmc100.mod;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpExecutor {

	public static final String API = "https://api.hcmc100.com/";
	private final static ExecutorService executor = Executors.newFixedThreadPool(2);

	private static void queue(Runnable runnable) {
		executor.submit(runnable);
	}

	public static void post(String url) {
		post(url, new byte[0]);
	}

	public static void post(String url, byte[] data) {
		post(url, data, null);
	}

	public static void post(String url, byte[] data, Runnable complete) {
		queue(() -> {
			try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpPost request = new HttpPost(API + url);
				request.setEntity(new ByteArrayEntity(data, ContentType.APPLICATION_JSON));

				HttpResponse response = client.execute(request);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && complete != null){
					complete.run();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
