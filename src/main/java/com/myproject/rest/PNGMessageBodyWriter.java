package com.myproject.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import com.myproject.Image;

public class PNGMessageBodyWriter {// implements MessageBodyWriter<Image> {
	/*


	@Override
	public boolean isWriteable(java.lang.Class<?> type,
            java.lang.reflect.Type genericType,
            java.lang.annotation.Annotation[] annotations,
            MediaType mediaType) {
		 return mediaType.toString.equals("image/png");
	}

	@Override
	public void writeTo(Image instance, java.lang.Class<?> type, java.lang.reflect.Type genericType, java.lang.annotation.Annotation[] annotations, 
		    MediaType mediaType, MultivaluedMap<java.lang.String,java.lang.Object> httpHeaders, java.io.OutputStream entityStream)
			throws IOException, WebApplicationException {
		    JPEGTranscoder t = new JPEGTranscoder();
		    t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
		    InputStream is = new ByteArrayInputStream(instance.getBytes());
		    TranscoderInput input = new TranscoderInput(is);
		    TranscoderOutput output = new TranscoderOutput(entityStream);
		    t.transcode(input, output);
		    httpHeaders.put("Content-Disposition", ""attachment;filename=" + instance.getFilename());
		    // and so on....
		    httpHeaders.put("whatever else", "some other value");
		
	}

	@Override
	public long getSize(Image arg0, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4) {
		// TODO Auto-generated method stub
		return 0;
	}

*/}
