package io.hdavid.jabe;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.RockerOutput;
import com.fizzed.rocker.runtime.RockerRuntime;

public class TemplateTest {

    public static void main(String[] args) {

        System.setProperty(RockerRuntime.KEY_RELOADING, "true");
//        RockerRuntime.getInstance().setReloading(true);

//        simple.index.template()
        BindableRockerModel template = Rocker.template("simple/index.rocker.html", "Uno");
        RockerOutput render = template.render();
        System.out.println(render.toString()+"\n---------------------\n\n");

    }
}
