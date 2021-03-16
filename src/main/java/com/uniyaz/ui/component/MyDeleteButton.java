package com.uniyaz.ui.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MyDeleteButton extends Button {

    public MyDeleteButton(){
        setIcon(FontAwesome.REMOVE);
        addStyleName(ValoTheme.BUTTON_DANGER);
    }
}
