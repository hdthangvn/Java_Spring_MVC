package vn.hoidanit.laptopshop.controller.admin;

import org.springframework.ui.Model;
import vn.hoidanit.laptopshop.domain.User; // ← THÊM import này

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import vn.hoidanit.laptopshop.service.UserService; // ← Thêm import này
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import jakarta.servlet.ServletContext;

@Controller
public class UserController {

    private final UserService userService;
    private final ServletContext servletContext;

    public UserController(UserService userService, ServletContext servletContext) {
        this.userService = userService;
        this.servletContext = servletContext;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("ducthanghoang892004@gmail.com");
        System.out.println(arrUsers);
        model.addAttribute("eric", "test");
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User()); // Giả sử bạn có một lớp User
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") User hoidanit,
            @RequestParam("ducthangFile") MultipartFile file) {
            
            try {
                byte[] bytes;
                bytes = file.getBytes();
                
                String rootPath = this.servletContext.getRealPath("/resources/images");
                File dir = new File(rootPath + File.separator + "avatar");
                if (!dir.exists())
                dir.mkdirs();
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator +
                +System.currentTimeMillis() + "-" + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();    
            } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        // this.userService.handleSaveUser(hoidanit);
        return "redirect:/admin/user";
    }

    // Thêm handler cho request GET đến /admin/user/{id}/edit
    @RequestMapping("/admin/user/{id}/update")
    public String getUpdateUserPage(Model model, @PathVariable long id) {
        User currentUser = this.userService.getUserById(id);

        if (currentUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("newUser", currentUser);
        return "admin/user/update";
    }

    // Thêm handler cho request POST để xử lý việc cập nhật
    @PostMapping("/admin/user/{id}/update")
    public String postUpdateUser(Model model, @ModelAttribute("newUser") User hoidanit) {
        User currentUser = this.userService.getUserById(hoidanit.getId());
        if (currentUser != null) {
            currentUser.setAddress(hoidanit.getAddress());
            currentUser.setFullName(hoidanit.getFullName());
            currentUser.setPhone(hoidanit.getPhone());
        }

        this.userService.handleSaveUser(currentUser);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}/delete")
    public String getDeleteUserPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        // User user = new User();
        // user.setId(id);
        model.addAttribute("newUser", new User()); // Tạo một đối tượng User với ID
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/{id}/delete")
    public String postDeleteUser(Model model, @ModelAttribute("newUser") User kaito) {
        this.userService.deleteUserById(kaito.getId());
        return "redirect:/admin/user";
    }

}
