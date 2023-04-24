package org.project.Model;

import org.junit.jupiter.api.Test;
import org.project.Model.POb_2Js;

class POb_2JsTest {

    @Test
    void to_json() {
        POb_2Js pOb_2Js = new POb_2Js();
        POb_2Js.to_json(pOb_2Js);
    }
}