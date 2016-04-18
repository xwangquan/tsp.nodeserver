package cn.richinfo.tsp.nodeserver.concurrent.mutex;

public class Mutex implements Sync {

    /**
     * The lock status *
     */
    protected boolean inUse;

    /**
     * Wait (possibly forever) until successful passage. Fail only upon
     * interuption. Interruptions always result in `clean' failures. On failure,
     * you can be sure that it has not been acquired, and that no corresponding
     * release should be performed. Conversely, a normal return guarantees that
     * the acquire was successful.
     *
     * @see Sync#acquire()
     */
    public void acquire() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        synchronized (this) {
            try {
                while (inUse) {
                    wait();
                }
                inUse = true;
            } catch (InterruptedException ex) {
                notify();
                throw ex;
            }
        }
    }

    /**
     * @param msecs
     *            the number of milleseconds to wait. An argument less than or
     *            equal to zero means not to wait at all. However, this may
     *            still require access to a synchronization lock, which can
     *            impose unbounded delay if there is a lot of contention among
     *            threads.
     * @return true if acquired
     * @see Sync#attempt(long)
     */
    public boolean attempt(long msecs) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        synchronized (this) {
            if (!inUse) {
                inUse = true;
                return true;
            } else if (msecs <= 0) {
                return false;
            } else {
                long waitTime = msecs;
                long start = System.currentTimeMillis();
                try {
                    for (;;) {
                        wait(waitTime);
                        if (!inUse) {
                            inUse = true;
                            return true;
                        } else {
                            waitTime = msecs - (System.currentTimeMillis() - start);
                            if (waitTime <= 0) {
                                return false;
                            }
                        }
                    }
                } catch (InterruptedException ex) {
                    notify();
                    throw ex;
                }
            }
        }
    }

    /**
     * Potentially enable others to pass. <p/> Because release does not raise
     * exceptions, it can be used in `finally' clauses without requiring extra
     * embedded try/catch blocks. But keep in mind that as with any java method,
     * implementations may still throw unchecked exceptions such as Error or
     * NullPointerException when faced with uncontinuable errors. However, these
     * should normally only be caught by higher-level error handlers.
     *
     * @see Sync#release()
     */
    public synchronized void release() {
        inUse = false;
        notify();
    }
}
