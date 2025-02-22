package kp.company.controller;

import kp.company.api.TitlesApi;
import kp.company.model.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * The controller for the {@link Title}.<br/>
 * The implementation of the {@link TitlesApi}.
 */
@RestController
@Validated
public class TitlesController implements TitlesApi {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<Title>> getTitles() {
        logger.info("getTitles():");
        return ResponseEntity.ok(List.of(Title.values()));
    }

}
