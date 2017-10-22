package com.app.feja.mooddiary.app.testsuite;

import com.app.feja.mooddiary.app.testcase.MainTestCase;
import com.app.feja.mooddiary.app.testcase.PasswordTestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PasswordTestCase.class,
        MainTestCase.class
})
public class TestSuiteAll {}
