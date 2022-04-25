package csu.software.meetingsystem.phoneCode;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.rmi.ServerException;
import java.util.Scanner;

public class PhoneCode {

    private static String code ;


    public static String getPhonemsg(String mobile,String code) {

        if (mobile == null || mobile == "") {
            return "";
        }

        System.setProperty(StaticPeram.defaultConnectTimeout, StaticPeram.Timeout);
        System.setProperty(StaticPeram.defaultReadTimeout, StaticPeram.Timeout);
        final String product = StaticPeram.product;
        final String domain = StaticPeram.domain;
        final String accessKeyId = StaticPeram.accessKeyId;
        final String accessKeySecret = StaticPeram.accessKeySecret;
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                accessKeyId, accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,
                    domain);
        } catch (ClientException e1) {
            e1.printStackTrace();
        }

        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(mobile);
        request.setSignName(StaticPeram.SignName);
        request.setTemplateCode(StaticPeram.TemplateCode);
        request.setTemplateParam("{ \"code\":\""+code+"\"}");
        request.setOutId("yourOutId");
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null
                    && sendSmsResponse.getCode().equals("OK")) {
            } else {
            }
        } catch (ClientException e) {
            e.printStackTrace();
            return "由于系统维护，暂时无法获取验证码！";
        }
        return "true";
    }

    public static String vcode(){
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }
}
