package com.uniyaz.ui.component.Buttons;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class MyEditButton extends Button {

    public MyEditButton() {
        setIcon(FontAwesome.EDIT);
        addStyleName(ValoTheme.BUTTON_FRIENDLY);
    }
}
