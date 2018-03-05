package lostandfound.demo.Repositories;

import lostandfound.demo.Model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepo extends CrudRepository<Item, Long> {
    Item findItemByTitle(String roleName);
    //Iterable<Item> findAllByItemNameContainingIgnoreCase(String searchitems);

    //Iterable<Item> findAllByitemStatusContainingIgnoreCase(String itemStatus);

    Iterable<Item> findAllByitemStatusContainingIgnoreCase(String itemStatus);
}
