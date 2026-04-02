package com.mingsha.jvm.gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;
import java.util.ArrayList;
import java.util.List;

/**
 * GC Roots for reachability analysis.
 * <p>
 * GC Roots are the starting points for marking live objects.
 *
 * @version 1.0.0
 */
public class GCRoots {

    /** Logger instance */
    private static final Logger logger = LoggerFactory.getLogger(GCRoots.class);

    /** Registered GC roots */
    private final List<Object> roots = new ArrayList<>();

    /**
     * Adds an object as GC root.
     *
     * @param obj the root object
     */
    public void addRoot(Object obj) {
        roots.add(obj);
        logger.trace("Added GC root: {}", obj);
    }

    /**
     * Returns all GC roots including thread stacks.
     *
     * @return list of root objects
     */
    public List<Object> getRoots() {
        List<Object> allRoots = new ArrayList<>(roots);
        logger.debug("GC roots: {} registered + thread stacks", roots.size());
        return allRoots;
    }

    /**
     * Registers a thread's stack as GC root.
     *
     * @param thread the thread to register
     */
    public void registerThreadStack(MingshaThread thread) {
        JavaStack stack = thread.getStack();
        if (stack != null && !stack.isEmpty()) {
            StackFrame frame = stack.getTopFrame();
            if (frame != null) {
                roots.add(frame);
                logger.debug("Registered thread stack as GC root: {}", thread.getName());
            }
        }
    }

    /** Clears all GC roots */
    public void clear() {
        roots.clear();
        logger.debug("Cleared all GC roots");
    }

    /** @return number of registered roots */
    public int size() { return roots.size(); }
}
