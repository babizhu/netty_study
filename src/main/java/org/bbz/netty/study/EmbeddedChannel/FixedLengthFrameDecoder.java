package org.bbz.netty.study.EmbeddedChannel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by liu_k on 2015/10/14.
 * ����ѧϰ��û����ʵ������������β���ChannelHandler�����Ӵ��롣
 * ��ϸ�Ĳ��Է�����ο���Ӧ��test����FixedLengthFrameDecoderTest
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder{

    private final int frameLength;

    public FixedLengthFrameDecoder( int frameLength ){
        if( frameLength <= 0 ) {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength );
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode( ChannelHandlerContext ctx, ByteBuf in, List<Object> out ) {
        if( in.readableBytes() >= frameLength ) {
            ByteBuf buf = in.readBytes( frameLength );
            out.add( buf );
        }
    }
}