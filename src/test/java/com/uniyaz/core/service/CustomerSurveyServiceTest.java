package com.uniyaz.core.service;

import com.uniyaz.core.dao.CustomerSurveyDao;
import com.uniyaz.core.domain.CustomerSurvey;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CustomerSurveyServiceTest {

    CustomerSurveyDao customerSurveyDao;

    @Test
    public void listCustomerSurveysByMail(){
        customerSurveyDao = new CustomerSurveyDao();
        List<CustomerSurvey> customerSurveysList = customerSurveyDao.listCustomerSurveysByMail("mcelik@gmail.com");
        for (CustomerSurvey customerSurvey : customerSurveysList) {
            System.out.println(customerSurvey.getSurvey().getName());
        }

    }
}