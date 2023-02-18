package com.driver.services;

import com.driver.models.Blog;
import com.driver.models.Image;
import com.driver.models.User;
import com.driver.repositories.BlogRepository;
import com.driver.repositories.ImageRepository;
import com.driver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository1;

    @Autowired
    UserRepository userRepository1;

    public Blog createAndReturnBlog(Integer userId, String title, String content) throws Exception {
        //create a blog at the current time
        Blog blog = new Blog();
        blog.setContent(content);
        blog.setTitle(title);

        User user = userRepository1.findById(userId).get();
        if(user == null){
            throw new Exception();
        }
        List<Blog> blogList = user.getBlogList();
        blogList.add(blog);
        user.setBlogList(blogList);

        blog.setUser(user);
        blogRepository1.save(blog);
        return blog;
    }

    public void deleteBlog(int blogId){
        //delete blog and corresponding images
        Blog blog = blogRepository1.findById(blogId).get();
        if(blog == null){
            return;
        }
        User user = blog.getUser();
        List<Blog> blogList = user.getBlogList();
        List<Blog> newBlogList = new ArrayList<>();
        for(Blog b : blogList){
            if(b.equals(blog)){
                continue;
            }
            newBlogList.add(blog);
        }
        user.setBlogList(newBlogList);
        userRepository1.save(user);
        blogRepository1.deleteById(blogId);
    }
}
