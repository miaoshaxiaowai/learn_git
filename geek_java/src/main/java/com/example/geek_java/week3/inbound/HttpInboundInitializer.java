package com.example.geek_java.week3.inbound;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServer;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p=ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpObjectAggregator(1024*1024));
        ChannelInboundHandlerAdapter adapter=new HttpInboundHandler(this.proxyServer);
        p.addLast(adapter);
    }

    public HttpInboundInitializer(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }
}
