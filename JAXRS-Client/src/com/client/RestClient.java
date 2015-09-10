package com.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.net.ssl.HostnameVerifier;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.helpers.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;


public class RestClient {
	
	static {
	    //for localhost testing only
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost:444")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}

	public static void main(String[] args) throws Exception{
		
		//JsonNode jnode= getJsonNode();
		
		
		 HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		 DefaultHttpClient client = new DefaultHttpClient();

		 SchemeRegistry registry = new SchemeRegistry();
		 SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		 socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		 registry.register(new Scheme("https", socketFactory, 444));
		 SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
		 DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
		 
		 httpClient.getCredentialsProvider().setCredentials(new AuthScope("localhost", 444), new UsernamePasswordCredentials("Automation-User1@bbc.co.uk", "brusPA7e"));
		 javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		 HttpGet httpget1 = new HttpGet("https://localhost:444/rest/bpm/htm/v1/tasks/query?queryFilter=STATE%3DSTATE_READY+AND+WI.REASON%3DREASON_POTENTIAL_OWNER&queryTableName=EM3.MOVE_SPOOLS_TASK");

		 httpget1.addHeader("Accept", MediaType.APPLICATION_JSON);
		 httpget1.addHeader("Content-Type", MediaType.APPLICATION_JSON);
		 HttpResponse  response1 = httpClient.execute(httpget1);
		// httpClient.close();
		 //httpget1.completed();
		 HttpEntity res=response1.getEntity();
		 String jsonData = IOUtils.toString(res.getContent(), "UTF-8");
		 ObjectMapper mapper = new ObjectMapper();
		 JsonNode jsonNode = mapper.readValue(jsonData, JsonNode.class);
		
		final ArrayNode taskIdItemsNode = (ArrayNode) jsonNode.get("items");
		System.out.println(" size "+taskIdItemsNode.size());
		
		
		
		
		for(JsonNode taskIdItemNode : taskIdItemsNode) {
			 final String taskId = taskIdItemNode.get("TKIID").getTextValue();
			 String url="https://localhost:444/rest/bpm/htm/v1/task/"+taskId+"/input"; 
			 

			 client = new DefaultHttpClient();

			 registry = new SchemeRegistry();
			 socketFactory = SSLSocketFactory.getSocketFactory();
			 socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			 registry.register(new Scheme("https", socketFactory, 444));
			 mgr = new SingleClientConnManager(client.getParams(), registry);
			 httpClient = new DefaultHttpClient(mgr, client.getParams());
			 
			 httpClient.getCredentialsProvider().setCredentials(new AuthScope("localhost", 444), new UsernamePasswordCredentials("Automation-User1@bbc.co.uk", "brusPA7e"));
			 javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			 
			 
			 
			 HttpGet httpget = new HttpGet(url);

			 httpget.addHeader("Accept", MediaType.APPLICATION_XML);
			 httpget.addHeader("Content-Type", MediaType.APPLICATION_XML);
			 HttpResponse  response = httpClient.execute(httpget);
			 System.out.println(url + " "+response.getStatusLine().getStatusCode());
			 if(200==(response.getStatusLine().getStatusCode())){
				 HttpEntity obj=response.getEntity();
				 String body = IOUtils.toString(obj.getContent(), "UTF-8");
				// System.out.println(body);
			 }
			
		}
		System.out.println("Done");

	}
	

	}
