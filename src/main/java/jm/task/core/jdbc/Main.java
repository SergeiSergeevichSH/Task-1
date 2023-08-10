package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
////
        userService.saveUser("Антон", "Антонов", (byte) 15);
//        userService.saveUser("Борис", "Борисов", (byte) 22);
//        userService.saveUser("Владмир", "Путин", (byte) 105);
//        userService.saveUser("Иван", "Иванов", (byte) 34);
//        userService.saveUser("Семен", "Семенов", (byte) 50);
//
        List<User> list = userService.getAllUsers();
//        System.out.println("list.get(12)+\"\\n\" = " + list.get(12-1) + "\n");
//        list.forEach(System.out::println);
//        userService.removeUserById(2);
//
//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();
    }
}
