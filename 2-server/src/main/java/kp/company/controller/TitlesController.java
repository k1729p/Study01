package kp.company.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import kp.company.api.TitlesApi;
import kp.company.model.Title;

/**
 * The controller for the {@link Title}.<br/>
 * The implementation of the {@link TitlesApi}.
 * 
 */
@RestController
@Validated
public class TitlesController implements TitlesApi {
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The constructor.
	 * 
	 */
	public TitlesController() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<List<Title>> getTitles() {
		logger.info("getTitles():");
		return ResponseEntity.ok(List.of(Title.values()));
	}

}
