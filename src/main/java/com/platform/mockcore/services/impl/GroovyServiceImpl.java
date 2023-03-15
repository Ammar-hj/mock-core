package com.platform.mockcore.services.impl;

import com.platform.mockcore.enums.RespCodeEnum;
import com.platform.mockcore.exception.MockException;
import com.platform.mockcore.services.GroovyService;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.Script;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
public class GroovyServiceImpl implements GroovyService {
    private static final Logger logger = LoggerFactory.getLogger(GroovyServiceImpl.class);
    private static final String IMPORT = "import groovy.util.XmlSlurper\n" +
            "import groovy.json.JsonSlurper\n";

    @Autowired
    GroovyClassLoader groovyClassLoader;

    @Override
    public String exec(Binding binding, String text) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        binding.setProperty("out", printWriter);

        String groovyName = "script" + System.currentTimeMillis() + Math.abs(text.hashCode()) + ".groovy";
        text = IMPORT + text;
        GroovyCodeSource groovyCodeSource = new GroovyCodeSource(text, groovyName, "/groovy/script");

        Class groovyClazz = groovyClassLoader.parseClass(groovyCodeSource);
        Script script = InvokerHelper.createScript(groovyClazz, binding);
        Object runResult = script.run();
        String stdout = stringWriter.toString();
        if (StringUtils.isNoneBlank(stdout)) {
            logger.info("stdout: " + stdout);
        }
        if (runResult == null) {
            return null;
        }
        try {
            if (runResult.getClass().getMethod("toString").getDeclaringClass().equals(Object.class)) {
                throw new MockException(RespCodeEnum.FAILED_TO_CONVERT_GROOVY_EXEC_RESULT_TO_STRING);
            }
        } catch (NoSuchMethodException e) {
            throw new MockException(RespCodeEnum.NOT_FOUND_TO_STRING_METHOD);
        }
        return runResult.toString();
    }
}
