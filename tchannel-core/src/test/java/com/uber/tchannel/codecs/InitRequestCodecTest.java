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

package com.uber.tchannel.codecs;

import com.uber.tchannel.messages.InitMessage;
import com.uber.tchannel.messages.InitRequest;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class InitRequestCodecTest {

    @Test
    public void shouldEncodeAndDecodeInitRequest() {

        EmbeddedChannel channel = new EmbeddedChannel(
                new TChannelLengthFieldBasedFrameDecoder(),
                new TFrameCodec(),
                new InitRequestCodec()
        );

        InitRequest initReq = new InitRequest(
                42,
                InitRequest.DEFAULT_VERSION,
                new HashMap<String, String>() {{
                    put(InitMessage.HOST_PORT_KEY, "0.0.0.0:0");
                    put(InitMessage.PROCESS_NAME_KEY, "test-process");
                }}
        );

        channel.writeOutbound(initReq);
        channel.writeInbound(channel.readOutbound());

        InitRequest newInitReq = channel.readInbound();
        assertEquals(initReq.getMessageType(), newInitReq.getMessageType());
        assertEquals(newInitReq.getId(), newInitReq.getId());
        assertEquals(newInitReq.getVersion(), newInitReq.getVersion());
        assertEquals(newInitReq.getHostPort(), newInitReq.getHostPort());
        assertEquals(newInitReq.getProcessName(), initReq.getProcessName());

    }

}
