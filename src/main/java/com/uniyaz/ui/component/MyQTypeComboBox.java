package com.uniyaz.ui.component;

import com.uniyaz.core.domain.EnumQType;
import com.vaadin.data.Item;
import com.vaadin.ui.ComboBox;

public class MyQTypeComboBox extends ComboBox {

    public MyQTypeComboBox() {
        fillCombobox();
    }

    public void fillCombobox() {
        for (EnumQType enumQType : EnumQType.values()) {
            Item item = addItem(enumQType);
        }
    }
}
