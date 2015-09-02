package com.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.wink.common.model.multipart.InMultiPart;
import org.apache.wink.common.model.multipart.InPart;

import com.model.InputVO;
import com.model.OutputVO;
@Path("/simpleservice")
public class SimpleServiceImpl implements SimpleService{
	@Override
	public Response uploadFile(InMultiPart inMP){
		String fileName=null;
		try{
		while (inMP.hasNext()) {
			
	        InPart part = inMP.next();
	        MultivaluedMap<String, String> heades = part.getHeaders();
	       
	        String CDHeader = heades.getFirst("Content-Disposition");
	        String[] headers=CDHeader.split(";");
	        for(String header:headers){
	        	if(header.contains("filename")){
	        		fileName = header.split("=")[1];
	        		fileName =fileName.replaceAll("\"", "");
	        		break;
	        	}
	        }
	        //  String workingDir = System.getProperty("user.dir");
	        // String filePath = new java.io.File( ".").getCanonicalPath()+"\\webapps\\upload\\";
	        String filePath2 = new File(System.getProperty("catalina.base"))+ "\\webapps\\upload\\";

	        File f  = new File(filePath2+fileName);
	    	f.createNewFile();
			OutputStream os = new FileOutputStream(f);
	        InputStream is = part.getBody(InputStream.class, null);
	        byte[] bte = new byte[1024];
	        int r=0;
	       while((r=is.read(bte))!=-1){
	    	   os.write(bte);
	       }
	       os.flush();
	       os.close();
	       is.close();
	       System.out.println("Done");
	    }
		}
		catch(Exception e){
			System.out.println("hello : "+e.getMessage());
		}
		
		OutputVO outputVO = new OutputVO();
		outputVO.setFileName(fileName);
		return Response.status(200).entity(outputVO).header("CustomHeader", "CustomHeader").build();
	}
	
	@Override
	public Response createRecord(InputVO bean){
		System.out.println(" Input Name :" +bean.getName());
		System.out.println(" Input Address:" +bean.getAddress());
		OutputVO outputVO = new OutputVO();
		outputVO.setPhone("8697042514");
		outputVO.setName("NABIN");
		return Response.status(200).entity(outputVO).header("CustomHeader", "CustomHeader").build();
	}
	
	@Override
	public Response readRecord(String uuid){
		String returnStr = "Hello Nabin";
		System.out.println("you passed is : "+uuid);
		//code here
		OutputVO outputVO = new OutputVO();
		outputVO.setPhone("8697042514");
		outputVO.setName("NABIN");
		outputVO.setFileName("Hello world");
		return Response.status(200).entity(outputVO).header("CustomHeader", "CustomHeader").build();
	}
	
}
