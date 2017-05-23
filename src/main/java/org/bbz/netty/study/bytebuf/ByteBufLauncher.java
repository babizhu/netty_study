package org.bbz.netty.study.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * Created by liu_k on 2015/10/20.
 * bytebuf的各种测试用例
 */
public class ByteBufLauncher{

    /**
     * Unpooled.wrappedBuffer的用法
     * 这样获取的ByteBuf的capcity是固定的，不能往里面写入新内容
     *
     * 这样获取的多个ByteBuf的内部读写索引是各自的，但是底层数据（byteBuf.array()）是相同的
     * 修改一个Bytebuf底层的arry，那么这个修改会反馈到其他ByteBuf上来
     * 最原始的bye数组内容被修改了也会反馈到各自的ByteBuf中
     *
     */
    private static void testWrappedBuffer(){
        String content = "Hello World";
        byte[] bytes = content.getBytes();
        //ByteBuf buf = Unpooled.wrappedBuffer( bytes );
        ByteBuf byteBuf = Unpooled.wrappedBuffer( bytes );
        ByteBuf byteBuf1 = Unpooled.wrappedBuffer( bytes );
        //byteBuf.capacity( 100 );会抛出异常

        System.out.println( byteBuf == byteBuf1 ? "byteBuf == byteBuf1" : "byteBuf != byteBuf1" );
        //byteBuf.writeBytes( "liulaoye".getBytes() );会抛出异常
        if(byteBuf.array() == byteBuf1.array() && byteBuf.array() == bytes ){
            System.out.println( "byteBuf.array() == byteBuf1.array() == bytes");
        }else {
            System.out.println("byteBuf.array() != byteBuf1.array() || byteBuf.array() != bytes");
        }


        bytes[4] = 'a';
        byteBuf1.setBytes( 1,"2".getBytes() );
        byteBuf.setChar( 2, '$' );
        System.out.println( byteBuf.toString( Charset.defaultCharset() ));
        System.out.println( byteBuf1.toString( Charset.defaultCharset() ));

    }

    /**
     * 和Unpooled.wrappedBuffer方法类似，不能扩充，最大的区别在于，每个ByteBuf存在自己的数据array
     * 互不影响
     */
    private static void testCopiedBuffer(){
        byte[] bytes = "Hello World!".getBytes();
        ByteBuf byteBuf = Unpooled.copiedBuffer( bytes );
        ByteBuf byteBuf1 = Unpooled.copiedBuffer( bytes );
        System.out.println( byteBuf == byteBuf1 ? "byteBuf == byteBuf1" : "byteBuf != byteBuf1" );


//        //byteBuf.writeBytes( "liulaoye".getBytes() );会抛出异常
        if(byteBuf.array() == byteBuf1.array() && byteBuf.array() == bytes ){
            System.out.println( "byteBuf.array() == byteBuf1.array() == bytes");
        }else {
            System.out.println("byteBuf.array() != byteBuf1.array() || byteBuf.array() != bytes");
        }

        byteBuf.setChar( 1, '你' );
        System.out.println( byteBuf.toString( Charset.defaultCharset() ));
        System.out.println( byteBuf1.toString( Charset.defaultCharset() ));
//
//        //相等的
//        System.out.println( byteBuf.toString( Charset.defaultCharset() ).equals( byteBuf1.toString(Charset.defaultCharset() ) ));
//        byteBuf.writeBytes( "liulaoye".getBytes() );
//        System.out.println( byteBuf.toString( Charset.defaultCharset() ).equals( byteBuf1.toString( Charset.defaultCharset() ) ));


    }

    public static void main( String[] args ){
//        testWrappedBuffer();
        testCopiedBuffer();
    }


}
