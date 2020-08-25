package com.validator.warden.express.factory;

import java.util.HashMap;
import java.util.Map;

import com.validator.warden.util.EncryptUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.codehaus.groovy.runtime.InvokerHelper;

/**
 * groovy脚本类享元工厂
 *
 * @author DandyLuo
 */
public class GroovyScriptFactory {

    private static final Map<String, Class<Script>> SCRIPT_CACHE = new HashMap<>();
    private final GroovyClassLoader CLASS_LOADER = new GroovyClassLoader();
    private static final GroovyScriptFactory FACTORY = new GroovyScriptFactory();

    /**
     * 设置为单例模式
     */
    private GroovyScriptFactory() {
    }

    public static GroovyScriptFactory getInstance() {
        return FACTORY;
    }

    private Class<Script> getScript(final String key) {
        // 压缩脚本节省空间
        final String encodeStr = EncryptUtil.SHA256(key);
        if (SCRIPT_CACHE.containsKey(encodeStr)) {
            return SCRIPT_CACHE.get(encodeStr);
        } else {
            // 脚本不存在则创建新的脚本
            @SuppressWarnings("unchecked")
            final Class<Script> scriptClass = this.CLASS_LOADER.parseClass(key);
            SCRIPT_CACHE.put(encodeStr, scriptClass);
            return scriptClass;
        }
    }

    private Object run(final Class<Script> script, final Binding binding) {
        final Script scriptObj = InvokerHelper.createScript(script, binding);
        final Object result = scriptObj.run();
        // 每次脚本执行完之后，一定要清理掉内存
        this.CLASS_LOADER.clearCache();
        return result;
    }

    public Object scriptGetAndRun(final String key, final Binding binding) {
        return this.run(this.getScript(key), binding);
    }
}
