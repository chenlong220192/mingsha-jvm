package com.mingsha.jvm.gc;

import com.mingsha.jvm.runtime.thread.MingshaThread;
import com.mingsha.jvm.runtime.stack.JavaStack;
import com.mingsha.jvm.runtime.stack.StackFrame;
import java.util.ArrayList;
import java.util.List;

/**
 * GC Roots for可达性分析.
 */
public class GCRoots {
    private final List<Object> roots = new ArrayList<>();

    public void addRoot(Object obj) { roots.add(obj); }

    public List<Object> getRoots() {
        List<Object> allRoots = new ArrayList<>(roots);
        // Add thread stacks as GC roots
        // In real implementation, would iterate over all threads
        return allRoots;
    }

    public void registerThreadStack(MingshaThread thread) {
        JavaStack stack = thread.getStack();
        if (stack != null && !stack.isEmpty()) {
            StackFrame frame = stack.getTopFrame();
            if (frame != null) {
                // Simplified: just mark the frame as a root
                roots.add(frame);
            }
        }
    }
}
