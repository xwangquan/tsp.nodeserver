/**
 *  Copyright 2003-2008 Luck Consulting Pty Ltd
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.richinfo.tsp.nodeserver.concurrent.mutex;

/**
 */
public interface Sync {

    /**
     * One second, in milliseconds; convenient as a time-out value *
     */
    long ONE_SECOND = 1000;

    /**
     * One minute, in milliseconds; convenient as a time-out value *
     */
    long ONE_MINUTE = 60 * ONE_SECOND;

    /**
     * One hour, in milliseconds; convenient as a time-out value *
     */
    long ONE_HOUR = 60 * ONE_MINUTE;

    /**
     * One day, in milliseconds; convenient as a time-out value *
     */
    long ONE_DAY = 24 * ONE_HOUR;

    /**
     * One week, in milliseconds; convenient as a time-out value *
     */
    long ONE_WEEK = 7 * ONE_DAY;

    /**
     * One year in milliseconds; convenient as a time-out value Not that it
     * matters, but there is some variation across standard sources about value
     * at msec precision. The value used is the same as in
     * java.util.GregorianCalendar
     */
    long ONE_YEAR = (long) (365.2425 * ONE_DAY);

    /**
     * One century in milliseconds; convenient as a time-out value
     */
    long ONE_CENTURY = 100 * ONE_YEAR;

    /**
     * Wait (possibly forever) until successful passage. Fail only upon
     * interuption. Interruptions always result in `clean' failures. On failure,
     * you can be sure that it has not been acquired, and that no corresponding
     * release should be performed. Conversely, a normal return guarantees that
     * the acquire was successful.
     */
    void acquire() throws InterruptedException;

    /**
     * Wait at most msecs to pass; report whether passed. <p/> The method has
     * best-effort semantics: The msecs bound cannot be guaranteed to be a
     * precise upper bound on wait time in Java. Implementations generally can
     * only attempt to return as soon as possible after the specified bound.
     * Also, timers in Java do not stop during garbage collection, so timeouts
     * can occur just because a GC intervened. So, msecs arguments should be
     * used in a coarse-grained manner. Further, implementations cannot always
     * guarantee that this method will return at all without blocking
     * indefinitely when used in unintended ways. For example, deadlocks may be
     * encountered when called in an unintended context. <p/>
     *
     * @param msecs
     *            the number of milleseconds to wait. An argument less than or
     *            equal to zero means not to wait at all. However, this may
     *            still require access to a synchronization lock, which can
     *            impose unbounded delay if there is a lot of contention among
     *            threads.
     * @return true if acquired
     */
    boolean attempt(long msecs) throws InterruptedException;

    /**
     * Potentially enable others to pass. <p/> Because release does not raise
     * exceptions, it can be used in `finally' clauses without requiring extra
     * embedded try/catch blocks. But keep in mind that as with any java method,
     * implementations may still throw unchecked exceptions such as Error or
     * NullPointerException when faced with uncontinuable errors. However, these
     * should normally only be caught by higher-level error handlers.
     */
    void release();
}
