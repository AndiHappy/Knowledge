package com.springboot.example.usualserver;

import com.springboot.example.dao.UsualDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
// @ComponentScan
// @Repository
public class UsualServer {

  @Autowired
  private UsualDao usualDao;

  public UsualDao getUsualDao() {
    return usualDao;
  }

  public void setUsualDao(UsualDao usualDao) {
    this.usualDao = usualDao;
  }

  public String getDaoTest(String daoName) {
    return usualDao.getDao(daoName);
  }
}
