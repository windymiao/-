package com.example.university.controller;


import com.example.university.common.Result;
import com.example.university.entity.Menu;
import com.example.university.entity.TStudent;
import com.example.university.entity.TUser;
import com.example.university.service.MenuService;
import com.example.university.service.TStudentService;
import com.example.university.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author university
 * @since 2024-05-23
 */
@RestController
@RequestMapping("/user")
public class TUserController {

    @Autowired
    private TUserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private TStudentService studentService;
    // 教师表未作，待后续添加

    // 登录功能
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> map){
        TUser user = null;
        TStudent student = null;
        if ("0".equals(map.get("roleId"))){
             user = userService.lambdaQuery()
                    .eq(TUser::getUsername, map.get("username"))
                    .eq(TUser::getPassword, map.get("password"))
                    .one();
        }
        else if ("1".equals(map.get("roleId"))){
            // 教师表未作，待后续添加
        }
        else if ("2".equals(map.get("roleId"))){
            student = studentService.lambdaQuery()
                    .eq(TStudent::getSno, map.get("username"))
                    .eq(TStudent::getSno, map.get("password"))
                    .one();
        }
        if (user!=null || student!= null) {
            String roleId = user!=null?"0":"2";
            List<Menu> menus = menuService.lambdaQuery().like(Menu::getMenuright, roleId).list();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user", user!=null?user:student);
            hashMap.put("menu", menus);
            return Result.success(hashMap);
        }
        return Result.fail();
    }


//    @PostMapping("/login")
    public Result login1(@RequestBody TUser user){
        TUser user1 = userService.lambdaQuery()
                .eq(TUser::getUsername, user.getUsername())
                .eq(TUser::getPassword, user.getPassword())
                .one();
        // 若查询到用户，一并将该用户的目录一并查询出来
        if (user1!=null) {
            List<Menu> menus = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("user", user1);
            hashMap.put("menu", menus);
            return Result.success(hashMap);
        }
        return Result.fail();

    }
}
