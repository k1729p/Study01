package kp.company.controller;

import kp.Constants;
import kp.SampleDataset;
import kp.company.model.Department;
import kp.company.model.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import static kp.Constants.DEP_INDEX_UPPER_BOUND;
import static kp.Constants.EMP_INDEX_UPPER_BOUND;

/**
 * The sample dataset loader controller.
 */
@RestController
public class SampleDatasetLoaderController {
    private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

    /**
     * Loads the {@link SampleDataset} for the {@link Department}s with the
     * {@link Employee}s.
     *
     * @param depIndex the {@link Department}'s index upper bound
     * @param empIndex the {@link Employee}'s index upper bound
     * @return the dataset loading confirmation response
     */
    @GetMapping(Constants.LOAD_SAMPLE_DATASET_PATH)
    public String loadSampleDataset(Long depIndex, Long empIndex) {

        SampleDataset.loadDataset(Optional.ofNullable(depIndex).orElse(DEP_INDEX_UPPER_BOUND),
                Optional.ofNullable(empIndex).orElse(EMP_INDEX_UPPER_BOUND));
        logger.debug("loadSampleDataset():");
        return Constants.LOAD_SAMPLE_DATASET_RESULT;
    }

}
