/*
 * Copyright (c) 2005, 2014 vacoor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.glanway.jty.utils;

import com.google.common.collect.Sets;

import java.util.Iterator;

/**
 * @author vacoor
 */
public abstract class DiffSwitch<L, R> {

    public static class Handler<L, T> extends DiffSwitch<L, T> {
        protected Handler(boolean skipNull) {
            super(skipNull);
        }

        @Override
        protected final boolean caseBothHas(L left, T right) {
            doBothHas(left, right);
            return super.caseBothHas(left, right);
        }

        @Override
        protected final boolean caseOnlyLeftHas(L left) {
            doOnlyLeftHas(left);
            return super.caseOnlyLeftHas(left);
        }

        @Override
        protected final boolean caseOnlyRightHas(T right) {
            doOnlyRightHas(right);
            return super.caseOnlyRightHas(right);
        }

        protected void doBothHas(L left, T right) { /*- adapter method */ }

        protected void doOnlyLeftHas(L left) { /*- adapter method */ }

        protected void doOnlyRightHas(T right) { /*- adapter method */ }
    }

    /* *****************************
     *
     * *****************************/

    /**
     * left 始终作为外层循环, 因此应该将元素多的作为 left
     *
     * @param left
     * @param right
     */
    @SuppressWarnings("unchecked")
    public final void doSwitch(Iterable<L> left, Iterable<R> right) {
        if (null == left && null == right) {
            return;
        }

        // 左侧为null, 右侧不为null
        if (null == left) {
            right = Sets.newHashSet(right);
            for (R r : right) {
                if (needSkip(r)) continue;
                if (!caseOnlyRightHas(r)) {
                    return;
                }
            }
            return;
        }

        // 如果右侧为null,左侧不为null 或 两侧相同
        if (null == right || left == right) {
            left = Sets.newHashSet(left);
            for (L leftValue : left) {
                if (needSkip(leftValue)) continue;
                // (右侧为null 并且 不继续) 或者 两侧都存在但是不继续
                if ((null == right && !caseOnlyLeftHas(leftValue)) || !caseBothHas(leftValue, (R) leftValue)) {
                    return;
                }
            }
            return;
        }

        //
        left = Sets.newHashSet(left);
        right = Sets.newHashSet(right);

        for (Iterator<L> leftIt = left.iterator(); leftIt.hasNext(); ) {
            L l = leftIt.next();
            if (needSkip(l)) continue;

            boolean processed = false;
            for (Iterator<R> rightIt = right.iterator(); rightIt.hasNext(); ) {
                R r = rightIt.next();
                if (needSkip(r)) continue;

                if (doEquals(l, r)) {
                    processed = true;
                    leftIt.remove();
                    rightIt.remove();

                    // 两侧都存在
                    if (!caseBothHas(l, r)) {
                        return;
                    }
                    break;
                }
            }

            if (processed) continue;

            leftIt.remove();
            // 只有左侧存在
            if (!caseOnlyLeftHas(l)) {
                return;
            }
        }
        // 仅右侧存在
        for (R r : right) {
            if (needSkip(r)) continue;
            if (!caseOnlyRightHas(r)) {
                return;
            }
        }
    }

    /* **************************
     *
     * ************************ */

    protected boolean caseBothHas(L left, R right) {
        return true;
    }

    protected boolean caseOnlyLeftHas(L left) {
        return true;
    }

    protected boolean caseOnlyRightHas(R right) {
        return true;
    }

    protected boolean doEquals(L left, R right) {
        return doDefaultEquals(left, right);
    }

    protected final boolean doDefaultEquals(Object left, Object right) {
        return left == right || null != left && left.equals(right);
    }

    private boolean needSkip(Object value) {
        return skipNull && null == value;
    }

    private final boolean skipNull;
    protected DiffSwitch(boolean skipNull) {
        this.skipNull = skipNull;
    }
}
