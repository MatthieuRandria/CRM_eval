package site.easy.to.build.crm.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.settings.Taux;
import site.easy.to.build.crm.repository.TauxRepository;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class TauxService {

    @Autowired
    private TauxRepository tauxRepository;

    public void insert(Taux taux) {
        tauxRepository.save(taux);
    }

    public List<Taux> getMostRecentTaux(int limit) {
        return tauxRepository.findRecentTaux();
    }
}
