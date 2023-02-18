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

    public Image addImage(Integer blogId, String description, String dimensions) throws Exception{
        //add an image to the blog
        Blog blog = blogRepository2.findById(blogId).get();
        if(blog == null){
            throw new Exception();
        }
        Image image = new Image();
        image.setBlog(blog);
        image.setDescription(description);
        image.setDimensions(dimensions);

        blog.getImageList().add(image);

        imageRepository2.save(image);
        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        if(image == null){
            return;
        }
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
        String [] scrarray = screenDimensions.split("X");
        Image image = imageRepository2.findById(id).get();

        String imageDimensions = image.getDimensions();
        String [] imgarray = imageDimensions.split("X");

        int scrl = Integer.parseInt(scrarray[0]);
        int scrb = Integer.parseInt(scrarray[1]);

        int imgl = Integer.parseInt(imgarray[0]);
        int imgb = Integer.parseInt(imgarray[1]);

        return no_Images(scrl,scrb,imgl,imgb);
    }
    private int no_Images(int scrl, int scrb, int imgl, int imgb) {
        int lenC = scrl/imgl;
        int lenB = scrb/imgb;
        return lenC*lenB;
    }
}
