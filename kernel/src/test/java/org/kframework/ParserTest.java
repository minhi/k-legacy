// Copyright (c) 2015-2016 K Team. All Rights Reserved.
package org.kframework;

import static org.junit.Assert.*;

import org.junit.Test;
import org.kframework.definition.*;
import org.kframework.frontend.K;
import org.kframework.frontend.Sort;
import scala.Option;
import scala.Tuple2;

import java.util.Set;

import static org.kframework.frontend.KORE.*;
import static org.kframework.Collections.*;

public class ParserTest {
    private static final Sort xSort = Sort("X");
    Module m = Module.apply("TEST", Set(
            Production.apply(xSort, Seq(Terminal.apply("x")), Att().add("klabel", "x"))
    ));

    @Test
    public void simpleNoWarning() {
        Tuple2<Option<K>, Set<Warning>> actual = Parser.from(m).apply(xSort, "x");
        assertTrue("The parse should succeed, but was " + actual, actual._1().isDefined());
        assertTrue("There should be no warnings, but were: " + actual._2(), actual._2().isEmpty());
        assertEquals(KApply(KLabel("x")), actual._1().get());
    }

    @Test
    public void simpleFailedParse() {
        Tuple2<Option<K>, Set<Warning>> actual = Parser.from(m).apply(xSort, "y");
        assertTrue("The parse should fail, but was " + actual, actual._1().isEmpty());
        assertTrue("There should be one error, but were: " + actual._2(), actual._2().size() == 1);
    }
}
