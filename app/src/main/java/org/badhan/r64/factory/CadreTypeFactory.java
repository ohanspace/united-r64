package org.badhan.r64.factory;

import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.CadreType;

import java.util.ArrayList;

public class CadreTypeFactory {
    private static ArrayList<CadreType> types;

    private static CadreTypeFactory instance;
    private CadreTypeFactory(){}

    public static CadreTypeFactory getInstance(){
        if (instance == null){
            instance = new CadreTypeFactory();
            types = instance.generateAllTypes();
        }

        return instance;
    }

    public ArrayList<CadreType> getAllTypes(){
        return types;
    }

    public CadreType getByKey(String key){
        for (CadreType cadreType : types){
            if (cadreType.getKey().equals(key)){
                return cadreType;
            }
        }
        return new CadreType();
    }

    private ArrayList<CadreType> generateAllTypes(){
        ArrayList<CadreType> types = new ArrayList<>();
        types.add(new CadreType("ADMINISTRATION", "BCS (Administration)","#9575CD"));
        types.add(new CadreType("AGRICULTURE", "BCS (Agriculture)","#42A5F5"));
        types.add(new CadreType("ECONOMIC", "BCS (Economic)","#0097A7"));
        types.add(new CadreType("FISHERIES", "BCS (Fisheries)","#009688"));
        types.add(new CadreType("INFORMATION", "BCS (Information)","#FF8A65"));
        types.add(new CadreType("JUDICIAL_SERVICE", "BJS (Bangladesh Judicial Service)","#8BC34A"));
        types.add(new CadreType("LIVESTOCK", "BCS (Livestock)","#1DE9B6"));
        types.add(new CadreType("POLICE", "BCS (Police)","#F06292"));
        types.add(new CadreType("PUBLIC_WORKS", "BCS (Public Works)","#FF5252"));
        types.add(new CadreType("TAXATION", "BCS (Taxation)","#9C27B0"));
        return types;
    }
}
