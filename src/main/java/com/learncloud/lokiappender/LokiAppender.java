package com.learncloud.lokiappender;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "LokiAppender", category = "Core", elementType = "appender", printObject = true)
public class LokiAppender extends AbstractAppender {

	private String url;
	private boolean auth;
	private String username;
	private String password;
	private String label;

	public LokiAppender(String name, Layout<?> layout, String url, boolean auth, String username, String password,
			String label) {
		super(name, null, layout, false);
		this.url = url;
		this.auth = auth;
		this.username = username;
		this.password = password;
		this.label = label;
	}

	@PluginFactory
	public static LokiAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("url") String url,
			@PluginAttribute("auth") boolean auth, @PluginAttribute("username") String username,
			@PluginAttribute("password") String password, @PluginAttribute("Lokilabel") String label) {
		return new LokiAppender(name, null, url, auth, username, password, label);
	}

	@Override
	public void append(LogEvent event) {
		String logMessage = event.getMessage().getFormattedMessage();
		String postData = buildPostData(logMessage, label);
		AsyncPostCall.sendpostdata(url, auth, username, password, postData);
	}

	public String buildPostData(String logMessage, String labelsConverted) {

		String lokiPostData = "{\"streams\": [{\"stream\": { \"Lokilabel\": \"" + labelsConverted
				+ "\"}, \"values\": [[\"" + getepochtime() + "\", \"" + logMessage + "\"]]}]}";
		System.out.println("jsondata:" + lokiPostData);
		return lokiPostData;

	}

	public long getepochtime() {
		long currentTimeMillis = System.currentTimeMillis(); // Time in milliseconds since Unix epoch
		long nanoTime = System.nanoTime(); // Time in nanoseconds since some starting point

		long nanoTimeSinceUnixEpoch = (currentTimeMillis * 1_000_000) + (nanoTime % 1_000_000);
		return nanoTimeSinceUnixEpoch;
	}
}
