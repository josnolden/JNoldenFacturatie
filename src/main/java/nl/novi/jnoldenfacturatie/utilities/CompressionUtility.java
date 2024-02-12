package nl.novi.jnoldenfacturatie.utilities;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

@Component
public class CompressionUtility {
    public static byte[] compress(byte[] input){
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(input);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
        byte[] temporaryArray = new byte[ 4 * 1024 ];
        try {
            while(!deflater.finished()){
                int size = deflater.deflate(temporaryArray);
                outputStream.write(temporaryArray, 0, size);
            }
            outputStream.close();
        }
        catch (IOException exception){
            throw new RuntimeException(exception);
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompress(byte[] input){
        Inflater inflater = new Inflater();
        inflater.setInput(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(input.length);
        byte[] temporaryArray = new byte[ 4 * 1024 ];
        try {
            while(!inflater.finished()){
                int count = inflater.inflate(temporaryArray);
                outputStream.write(temporaryArray, 0, count);
            }
            outputStream.close();
        }
        catch (DataFormatException | IOException exception){
            throw new RuntimeException(exception);
        }
        return outputStream.toByteArray();
    }
}
