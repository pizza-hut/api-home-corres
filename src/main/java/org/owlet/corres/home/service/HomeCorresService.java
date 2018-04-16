package org.owlet.corres.home.service;

import org.owlet.corres.home.model.CorresJobRequest;
import org.owlet.corres.home.model.CorresJobResponse;

public interface HomeCorresService {
	CorresJobResponse submitJob(CorresJobRequest req) throws Exception;

}
