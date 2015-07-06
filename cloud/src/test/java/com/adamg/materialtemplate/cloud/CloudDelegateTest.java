package com.adamg.materialtemplate.cloud;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * Unit test suite for the library. Base init test.
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CloudDelegateTest {

    @Test
    public void testSetup() throws Exception {
        final CloudDelegate delegate = CloudDelegate.go(RuntimeEnvironment.application);
        assert (delegate != null);
    }
}