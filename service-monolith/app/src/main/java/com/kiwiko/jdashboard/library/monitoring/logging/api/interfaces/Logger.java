package com.kiwiko.jdashboard.library.monitoring.logging.api.interfaces;

import com.kiwiko.jdashboard.library.monitoring.logging.api.dto.Log;

/**
 * Interface to represent a class that can log messages.
 *
 * Each log operation supports parameterized logging -- the input is a template string, and array of objects.
 * The template string must contain at least one "{}" placeholder in it, and the input array must contain an equal amount
 * of objects. Each "{}" in the template will be substituted with its corresponding object in the input array.
 * A {@link Throwable} can be optionally present in the input array as the last object. If so, it will not be substituted
 * into the template string, but rather logged separately as a throwable.
 * In the following example, the template string contains two placeholders. There are two real input objects, which will
 * be substituted into the template string. The third input object is a {@link Throwable}, so it will be logged separately.
 * <p>
 * <code>
 *     catch (Exception e) {
 *         int count = 1;
 *         String level = "error";
 *         logger.error("This is a log of level {} with a count of {}", info, count, e);
 *     }
 * </code>
 *
 * @deprecated prefer {@link org.slf4j.Logger}
 */
@Deprecated
public interface Logger {

    void debug(Log log);
    void debug(String message);
    void debug(String message, Throwable cause);

    /**
     * @param template the template string with "{}" placeholders
     * @param args the objects to substitute into the template string's placeholders
     */
    void debug(String template, Object... args);

    void info(Log log);
    void info(String message);
    void info(String message, Throwable cause);

    /**
     * @param template the template string with "{}" placeholders
     * @param args the objects to substitute into the template string's placeholders
     */
    void info(String template, Object... args);

    void warn(Log log);
    void warn(String message);
    void warn(String message, Throwable cause);

    /**
     * @param template the template string with "{}" placeholders
     * @param args the objects to substitute into the template string's placeholders
     */
    void warn(String template, Object... args);

    void error(Log log);
    void error(String message);
    void error(String message, Throwable cause);

    /**
     * @param template the template string with "{}" placeholders
     * @param args the objects to substitute into the template string's placeholders
     */
    void error(String template, Object... args);
}
