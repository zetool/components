package org.zetool.components.framework;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <M>
 */
@FunctionalInterface
public interface Displayable<M> {
    void setModel(M model);
}
