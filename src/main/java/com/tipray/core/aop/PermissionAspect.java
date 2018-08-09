package com.tipray.core.aop;

import com.tipray.bean.Message;
import com.tipray.bean.Permission;
import com.tipray.bean.ResponseMsg;
import com.tipray.bean.Role;
import com.tipray.constant.reply.PermissionErrorEnum;
import com.tipray.core.ThreadVariable;
import com.tipray.core.annotation.PermissionAnno;
import com.tipray.util.EmptyObjectUtil;
import com.tipray.util.ResponseMsgUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.util.List;

/**
 * 权限检查切面
 * <p>
 * 根据用户原有的权限，与目标方法的权限配置进行匹配，<br>
 * 如果目标方法需要的权限在用户原有的权限以内，则调用目标方法；<br>
 * 如果不匹配，则不调用目标方法。
 *
 * @author chenlong
 * @version 1.0 2018-04-17
 */
@Aspect
@Component
public class PermissionAspect {

    /**
     * 权限认证
     * <p>
     * 检查用户的权限和目标方法的需要的权限是否匹配，如果匹配则调用目标方法，不匹配则不调用
     *
     * @param joinPoint {@link ProceedingJoinPoint}
     * @return
     * @throws Throwable
     */
    // @Around("@annotation(com.tipray.core.annotation.PermissionAnno) and
    // execution(public * com.tipray.controller.*Controller.*(..))")
    @Around("@annotation(com.tipray.core.annotation.PermissionAnno)")
    public Object authentication(ProceedingJoinPoint joinPoint) throws Throwable {
        // 1.获取访问目标方法应该具备的权限
        // 为解析目标方法的PrivilegeInfo注解，根据我们定义的解析器，需要得到：目标类的class形式，方法的名称，方法的参数
        // Class<?> targetClass = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> returnType = method.getReturnType();
        // Class<?>[] parameterTypes = signature.getParameterTypes();
        // String methodName = signature.msg();
        // Object[] params = joinPoint.getArgs();

        // 判断方法上是否有PermissionAnno注解
        // method.isAnnotationPresent(PermissionAnno.class)
        // 得到方法上的注解
        PermissionAnno anno = method.getAnnotation(PermissionAnno.class);
        // 当前方法的访问权限
        String accessAuthority = anno.value();
        // 2.遍历用户的权限，看是否拥有目标方法对应的权限
        boolean isAccessed = false;
        if (EmptyObjectUtil.isEmptyString(accessAuthority)) {
            // 访问权限为null或""，说明任何人都可以访问
            isAccessed = true;
        } else {
            Role role = ThreadVariable.getRole();
            if (role.getIsSuper()) {
                // 超级管理员有访问权限
                isAccessed = true;
            } else {
                List<Permission> permissions = role.getPermissions();
                if (!EmptyObjectUtil.isEmptyList(permissions)) {
                    for (Permission permission : permissions) {
                        String ename = permission.getEname();
                        // 用户原有权限列表中的权限与目标方法上PermissionAnno注解配置的权限进行匹配
                        if (ename != null && ename.equalsIgnoreCase(accessAuthority)) {
                            isAccessed = true;
                            break;
                        }
                    }
                }
            }
        }
        // 3.如果用户拥有权限，则调用目标方法；如果没有，则不调用目标方法，只给出提示
        if (isAccessed) {
            return joinPoint.proceed();// 调用目标方法
        } else {
            return handleUnaccessibleResult(returnType);
        }
    }

    /**
     * 无权限访问目标方法处理
     *
     * @param returnType {@link Class<?>} 目标方法返回类型
     * @return 无权访问提示
     * @throws AccessControlException if returnType is not one of [String, Message, ResponseMsg]
     */
    private Object handleUnaccessibleResult(Class<?> returnType) {
        if (returnType.equals(String.class)) {
            return PermissionErrorEnum.PERMISSION_DENIED.msg();
        } else if (returnType.equals(Message.class)) {
            return Message.error(PermissionErrorEnum.PERMISSION_DENIED.msg());
        } else if (returnType.equals(ResponseMsg.class)) {
            return ResponseMsgUtil.error(PermissionErrorEnum.PERMISSION_DENIED);
        }
        throw new AccessControlException(PermissionErrorEnum.PERMISSION_DENIED.msg());
    }
}
