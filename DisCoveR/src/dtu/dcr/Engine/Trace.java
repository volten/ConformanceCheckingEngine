package dtu.dcr.Engine;

import lombok.Getter;

import dtu.dcr.ProcessModel.*;


public class Trace extends Activity{

    @Getter
    private String id;
    @Getter
    private String name;


    public Trace() {

    }

    public Trace(String name, String id) {
        this.name = name;
        this.id = id;
    }
    
}
