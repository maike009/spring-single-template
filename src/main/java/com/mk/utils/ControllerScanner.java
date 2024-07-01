package com.mk.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * ControllerScanner类用于扫描指定包路径下的所有Controller类，并获取它们的所有请求路径。
 * 该类依赖于Spring的ApplicationContext来查找所有RequestMappingHandlerMapping实例，
 * 然后通过这些实例来获取每个Controller类的请求路径。
 */
@Component
public class ControllerScanner {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取指定包路径下所有Controller类的请求路径。
     *
     * @return 包含所有请求路径的集合
     */
    public Set<String> getAllControllerEndpoints() {
        String basePackage = "com.mk.module.controller"; // 指定你的 Controller 包路径
        return scanPackage(basePackage);
    }

    /**
     * 扫描指定包路径，查找所有标注了@Controller或@RestController注解的类，并获取它们的请求路径。
     *
     * @param basePackage 要扫描的包路径
     * @return 包含所有请求路径的集合
     */
    private Set<String> scanPackage(String basePackage) {
        Set<String> endpoints = new HashSet<>();
        // 创建一个ClassPathScanningCandidateComponentProvider实例，用于扫描指定包路径下的候选组件
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        // 添加过滤器，只扫描标注了@Controller或@RestController注解的类
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

        // 查找指定包路径下的所有候选组件
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            try {
                // 加载类
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                // 获取类上的@RequestMapping注解路径
                String classMappingPath = getClassMappingPath(clazz);

                // 获取所有RequestMappingHandlerMapping
                Map<String, RequestMappingHandlerMapping> handlerMappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);

                // 遍历所有RequestMappingHandlerMapping
                for (RequestMappingHandlerMapping handlerMapping : handlerMappings.values()) {
                    // 获取所有请求映射信息和处理方法
                    Map<RequestMappingInfo, HandlerMethod> methods = handlerMapping.getHandlerMethods();

                    // 遍历所有请求映射信息
                    for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : methods.entrySet()) {
                        RequestMappingInfo requestMappingInfo = entry.getKey();
                        // 获取请求方法类型（如GET、POST等）
                        RequestMethodsRequestCondition methodsCondition = requestMappingInfo.getMethodsCondition();
                        Optional<String> optionalS = methodsCondition.getMethods().stream().findFirst().map(Enum::name);

                        HandlerMethod handlerMethod = entry.getValue();
                        // 判断处理方法是否属于当前类
                        boolean flag = handlerMethod.getBeanType() == clazz;
                        if (flag) {
                            // 获取路径模式条件
                            PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
                            if (pathPatternsCondition != null) {
                                // 获取方法的路径映射
                                Set<String> methodMappingPaths = pathPatternsCondition.getPatternValues();
                                for (String methodMappingPath : methodMappingPaths) {
                                    String fullPath;
                                    // 如果类的路径和方法的路径相同，添加请求方法类型
                                    if (classMappingPath.equals(methodMappingPath)){
                                        fullPath = classMappingPath + methodMappingPath + "-" + (optionalS.orElseGet(() -> "NOT"));
                                        endpoints.add(fullPath);
                                        break;
                                    }
                                    // 组合类的路径和方法的路径
                                    fullPath = classMappingPath + methodMappingPath;
                                    endpoints.add(fullPath);
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                // 处理类加载异常
                e.printStackTrace();
            }
        }
        return endpoints;
    }

    /**
     * 获取指定Controller类的基路径。
     *
     * @param controllerClass 要获取路径的Controller类
     * @return 该Controller类的基路径
     */
    private String getClassMappingPath(Class<?> controllerClass) {
        // 查找类上的@RequestMapping注解
        RequestMapping classMapping = AnnotationUtils.findAnnotation(controllerClass, RequestMapping.class);
        // 如果注解存在且有值，返回注解的第一个值，否则返回空字符串
        if (classMapping != null && classMapping.value().length > 0) {
            return classMapping.value()[0];
        }
        return "";
    }
}
