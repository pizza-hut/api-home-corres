package org.owlet.corres.home.controller;

import org.owlet.corres.home.model.CorresJobRequest;
import org.owlet.corres.home.model.CorresJobResponse;

import org.owlet.corres.home.service.HomeCorresService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeApiController{
	
	public static final Logger logger = LoggerFactory.getLogger(HomeApiController.class);

	@Autowired
	HomeCorresService corresService;
	
	@PostMapping(value="corres/home/submit")
	public ResponseEntity<CorresJobResponse> submit(@RequestBody CorresJobRequest req) {
		
		
		logger.info(req.getSourceSystem());
		logger.info(req.getExternalKey());
		logger.info(req.getCorresId());
		//logger.info(req.getCallbackUrl().toString());
		//logger.info(req.getDataFileContent());
		
		CorresJobResponse corresJobResponse = new CorresJobResponse(); 
		try {
			corresJobResponse = corresService.submitJob(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//return new ResponseEntity<corresJobResponse>(corresJobResponse, HttpStatus.);
			return null;
		}
		return new ResponseEntity<CorresJobResponse>(corresJobResponse, HttpStatus.OK);
		
	}
	
}
