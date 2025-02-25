/********************************************************************************
 * CruiseControl, a Continuous Integration Toolkit
 * Copyright (c) 2001-2003, ThoughtWorks, Inc.
 * 200 E. Randolph, 25th Floor
 * Chicago, IL 60601 USA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     + Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     + Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     + Neither the name of ThoughtWorks, Inc., CruiseControl, nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ********************************************************************************/
/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2000,2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Ant", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package com.thoughtworks.cruise.utils;

import static com.thoughtworks.cruise.util.ExceptionUtils.bomb;
import static java.lang.String.format;
import static java.nio.charset.Charset.defaultCharset;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.thoughtworks.cruise.util.Clock;
import com.thoughtworks.cruise.util.SystemTimeClock;


public class StreamPumper implements Runnable {

    private BufferedReader in;


    private boolean completed;
    private final StreamConsumer streamConsumer;
    private final String prefix;
    private long lastHeard;
    private final Clock clock;

    public StreamPumper(InputStream in, StreamConsumer streamConsumer, String prefix) {
        this(in, streamConsumer, prefix, null);
    }

    public StreamPumper(InputStream inputStream, StreamConsumer streamConsumer) {
        this(inputStream, streamConsumer, "");
    }

    public StreamPumper(InputStream in, StreamConsumer streamConsumer, String prefix, String encoding) {
        this(in, streamConsumer, prefix, encoding, new SystemTimeClock());
    }

    public StreamPumper(InputStream in, StreamConsumer streamConsumer, String prefix, String encoding, Clock clock) {
        this.streamConsumer = streamConsumer;
        this.prefix = prefix;
        this.clock = clock;
        this.lastHeard = System.currentTimeMillis();
        try {
            if ( encoding==null){
                this.in = new LineNumberReader(new InputStreamReader(in));
            }
            else {
                this.in = new LineNumberReader(new InputStreamReader(in, encoding));
            }
        } catch (UnsupportedEncodingException e) {
            bomb(format("Unable to use [%s] to decode stream.  The current charset is [%s]", encoding, defaultCharset()));
        }
    }

    public void run() {
        try {
            String s = in.readLine();
            while (s != null) {
                consumeLine(s);
                s = in.readLine();
            }
        } catch (Exception e) {
//            e.printStackTrace();
            // do nothing
        } finally {
            IOUtils.closeQuietly(in);
            completed = true;
        }
    }

    private void consumeLine(String line) {
        lastHeard = System.currentTimeMillis();
        if (streamConsumer != null) {
            if (StringUtils.isBlank(prefix)) {
                streamConsumer.consumeLine(line);
            } else {
                streamConsumer.consumeLine(prefix + line);
            }
        }
    }

    public static StreamPumper pump(InputStream stream, StreamConsumer streamConsumer) {
        return pump(stream, streamConsumer, "");
    }


    public void readToEnd() {
        while (!completed) {
            try {
                clock.sleepForMillis(100);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static StreamPumper pump(InputStream stream, StreamConsumer streamConsumer, String prefix) {
        return pump(stream, streamConsumer, prefix, null);
    }

    public static StreamPumper pump(InputStream stream, StreamConsumer streamConsumer, String prefix, String encoding) {
        StreamPumper pumper = new StreamPumper(stream, streamConsumer, prefix, encoding);
        new Thread(pumper).start();
        return pumper;
    }

    private Long timeSinceLastLine(TimeUnit unit) {
        long now = clock.currentTimeMillis();
        return unit.convert(now - lastHeard, TimeUnit.MILLISECONDS);
    }

    public boolean didTimeout(long duration, TimeUnit unit) {
        if (completed) { return false; }
        return timeSinceLastLine(unit) > duration;
    }
}
