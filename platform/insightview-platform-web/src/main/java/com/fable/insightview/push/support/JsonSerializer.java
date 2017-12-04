package com.fable.insightview.push.support;

import java.io.IOException;
import java.io.OutputStream;

import org.atmosphere.cpr.Serializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonSerializer implements Serializer{

	public void write(OutputStream out, Object message) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		out.write(mapper.writeValueAsString(message).getBytes("UTF-8"));
		out.close();
	}
}
