package ru.mail.krivonos.al.repository.impl;

import org.springframework.stereotype.Repository;
import ru.mail.krivonos.al.repository.FavoriteArticleRepository;
import ru.mail.krivonos.al.repository.model.FavoriteArticle;
import ru.mail.krivonos.al.repository.model.FavoriteArticleKey;

import javax.persistence.Query;
import java.util.List;

@Repository("favoriteArticleRepository")
public class FavoriteArticleRepositoryImpl extends GenericRepositoryImpl<FavoriteArticleKey, FavoriteArticle>
        implements FavoriteArticleRepository {

    @Override
    @SuppressWarnings("unchecked")
    public List<FavoriteArticle> findAllByUserIdWithDescOrder(int limit, int offset, Long userId, String orderField) {
        String query = String.format("from %s %s order by %s desc", entityClass.getName(),
                " where user_id = :user_id", orderField);
        Query q = entityManager.createQuery(query)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .setParameter("user_id", userId);
        return q.getResultList();
    }
}
