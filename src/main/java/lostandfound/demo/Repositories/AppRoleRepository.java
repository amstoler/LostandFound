package lostandfound.demo.Repositories;

import lostandfound.demo.Model.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole,Long> {
    AppRole findAppRoleByRoleName(String roleName);
}
