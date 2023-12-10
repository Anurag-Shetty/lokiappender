package com.learncloud.lokiappender;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

public class AsyncPostCall {

	public static void sendpostdata(String url, boolean auth, String username, String password, String postData) {

		WebClient webClient = WebClient.builder()
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + encodeBasicAuth(username, password))
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build())
				.build();

		webClient.post().uri(url).body(BodyInserters.fromValue(postData)).retrieve().bodyToMono(String.class)
				.doOnError(Throwable::printStackTrace).subscribe(responseBody -> {
					// Handle the response body here
					System.out.println("Response body: " + responseBody);
				});
	}

	private static String encodeBasicAuth(String username, String password) {
		String credentials = username + ":" + password;
		return java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
	}
}
