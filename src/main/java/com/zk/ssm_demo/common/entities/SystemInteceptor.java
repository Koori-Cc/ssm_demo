package com.zk.ssm_demo.common.entities;

import com.zk.ssm_demo.user.entities.User;
import com.zk.ssm_demo.utils.KeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author panbing@supcon.com
 * @create 2017/9/30 9:51
 * @description 拦截器拦截用户的请求
 */
@Component
public class SystemInteceptor implements HandlerInterceptor {

    /**
     * 配置文件中定义的不拦截url
     */
    private List<String> uncheckUrls;    //需要get,set方法

    public List<String> getUncheckUrls() {
        return uncheckUrls;
    }

    public void setUncheckUrls(List<String> uncheckUrls) {
        this.uncheckUrls = uncheckUrls;
    }

    @Value("${open_url}")
    private String open_url;  //项目的索引页

    private Logger logger = LoggerFactory.getLogger(SystemInteceptor.class);

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestUri = httpServletRequest.getRequestURI();
        User user = (User)httpServletRequest.getSession().getAttribute(KeyUtils.SESSION_USER);
        if(!uncheckUrls.contains(requestUri) && user == null){   //拦截这些请求
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/toIndex.do");
        }else if (uncheckUrls.contains(requestUri) && user != null) {    //包含这些请求的情况
            String requestPath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + requestUri;
            if(open_url.equals(requestPath)) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/user/toMenu.do");
            }
        }
        //logger.info("preHandle执行完毕");
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //logger.info("postHandle执行完毕");
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //logger.info("afterCompletion执行完毕");
    }
}
