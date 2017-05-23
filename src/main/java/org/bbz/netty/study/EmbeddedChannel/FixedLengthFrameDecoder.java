package org.bbz.netty.study.embeddedChannel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by liu_k on 2015/10/14.
 * 利用EmbeddedChannel来测试各种channel是否正确，即使在没有网络的情况下
 * 本类就是一个被测试对象，具体的测试代码请参看FixedLengthFrameDecoderTest
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