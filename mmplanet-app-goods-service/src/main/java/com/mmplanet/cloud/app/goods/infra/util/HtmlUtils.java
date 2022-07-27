package com.mmplanet.cloud.app.goods.infra.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @Company: chaohuhu © Copyright 2022<br>
 * @Description: <br>
 * @Project: mmplanet <br>
 * @CreateDate: Created in 2022/6/25 11:49 <br>
 * @Author: niujiao
 */
public final class HtmlUtils {

    private static ScriptEngineManager sem = new ScriptEngineManager();

    public static String unescape(String content) throws ScriptException {
        ScriptEngine engine = sem.getEngineByExtension("js");
        return (String)engine.eval("unescape('"+content+"')");
    }
}
