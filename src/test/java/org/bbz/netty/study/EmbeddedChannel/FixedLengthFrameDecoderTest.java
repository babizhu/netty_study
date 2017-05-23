package org.bbz.netty.study.embeddedChannel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by liu_k on 2015/10/14.
 * 学习在没有真实网络的情况下如何测试ChannelHandler
 */
public class FixedLengthFrameDecoderTest{

    @Test
    public void testFramesDecoded(){
        ByteBuf buf = Unpooled.buffer();
        for( int i = 0; i < 9; i++ ) {
            buf.writeByte( i );
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel( new FixedLengthFrameDecoder( 3 ) );
        assertEquals( false, channel.writeInbound( input.readBytes( 2 ) ) );
        assertEquals( true, channel.writeInbound( input.readBytes( 7 ) ) );


        assertEquals( true, channel.finish() );

        ByteBuf read = channel.readInbound();
        assertEquals( read, buf.readSlice( 3 ) );
        read.release();

        read = channel.readInbound();
        assertEquals( read, buf.readSlice( 3 ) );
        read.release();

        read = channel.readInbound();
        assertEquals( read, buf.readSlice( 3 ) );
        read.release();

//        assertEquals( null, channel.readInbound() );
        assertNull( channel.readInbound() );
        //Assert.assertNull(channel.readInbound());
        buf.release();
    }
    @Test
    public void testFramesDecoded2(){
        ByteBuf buf = Unpooled.buffer();
        for( int i = 0; i < 9; i++ ) {
            buf.writeByte( i );
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel( new FixedLengthFrameDecoder( 3 ) );
        assertEquals( false, channel.writeInbound( input.readBytes( 2 ) ) );//解码需要三个字节，目前只收到2个字节
//        assertEquals( false, channel.finish() );
        assertEquals( true, channel.writeInbound( input.readBytes( 7 ) ) );

        assertEquals( true, channel.finish() );

//        Assert.assertTrue(channel.finish());
        ByteBuf read = channel.readInbound();

        assertEquals( read, buf.readSlice( 3 ) );
        //Assert.assertEquals(, read);
        read.release();

        read = channel.readInbound();
        assertEquals( read, buf.readSlice( 3 ) );
//        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = channel.readInbound();
        assertEquals( read, buf.readSlice( 3 ) );
//        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

//        assertEquals( null, channel.readInbound() );
        assertNull( channel.readInbound() );
//        Assert.assertNull(channel.readInbound());
        buf.release();
    }
}