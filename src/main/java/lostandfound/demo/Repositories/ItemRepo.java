package lostandfound.demo.Repositories;

import lostandfound.demo.Model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<Item, Long> {
    Item findItemByTitle(String roleName);
    Iterable<Item> findAllByitemStatusContainingIgnoreCase(String itemStatus);
}
