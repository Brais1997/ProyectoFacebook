/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofacebook;

import com.restfb.BinaryAttachment;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.Post;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.swing.DefaultListModel;

/**
 *
 * @author balva
 */
public class MetodosFB {
    public final static String ACCESSTOKEN="EAACEdEose0cBAIXKdsyOul7BoFkRhwCZBlFUgCnp4mZAJIBuWvsr9Q2cXF0vbCMjObssBWyTJsVKkbU0aZABHZAr41EVdzd9djZABUzOyjyTDX5YdZAG1xGLH7oZBt3ZB37iZCUlTujAjEDO06gJN15ZCo1Y6OZC487H7ZBPOZBrKGFksnQZDZD";
public static FacebookClient facebookClient = new DefaultFacebookClient(ACCESSTOKEN);

    static void post(String message) throws FacebookOAuthException {
        facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", message));
}
     static void uploadImage(String imagePath, String messagePost) throws FileNotFoundException {
        InputStream is = new FileInputStream(new File(imagePath));
        FacebookType publishVideoResponse = facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("myphoto.jpg", is),
                Parameter.with("message", messagePost));
    }

    static DefaultListModel getPosts() {
        DefaultListModel model = new DefaultListModel();
        try {
            Page page = facebookClient.fetchObject("100008350479291", Page.class);
            System.out.println(page.getName());
            Connection<Post> pageFeed = facebookClient.fetchConnection(page.getId() + "/feed", Post.class);
            while (pageFeed.hasNext()) {
                pageFeed = facebookClient.fetchConnectionPage(pageFeed.getNextPageUrl(), Post.class);
                model.addElement(pageFeed.getNextPageUrl());
            }
        } catch (com.restfb.exception.FacebookOAuthException ex) {
            System.out.println("\n!!!!!!! Token Expired !!!!!!!!\n");
        }
        return model;
    }
}
