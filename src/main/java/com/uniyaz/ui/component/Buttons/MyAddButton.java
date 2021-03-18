package com.uniyaz.ui.component.Buttons;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MyAddButton extends Button {

    public MyAddButton(){
        setIcon(FontAwesome.PLUS);
        addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }
}
