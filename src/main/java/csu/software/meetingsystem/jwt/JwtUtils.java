package csu.software.meetingsystem.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import csu.software.meetingsystem.exception.JWTException;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class JwtUtils {

    public static String createToken(String userId, String userName) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,300);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(userId)
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .withClaim("userName", userName)
                .sign(Algorithm.HMAC256(userId+"MeetingOrder"));   //加密
    }


    public static void verifyToken(String token, String secret) throws Exception {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret+"MeetingOrder")).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
            throw new JWTException("Invalid JWT", HttpStatus.BAD_REQUEST);
        }
    }

    public static String getAudience(String token) throws Exception {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
            throw new JWTDecodeException("获取签发对象失败");
        }
        return audience;
    }


    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }

}
