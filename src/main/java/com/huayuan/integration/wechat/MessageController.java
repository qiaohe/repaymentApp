package com.huayuan.integration.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huayuan.domain.wechat.User;
import com.huayuan.integration.wechat.domain.EventMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Johnson on 4/14/14.
 */
@Controller(value = "messageController")
public class MessageController {
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private static final String ACCESS_TOKEN_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={appid}&secret={appSecret}";
    private static final String GET_USER_URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={accessToken}&openid={openid}&lang=zh_CN";
    private static final ObjectMapper MAPPER = new ObjectMapper();


    @Inject
    private Unmarshaller jaxbMarshaller;

    @RequestMapping(value = "/huayuan158", method = RequestMethod.GET)
    @ResponseBody
    public String init(@RequestParam String signature, @RequestParam String timestamp,
                       @RequestParam String nonce, @RequestParam String echostr) throws IOException {

        if (checkSignaturePass(signature, timestamp, nonce))
            return echostr;
        return null;
    }

    @RequestMapping(value = "/huayuan158", method = RequestMethod.POST)
    @ResponseBody
    public String handleMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestMessageXml = IOUtils.toString(request.getInputStream(), "utf-8");
        request.setCharacterEncoding("utf-8");

        EventMessage eventMessage = (EventMessage)jaxbMarshaller.unmarshal(new StreamSource(request.getInputStream()));
        System.out.println(eventMessage.getEventKey());
        return requestMessageXml;

    }

    private String getAccessToken() {
        String token = REST_TEMPLATE.getForObject(ACCESS_TOKEN_URL_PATTERN,
                String.class, Configuration.appId(), Configuration.appSecret());
        return StringUtils.split(token, ":")[2];
    }

    private User getUser(final String openId) {
        String userResponseJson = REST_TEMPLATE.getForObject(GET_USER_URL_PATTERN, String.class, openId);
        try {
            return MAPPER.readValue(userResponseJson, User.class);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }


    private static boolean checkSignaturePass(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{Configuration.token(), timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (String s : arr) {
            content.append(s);
        }
        final String checkedSignature = DigestUtils.shaHex(content.toString());
        return StringUtils.isNotEmpty(checkedSignature) && checkedSignature.equals(signature.toUpperCase());
    }

    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
        MessageController messageController = context.getBean("messageController", MessageController.class);

    }
}
