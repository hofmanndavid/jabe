package io.hdavid.jabe;

import com.fizzed.rocker.BindableRockerModel;
import com.fizzed.rocker.Rocker;
import com.fizzed.rocker.RockerOutput;
import com.fizzed.rocker.runtime.RockerRuntime;

public class TemplateTest {

    public static void main(String[] args) {
        RockerRuntime.getInstance().setReloading(true);
        BindableRockerModel template = Rocker.template("rtemplates/simple/index.rocker.html", "Uno", "Dos", "Tres");
        RockerOutput render = template.render();
        System.out.println(render.toString()+"\n---------------------\n\n");


    }
}
