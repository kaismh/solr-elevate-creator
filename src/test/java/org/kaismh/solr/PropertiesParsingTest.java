package org.kaismh.solr;

import com.github.davidcarboni.ResourceUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaismh on 7/24/16.
 */
public class PropertiesParsingTest {

    @Test
    public void parsingTest() throws IOException {

        ElevateConfig elevateConfig= ElevateConfigFactory.getElevateConfig(
                ResourceUtils.getFile("/elevateConfigTest.properties"));

        Assert.assertNotNull(elevateConfig);

        System.out.println(elevateConfig);
    }

}
