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

import com.uber.tchannel.framing.TFrame;
import com.uber.tchannel.messages.Cancel;
import com.uber.tchannel.messages.MessageType;
import com.uber.tchannel.tracing.Trace;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

public class CancelCodec extends MessageToMessageCodec<TFrame, Cancel> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Cancel msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        // ttl:4
        buffer.writeInt((int) msg.getTtl());

        // tracing:25
        CodecUtils.encodeTrace(msg.getTracing(), buffer);

        // why~2
        CodecUtils.encodeString(msg.getWhy(), buffer);

        TFrame frame = new TFrame(buffer.writerIndex(), MessageType.Cancel, msg.getId(), buffer);
        out.add(frame);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TFrame frame, List<Object> out) throws Exception {
        // ttl:4
        long ttl = frame.payload.readUnsignedInt();

        // tracing:25
        Trace tracing = CodecUtils.decodeTrace(frame.payload);

        // why~2
        String why = CodecUtils.decodeString(frame.payload);

        Cancel cancel = new Cancel(frame.id, ttl, tracing, why);
        out.add(cancel);
    }
}
