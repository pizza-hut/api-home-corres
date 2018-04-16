package org.owlet.corres.home.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.owlet.corres.home.model.CorresACLJobRequest;
import org.owlet.corres.home.model.CorresACLJobResponse;
import org.owlet.corres.home.model.CorresJobRequest;
import org.owlet.corres.home.model.CorresJobResponse;
//import org.owlet.corres.home.model.CorresStatusRequest;
//import org.owlet.corres.home.model.CorresStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import com.fasterxml.jackson.databind.ObjectMapper;

@Service("HomeCorresService")
public class HomeCorresServiceImpl implements HomeCorresService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${url.api.acl.corres}")
	private String url_api_acl_corres;
	
	@Value("${kafka.topic.job.request}")
	private String kafka_topic;
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public CorresJobResponse submitJob(CorresJobRequest req) throws Exception {		
		logger.info("submitJob: '{}' '{}' '{}' '{}'", req.getCorresId(), req.getSourceSystem(), req.getExternalKey());
		CorresACLJobRequest aclRequest = new CorresACLJobRequest();
		aclRequest.setProjectName(req.getCorresId());		
		aclRequest.setDataFileContent(req.getDataFileContent());		
		aclRequest.setSiteId(req.getSiteId());
		
		//TO-DO enhance exception handling here
		//add back later
		CorresACLJobResponse aclJobResponse = this.postJob(aclRequest);
		logger.info(aclJobResponse.getJobId());
		JSONObject json = new JSONObject();
		
		json.put("sourceSystem", req.getSourceSystem());
		json.put("externalKey", req.getExternalKey());
		json.put("siteId", req.getSiteId());
		json.put("callback_url", req.getCallbackUrl());
		json.put("externalJobRef", req.getExternalJobRef());
		//json.put("jobId", aclJobResponse.getJobId());
		//json.put("status", aclJobResponse.getStatus());
		
		//ObjectMapper objectMapper = new ObjectMapper();
		//logger.info("corresJobRequest JSON representation '{}'", objectMapper.writeValueAsString(req));		
		//json.put("request", objectMapper.writeValueAsString(req));
		json.put("jobId", aclJobResponse.getJobId());
		json.put("status", aclJobResponse.getStatus());
		
		String msg=json.toString();
		logger.info("msg in json: '{}'", msg);
		
		//TO-DO enhance exception handling here
		this.publishMessage(this.kafka_topic, msg);
		
		CorresJobResponse corresJobResponse = new CorresJobResponse();
		corresJobResponse.setJobId(aclJobResponse.getJobId());
		corresJobResponse.setStatus(aclJobResponse.getStatus());
		corresJobResponse.setPriority(aclJobResponse.getPriority());
		
		return corresJobResponse;		
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();		
	}
	
	
	private CorresACLJobResponse postJob(CorresACLJobRequest req) {
		logger.info(this.url_api_acl_corres);				
		CorresACLJobResponse corresACLJobResponse = this.restTemplate.postForObject(this.url_api_acl_corres, req, CorresACLJobResponse.class); 
		return corresACLJobResponse;
	}
	
	
	private void publishMessage(String topic, String msg) {
		logger.info(topic + " " + msg);
		kafkaTemplate.send(topic, msg);		
	}	

}
