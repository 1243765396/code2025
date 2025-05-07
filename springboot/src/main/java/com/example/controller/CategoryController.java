package com.example.controller;

import com.example.common.Result;
import com.example.entity.Category;
import com.example.service.CategoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: youMeng
 * @Date: 2025/3/7 - 03 - 07 - 17:00
 * @Description: com.example.controller
 * @version: 1.0
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody Category category) { // @RequestBody 接受前端传来的 json 数据
        categoryService.add(category);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) { // @RequestBody 接受前端传来的 json 数据
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) { // @PathVariable 接受前端传来的路径参数
        categoryService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(Category category) {
        List<Category> categoryList = categoryService.selectAll(category);
        return Result.success(categoryList);
    }

    /**
     * 分页查询
     * pageNum: 当前的页码
     * pageSize: 每页的个数
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Category category) {
        PageInfo<Category> pageInfo = categoryService.selectPage(pageNum, pageSize, category);
        return Result.success(pageInfo); // 返回的是分页的对象
    }
}
