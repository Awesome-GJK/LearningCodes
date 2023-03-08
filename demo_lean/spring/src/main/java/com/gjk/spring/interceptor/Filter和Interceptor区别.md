# 浅谈Filter和Interceptor区别

## 1、实现方式不同

> **过滤器是实现javax.servlet.Filter接口，基于回调实现的**。Filter接口是在Servlet规范中定义的，也就是说**过滤器Filter的使用依赖于tomcat等容器，只能在WEB应用中使用**。
>
> 我们自定义一个Filter，需要重写doFilter()方法，该方法的第三个参数FilterChain参数实际是一个回调接口。ApplicationFilterChain是它的实现类，实现类内部有一个doFilterr()方法，即回调方法。

```java

/**
 * MyFilter
 *
 * @author: GJK
 * @date: 2022/7/11 18:37
 * @description:
 */
@Component
@Order(1)
@WebFilter(urlPatterns = "/test/**")
public class MyFilter1 implements Filter {

    @Autowired
    private InterceptorService interceptorService;

    /**
     * Filter初始化的时候调用该方法
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        interceptorService.a();
        System.out.println("MyFilter1 has bean initialized...");
    }

    /**
     * 每个用户请求都会调用这个方法，校验通过则放行到下个过滤器
     * 等请求通过所有过滤器后才会到达servlet
     * @param request
     * @param response
     * @param chain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("MyFilter1#doFilter方法开始执行，对" + ((HttpServletRequest)request).getRequestURI().toString() + "请求进行过来...");
        System.out.println("检验接口是否被调用，尝试获取contentType如下： " + response.getContentType());
        interceptorService.a();
        chain.doFilter(request,response);
        System.out.println("尝试获取contentType如下： " + response.getContentType());
        System.out.println("检验接口是否被调用，MyFilter1#doFilter执行结束");

    }

    @Override
    public void destroy() {
        System.out.println("MyFilter1 has been destroyed...");
    }
}
```

> **拦截器是实现HandlerIntercepor接口，基于java反射(动态代理)实现的**。HandlerInterceptor接口是Spring定义的接口，不依赖于tomcat等容器，是可以单独使用的。**不仅能在WEB程序中使用，也可以在Application、Swing等程序中使用**。
>
> 自定义拦截器需要重写preHandle()、postHandle()、afterCompletion()方法。这三个方法分别代表**执行处理程序之前的拦截点**、**成功执行处理程序后的拦截点**、**回调后完成请求处理**。

```java

/**
 * FristInterceptor
 *
 * @author: GJK
 * @date: 2021/12/21 14:48
 * @description:
 */
@Component
@Order(1)
public class FristInterceptor implements HandlerInterceptor {

    @Autowired
    private InterceptorService interceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("--------------执行FristInterceptor----preHandle方法-----------------");
        interceptorService.a();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--------------执行FristInterceptor----postHandle方法-----------------");
        interceptorService.a();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("--------------执行FristInterceptor----afterCompletion方法-----------------");
        interceptorService.a();
    }
}

```

## 2、触发时机不同

> 之前说过，Filter#doFilter()方法的第三个参数是回调接口，具体实现在ApplicationFilterChain类中。我们进入ApplicationFilterChain类的源码看看：
>
> * 先获取自定义过滤器
> * 再执行Filter过滤器
> * 最后执行servlet
>
> 从这个方法，我们可以看出**过滤器Filter是在请求进入容器后，但在进入servlet之前进行预处理，请求结束是在servlet处理完以后**。

```java
public final class ApplicationFilterChain implements FilterChain {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response)
        throws IOException, ServletException {   
            ...//省略
            //还得进去
            internalDoFilter(request,response);
    }
    
    private void internalDoFilter(ServletRequest request,
                                  ServletResponse response)
        throws IOException, ServletException {

        //  先调用过滤器Filter
        if (pos < n) {
            ApplicationFilterConfig filterConfig = filters[pos++];
            //获取过滤器
            Filter filter = filterConfig.getFilter();
            //执行过滤器doFilter方法
            filter.doFilter(request, response, this);
        }
        ...//省略

        // 再调用servlet
        servlet.service(request, response);
           
}
```

> Interceptor拦截器和SpringMVC息息相关，我们在SpringMVC核心类DisPatcherServlet的doDispatch方法中看到了Interceptor各个方法的调用情况，步骤如下：
>
> * 根据请求从HandlerMapping中获取handler以及interceptor调用链
> * 根据handler获取对应的handlerAdapter
> * 在执行handler之前，先调用interceptor的preHandle方法，判断是否拦截
> * 通过handlerAdapter执行handler处理器，返回ModelAndView
> * 请求视图解析器，处理视图
> * 完成handler处理后执行interceptor的postHandle方法
> * 最终调用nterceptor的afterCompletion方法
>
> 从源码中可以看出：**拦截器 Interceptor 是在请求进入servlet后，在进入Controller之前进行预处理的，Controller 中渲染了对应的视图之后请求结束。**

```java
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
            ...//省略

            // 根据请求从HandlerMapping中获取handler以及interceptor调用链
            HandlerExecutionChain mappedHandler = getHandler(processedRequest);

            ...//省略

            // 根据handler获取对应的handlerAdapter
            HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

            //在执行handler之前，先调用interceptor的preHandle方法，判断是否拦截
            if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                //被拦截直接return
                return;
            }

            //通过handlerAdapter执行handler处理器，返回ModelAndView
            ModelAndView mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

           //请求视图解析器，处理视图
            applyDefaultViewName(processedRequest, mv);
           //在完成handler处理后执行interceptor的postHandle方法
            mappedHandler.applyPostHandle(processedRequest, response, mv);
           //这个方法内部会调用interceptor的afterCompletion方法
            processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
	
	}
```

## 3、拦截范围不同

> 过滤器几乎可以对所有进入容器的请求起作用，而拦截器只会对Controller中请求或访问static目录下的资源请求起作用