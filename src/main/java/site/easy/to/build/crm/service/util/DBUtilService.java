package site.easy.to.build.crm.service.util;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.repository.util.DBUtilRepository;

@Service
public class DBUtilService {
    @Autowired
    private DBUtilRepository repository;

    @Transactional
    public void clearTable(String tableName) {
        repository.clearTable(tableName);
    }

    @Transactional
    public void disableConstraint(int step){
        repository.disableConstraint(step);
    }
}
