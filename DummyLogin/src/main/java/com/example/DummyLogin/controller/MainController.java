package com.example.DummyLogin.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.DummyLogin.dao.UploadDao;
import com.example.DummyLogin.dao.UserDao;
import com.example.DummyLogin.model.User;
import com.example.DummyLogin.repository.UserRepository;


@Controller
public class MainController {
	
	/*path where all uploaded files are stored*/
	private static String UPLOADED_FOLDER = "E://Data/";
  
	private UploadDao fileUploadDao = new UploadDao();

	@Autowired
	private UserRepository repo;

	/*start*/
	@GetMapping("/")
	public String register()
	{
		return "registration";
	}

    @GetMapping("/login")
	public String login()
	{
		return "login";
	}
    
	@GetMapping("/registration")
	public String registration()
	{
		return "registration";
	}
    
	@PostMapping("/login")
	public String login_process(@RequestParam("username") String username,
			@RequestParam("password") String password,HttpSession session,Model model)
	{
			     
	    session.setAttribute("userName",username); 
		session.setAttribute("password", password);
		User u=null;
		try
		{
			u= repo.findByuserNameAndPassword(username,password);			
		}
		catch(Exception e)
		{
			System.out.println("Invalid Username or password!!");
		}
		if(u!=null)
		{
			model.addAttribute("UserName", "Welcome "+username);
			return "home";
		}
		else
		{
			model.addAttribute("error", "Invalid Username or password!!");
			return "login";
		}
	}
	
	
	
	@PostMapping("/registration")
	public String registration_process(@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("username") String username,
			@RequestParam("passwd") String password,
			@RequestParam("confirmpassword") String confirmpassword,Model model)
	{	
		
        User user = new User();
       //Using Java Beans - An easiest way to play with group of related data
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUserName(username);
        user.setPassword(password); 
        
        UserDao registerDao = new UserDao();

        String userRegistered = registerDao.registerUser(user);
        
        if(userRegistered.equals("SUCCESS"))   //On success, you can display a message to user on Home page
        {
        	model.addAttribute("success","You've successfully registered!");
        	return "registration";
        }
        else   //On Failure, display a meaningful message to the User.
        {
           return "registration";
        }
		
	}
		
	
	@PostMapping("/uploadfile")
	public String fileUpload(@RequestParam("file") MultipartFile file,HttpSession session, Model model)
	{
	  
		String userName=(String)session.getAttribute("userName");
        String password=(String)session.getAttribute("password");
                
         String inputStream = null; 
         
            if (file != null) 
           {
            	System.out.print("hi");
            	inputStream = file.getOriginalFilename();
            	  
           }
            
            int row = fileUploadDao.uploadFile(userName,password,inputStream);
            if (row > 0) 
            {
            model.addAttribute("success","file uploaded sucessfully");
        	  model.addAttribute("UserName","Welcome "+session.getAttribute("userName"));
        
            }
        
          try {
		          byte[] bytes = file.getBytes();
		          Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
		          Files.write(path, bytes);
    
          	  }
          catch (IOException e) 
          {
        	  e.printStackTrace();
          }

          
                    return "home";		
	}
}
