package nl.rhofman.bookstore.persist.service;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import nl.rhofman.bookstore.persist.model.Category;
import nl.rhofman.bookstore.persist.repository.CategoryRepository;
import nl.rhofman.persist.Service.AbstractService;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Dependent
@Transactional(SUPPORTS)
public class CategoryService extends AbstractService {

    @Inject
    private CategoryRepository categoryRepository;

    public Category getReference(@NotNull Long id) {
        return execute(() -> categoryRepository.getReference(id), "Error getting Category by ID");
    }

    public Category findByName(String name) {
        return execute(() -> categoryRepository.findByName(name), "Error getting Category by Name");
    }

    public Category create(Category category) {
        return execute(() -> categoryRepository.create(category), "Error creating new Category");
    }
}
