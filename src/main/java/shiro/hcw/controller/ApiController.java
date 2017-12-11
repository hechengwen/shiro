package shiro.hcw.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shiro.hcw.pojo.RsaPOJO;
import shiro.hcw.pojo.User;
import shiro.hcw.rsa.Base64;
import shiro.hcw.rsa.RSAEncrypt;
import shiro.hcw.rsa.RSASignature;
import shiro.hcw.service.UserService;
import shiro.hcw.utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * Copyright (C), 2017，jumore Tec.
 * Author: hechengwen
 * Version:
 * Date: 2017/12/11 13:30
 * Description:
 * Others:
 */
@Controller
@RequestMapping("api")
public class ApiController {

    private static final String filepath = "E:\\rsa";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    @RequestMapping("receive")
    @ResponseBody
    public String receive(RsaPOJO rsaPOJO) throws Exception{
        String encodeStr = rsaPOJO.getEncodeStr();
        String cipher = rsaPOJO.getCipher();
        String sign = rsaPOJO.getSign();
        if (StringUtils.isEmpty(encodeStr) || StringUtils.isEmpty(cipher) || StringUtils.isEmpty(sign)) {
            logger.info("encodeStr is empty or cipher is empty or sign is empty;\nsign={},\ncipher={},\nemcodeStr={}", rsaPOJO.getSign(), rsaPOJO.getCipher(),rsaPOJO.getEncodeStr());
            return null;
        }
        String data = URLDecoder.decode(encodeStr,"UTF-8");
        // 公钥验签
        boolean result = RSASignature.doCheck(data,sign,RSAEncrypt.loadPublicKeyByFile(filepath));
        if (!result) {
            logger.error("签名验签失败！！！！");
            return null;
        }
        logger.info("签名验签成功！,sign={}", sign);
        // 公钥解密数据
        byte bytes[] = RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),Base64.decode(cipher));
        return new String(bytes);
    }

    @RequestMapping("receiveStr")
    @ResponseBody
    public String receiveStr(HttpServletRequest request) {
        try {
            RsaPOJO pojo = new RsaPOJO();
            String result = checkData(pojo, request);
            logger.info("接收到请参数为：{}", result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "{\"code\":\"0\",\"msg\":\"ok\"}";
    }

    public String checkData(RsaPOJO pojo, HttpServletRequest request) throws Exception {
        Map<String, Object> map = getFormData(request);
        if (map.isEmpty()) {
            return null;
        }
        // 赋值到bean
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            BeanUtils.setProperty(pojo, entry.getKey(), entry.getValue());
        }
        String data = URLDecoder.decode(pojo.getEncodeStr(), "UTF-8");
        String sign = pojo.getSign();
        // 公钥验签
        boolean result = RSASignature.doCheck(data, sign, RSAEncrypt.loadPublicKeyByFile(filepath));
        if (!result) {
            logger.error("签名验签失败!!!!");
            return null;
        }
        logger.info("签名验签成功！,sign={}", sign);
        String cipher = pojo.getCipher();
        // 私钥解密过程
        byte[] res = RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));
        return new String(res);
    }

    private Map<String, Object> getFormData(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        try {
            Enumeration parameterNames = request.getParameterNames();
            for (; parameterNames.hasMoreElements(); ) {
                String name = parameterNames.nextElement().toString();
                data.put(name, request.getParameter(name));
            }
            String query = request.getQueryString();
            if (StringUtils.isNotEmpty(query)) {
                Map map = getMapFromQuery(query);
                data.putAll(map);
            }
            String content = IOUtils.toString(request.getInputStream());
            if (StringUtils.isNotEmpty(content)) {
                JSONObject jsonObject = JSON.parseObject(content);
                data.putAll(jsonObject);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return data;
    }

    public static Map<String, String> getMapFromQuery(String queryString) {
        Map<String, String> map = new HashMap<String, String>();
        String[] item = queryString.split("&");//分割每个参数
        for (String i : item) {
            int index = i.indexOf("=");
            String key = i.substring(0, index);
            String value = i.substring(index + 1);
            map.put(key, value);
        }
        return map;
    }

}
