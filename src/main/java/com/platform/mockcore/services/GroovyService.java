package com.platform.mockcore.services;

import groovy.lang.Binding;

public interface GroovyService {
    String exec(Binding binding, String text);
}
