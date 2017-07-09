package com.example.bill.delta.internal.di.scopes;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import javax.inject.Scope;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Fragment to be memorized in the
 * correct component.
 */
@Scope
@Retention(RUNTIME)
public @interface PerFragment {}
