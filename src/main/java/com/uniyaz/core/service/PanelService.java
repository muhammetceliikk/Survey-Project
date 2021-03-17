package com.uniyaz.core.service;

import com.uniyaz.core.dao.PanelDao;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Survey;

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
        return panelDao.listPanels();
    }

    public List<MyPanel> listPanelsById(Survey survey) {
        panelDao = new PanelDao();
        return panelDao.listPanelsById(survey);
    }
}
