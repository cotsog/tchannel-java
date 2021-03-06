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

package com.uber.tchannel.api;

import java.util.HashMap;
import java.util.Map;

public final class Response<T> {

    private final Map<String, String> transportHeaders;
    private final String endpoint;
    private final Map<String, String> headers;
    private final T body;

    private Response(Builder<T> builder) {
        this.transportHeaders = builder.transportHeaders;
        this.endpoint = builder.endpoint;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s transportHeaders=%s endpoint=%s headers=%s body=%s>",
                this.getClass().getSimpleName(),
                this.transportHeaders,
                this.endpoint,
                this.headers,
                this.body
        );
    }

    public static class Builder<U> {

        private Map<String, String> transportHeaders = new HashMap<>();
        private String endpoint;
        private Map<String, String> headers = new HashMap<>();
        private U body;

        public Builder(U body) {
            this.body = body;
        }

        public Builder<U> setTransportHeader(String key, String value) {
            this.transportHeaders.put(key, value);
            return this;
        }

        public Builder<U> setTransportHeaders(Map<String, String> transportHeaders) {
            this.transportHeaders.putAll(transportHeaders);
            return this;
        }

        public Builder<U> setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public Builder<U> setHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder<U> setHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder<U> validate() {

            if (endpoint == null) {
                throw new IllegalStateException("`endpoint` cannot be null.");
            }

            return this;
        }

        public Response<U> build() {
            return new Response<>(this.validate());
        }

    }
}
