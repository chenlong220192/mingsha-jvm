package com.mingsha.jvm.core.oop;

import com.mingsha.jvm.core.constants.JVMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all Oop (Ordinary Object Pointer) types in the JVM.
 * <p>
 * In the JVM, all objects are represented as Oops. This class provides the base
 * structure for object headers, including the mark word and klass pointer.
 * <p>
 * Object header layout (64-bit JVM):
 * <ul>
 *   <li>Mark word: 8 bytes - contains identity hash, GC age, lock state</li>
 *   <li>Klass pointer: 8 bytes - pointer to class metadata</li>
 * </ul>
 *
 * @version 1.0.0
 * @see InstanceOop for object instances
 * @see ArrayOop for array objects
 */
public class OopDesc {

    /** Logger instance */
    protected static final Logger logger = LoggerFactory.getLogger(OopDesc.class);

    /**
     * Mark word stored in the object header.
     * Contains:
     * <ul>
     *   <li>Identity hash code (bits 0-31)</li>
     *   <li>GC age counter (bits 32-34)</li>
     *   <li>Lock state (bits 35-36)</li>
     *   <li>Thread ID (bits 37-63)</li>
     * </ul>
     */
    protected long markWord;

    /**
     * Pointer to the class metadata (Klass).
     * Points to the MethodArea where class information is stored.
     */
    protected long klassPointer;

    /**
     * Default constructor.
     * Initializes mark word and klass pointer to zero.
     */
    public OopDesc() {
        this.markWord = 0;
        this.klassPointer = 0;
        logger.trace("Created new OopDesc");
    }

    /**
     * Returns the mark word value.
     *
     * @return the mark word
     */
    public long getMarkWord() {
        return markWord;
    }

    /**
     * Sets the mark word value.
     *
     * @param markWord the new mark word value
     */
    public void setMarkWord(long markWord) {
        this.markWord = markWord;
        logger.trace("Mark word updated: 0x{}", Long.toHexString(markWord));
    }

    /**
     * Returns the klass pointer.
     *
     * @return the klass pointer
     */
    public long getKlassPointer() {
        return klassPointer;
    }

    /**
     * Sets the klass pointer.
     *
     * @param klassPointer the new klass pointer value
     */
    public void setKlassPointer(long klassPointer) {
        this.klassPointer = klassPointer;
        logger.trace("Klass pointer updated: 0x{}", Long.toHexString(klassPointer));
    }

    /**
     * Returns the size of this object in bytes.
     * Default implementation returns the header size only.
     *
     * @return the object size in bytes
     */
    public int size() {
        return JVMConstants.OBJECT_HEADER_SIZE;
    }

    /**
     * Returns the identity hash code for this object.
     * <p>
     * Uses the mark word to compute a hash code:
     * <pre>
     * hash = (int) (markWord ^ (markWord >>> 32))
     * </pre>
     *
     * @return the identity hash code
     */
    public int identityHashCode() {
        return (int) (markWord ^ (markWord >>> 32));
    }

    /**
     * Returns a string representation of this object.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return String.format("OopDesc{markWord=0x%x, klassPointer=0x%x, size=%d}",
                markWord, klassPointer, size());
    }
}
