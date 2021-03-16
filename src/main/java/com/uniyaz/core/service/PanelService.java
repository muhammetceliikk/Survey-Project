package com.uniyaz.core.service;

import com.uniyaz.core.dao.PanelDao;
import com.uniyaz.core.dao.SurveyDao;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Survey;
import com.vaadin.ui.Panel;

import java.util.List;

public class PanelService {

    private PanelDao panelDao;

    public PanelService() {
    }

    public void savePanel(MyPanel panel) {
        panelDao = new PanelDao();
        panelDao.savePanel(panel);
    }

    public List<MyPanel> listPanels() {
        panelDao = new PanelDao();
        return panelDao.listSurveys();
    }
}
