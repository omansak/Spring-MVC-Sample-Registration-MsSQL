package spring.mvc.demo.Helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@PropertySource("classpath:appSettings.properties")
public class EmailToken {
    private final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String ALGO = "AES";
    private final byte[] keyValue =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    private String getCryptoSplit = ":OSK:";

    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return new BASE64Encoder().encode(encVal);
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(keyValue, ALGO);
    }


    public String CreateMailToken(String email, String token) throws Exception {
        return encrypt(email + getCryptoSplit + token + getCryptoSplit + sdf.format(Calendar.getInstance().getTime()));
    }

    public String CreateToken() throws Exception {
        return encrypt(UUID.randomUUID().toString());
    }

}
