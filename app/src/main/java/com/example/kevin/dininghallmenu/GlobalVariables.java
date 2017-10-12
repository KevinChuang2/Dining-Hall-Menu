package com.example.kevin.dininghallmenu;

import java.util.ArrayList;

/**
 * Created by Kevin on 5/13/2017.
 */

public class GlobalVariables {
    public ArrayList<String> menu;
    private GlobalVariables()
    {
        menu = new ArrayList<String>();
    }
    private static GlobalVariables instance;
    public static GlobalVariables getInstance()
    {
        if (instance ==null)
            instance = new GlobalVariables();
        return instance;
    }
}
