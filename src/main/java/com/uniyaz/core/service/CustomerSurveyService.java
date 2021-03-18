package com.uniyaz.core.service;

import com.uniyaz.core.dao.CustomerSurveyDao;
import com.uniyaz.core.domain.CustomerSurvey;

import java.util.List;

public class CustomerSurveyService {

    private CustomerSurveyDao customerSurveyDao;

    public CustomerSurveyService() {
    }

    public void saveCustomerSurvey(CustomerSurvey customerSurvey) {
        customerSurveyDao = new CustomerSurveyDao();
        customerSurveyDao.saveCustomerSurvey(customerSurvey);
    }

    public void deleteCustomerSurvey(CustomerSurvey customerSurvey) {
        customerSurveyDao = new CustomerSurveyDao();
        customerSurveyDao.deleteCustomerSurvey(customerSurvey);
    }

    public List<CustomerSurvey> listCustomerSurveys() {
        customerSurveyDao = new CustomerSurveyDao();
        return customerSurveyDao.listCustomerSurveys();
    }

    public List<CustomerSurvey> listCustomerSurveysByMail(String mail){
        customerSurveyDao = new CustomerSurveyDao();
        return customerSurveyDao.listCustomerSurveysByMail(mail);
    }
}
