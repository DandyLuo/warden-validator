package com.validator.warden.express;

import com.validator.warden.express.factory.GroovyScriptFactory;
import com.validator.warden.util.Maps;
import groovy.lang.Binding;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author DandyLuo
 */
@Slf4j
@Accessors(chain = true)
public class ExpressParser {

    private final GroovyScriptFactory groovy = GroovyScriptFactory.getInstance();

    private Binding binding;

    public ExpressParser(){
        this.binding = new Binding();
    }

    public ExpressParser(final Maps<?, ?> maps){
        this.binding = new Binding(maps.build());
    }

    public void setBinding(final Maps<?, ?> maps){
        this.binding = new Binding(maps.build());
    }

    @SuppressWarnings("unchecked")
    public void addBinding(final Maps<?, ?> maps){
        this.binding.getVariables().putAll(maps.build());
    }

    /**
     * 表达式解析
     *
     * 我们这里只返回Boolean结果
     * @param importPath 脚本依赖的其他的jar包路径
     * @param script 待解析的表达式脚本
     * @return true=解析为true, false=解析结果为false
     */
    public Boolean parse(final String importPath, String script){
        script = importPath + "\n" + script;
        try {
            final Object result = this.groovy.scriptGetAndRun(script, this.binding);
            if (null == result) {
                return null;
            }
            return (Boolean) result;
        }catch (final Exception e){
            log.error("表达式执行失败", e);
        }
        return null;
    }

    /**
     * 表达式解析
     *
     * 我们这里只返回Boolean结果
     * @param script 待解析的表达式脚本
     * @return true=解析为true, false=解析结果为false
     */
    public Boolean parse(final String script){
        return this.parse("", script);
    }
}
