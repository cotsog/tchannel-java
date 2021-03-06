/*
 * Copyright (c) 2015 Uber Technologies, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.uber.tchannel.messages;

import com.uber.tchannel.tracing.Trace;

public final class Cancel implements Message {

    private final long id;
    private final long ttl;
    private final Trace tracing;
    private final String why;

    /**
     * Designated Constructor
     *
     * @param id      unique id of the message
     * @param ttl     ttl on the wire
     * @param tracing tracing information
     * @param why     why the message was canceled
     */
    public Cancel(long id, long ttl, Trace tracing, String why) {
        this.id = id;
        this.ttl = ttl;
        this.tracing = tracing;
        this.why = why;
    }

    public long getTtl() {
        return ttl;
    }

    public Trace getTracing() {
        return tracing;
    }

    public long getId() {
        return this.id;
    }

    public MessageType getMessageType() {
        return MessageType.Cancel;
    }

    public String getWhy() {
        return why;
    }

}
