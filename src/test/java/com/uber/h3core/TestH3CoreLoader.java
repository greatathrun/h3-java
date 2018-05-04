/*
 * Copyright 2017-2018 Uber Technologies, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uber.h3core;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * H3CoreLoader is mostly tested by {@link TestH3Core}. This also tests OS detection.
 */
public class TestH3CoreLoader {
    @Test
    public void testConstructor() {
        new H3CoreLoader();
        // Nothing else to test here
    }

    @Test
    public void testDetectOs() {
        assertEquals(H3CoreLoader.OperatingSystem.ANDROID,
                H3CoreLoader.detectOs("Android", "anything"));
        assertEquals(H3CoreLoader.OperatingSystem.DARWIN,
                H3CoreLoader.detectOs("vendor", "Mac OS X"));
        assertEquals(H3CoreLoader.OperatingSystem.WINDOWS,
                H3CoreLoader.detectOs("vendor", "Windows"));
        assertEquals(H3CoreLoader.OperatingSystem.LINUX,
                H3CoreLoader.detectOs("vendor", "Linux"));

        assertEquals(H3CoreLoader.OperatingSystem.LINUX,
                H3CoreLoader.detectOs("vendor", "anything else"));
    }

    @Test
    public void testDetectArch() {
        assertEquals("x64", H3CoreLoader.detectArch("amd64"));
        assertEquals("x64", H3CoreLoader.detectArch("x86_64"));
        assertEquals("x64", H3CoreLoader.detectArch("x64"));

        assertEquals("x86", H3CoreLoader.detectArch("x86"));
        assertEquals("x86", H3CoreLoader.detectArch("i386"));
        assertEquals("x86", H3CoreLoader.detectArch("i486"));
        assertEquals("x86", H3CoreLoader.detectArch("i586"));
        assertEquals("x86", H3CoreLoader.detectArch("i686"));
        assertEquals("x86", H3CoreLoader.detectArch("i786"));
        assertEquals("x86", H3CoreLoader.detectArch("i886"));

        assertEquals("anything", H3CoreLoader.detectArch("anything"));
        assertEquals("i986", H3CoreLoader.detectArch("i986"));
        assertEquals("i286", H3CoreLoader.detectArch("i286"));
    }

    @Test(expected = UnsatisfiedLinkError.class)
    public void testExtractNonexistant() throws IOException {
        File tempFile = File.createTempFile("test-extract-resource", null);

        tempFile.deleteOnExit();

        H3CoreLoader.copyResource("/nonexistant-resource", tempFile);
    }
}
