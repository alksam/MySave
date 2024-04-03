package app.dao;

import app.model.Role;
import app.model.User;

public interface ISecurityDAO {
    User createUser(String username, String password, String email, int phoneNumber);
    Role createRole(String role);
    User addRoleToUser(String username, String role);
}
