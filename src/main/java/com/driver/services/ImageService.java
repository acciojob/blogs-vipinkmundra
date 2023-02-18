package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        Image image = new Image();
        image.setBlog(blog);
        image.setDescription(description);
        image.setDimensions(dimensions);

        imageRepository2.save(image);
        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();
        List<Image> imageList = blog.getImageList();
        List<Image> newImageList = new ArrayList<>();
        for(Image i : imageList){
            if(i.equals(image)){
                continue;
            }
            newImageList.add(i);
        }
        blog.setImageList(newImageList);
        blogRepository2.save(blog);
        imageRepository2.deleteById(id);
    }

    public int countImagesInScreen(Integer id, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        int count = 0;
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();
        List<Image> imageList = blog.getImageList();
        for(Image i : imageList){
            if(i.getDimensions() == screenDimensions){
                count++;
            }
        }
        return count;
    }
}
