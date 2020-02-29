package stoner.tspringcloud.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截requestMapping方法 可进行参数绑定等
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //防sql注入 参数处理
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true));
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handler (HttpServletRequest request, Exception e) {
        log.error("error uri: {}", request.getRequestURI());
        log.error(e.getMessage(), e);
        if (e.getCause() == null) {
            return e.getMessage();
        } else {
            return e.getCause().getMessage();
        }
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public String handler (HttpServletRequest request, ServiceException e) {
        log.error("error uri: {}", request.getRequestURI());
        log.error("service Exception {}", e.getMessage());
        if (e.getCause() == null) {
            return e.getMessage();
        } else {
            return e.getCause().getMessage();
        }
    }
}
